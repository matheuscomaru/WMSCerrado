package com.tecgesco.wmscerrado.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.tecgesco.wmscerrado.dao.OrdemProducaoDao;
import com.tecgesco.wmscerrado.dao.ProdutoDao;
import com.tecgesco.wmscerrado.model.OrdemProducao;
import com.tecgesco.wmscerrado.model.Produto;

public class OpPorId implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		OrdemProducao pcp = new OrdemProducao();
		OrdemProducaoDao pcpDao = new OrdemProducaoDao();

		if ("GET".equals(exchange.getRequestMethod())) {

			String path = exchange.getRequestURI().getPath();

			String[] partes = path.split("/");

			if (partes.length >= 2) {

				String id = partes[2];
				String resposta;

				pcp = pcpDao.getByCodigo(id);

				if (pcp != null) {

					resposta = pcp.toJson();
					System.out.println(resposta);
					exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
					exchange.sendResponseHeaders(200, 0);

					try (OutputStream os = exchange.getResponseBody()) {
						os.write(resposta.getBytes(StandardCharsets.UTF_8));

					}

				} else {
					exchange.sendResponseHeaders(204, 0);
				}

			} else {
				enviarRespostaNotFound(exchange);
			}
		} else {
			exchange.sendResponseHeaders(405, 0);
		}

	}

	private static void enviarRespostaNotFound(HttpExchange exchange) throws IOException {
		exchange.sendResponseHeaders(404, 0);
		exchange.getResponseBody().close();
	}
}