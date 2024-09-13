package com.tecgesco.wmscerrado.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.tecgesco.wmscerrado.Tools;
import com.tecgesco.wmscerrado.dao.OrdemProducaoDao;
import com.tecgesco.wmscerrado.dao.ProdutoDao;
import com.tecgesco.wmscerrado.model.OrdemProducao;
import com.tecgesco.wmscerrado.model.Produto;

public class OpPorId implements HttpHandler {

	Tools ts = new Tools();
	String id;
	String resposta;

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		OrdemProducao pcp = new OrdemProducao();
		OrdemProducaoDao pcpDao = new OrdemProducaoDao();

		if ("GET".equals(exchange.getRequestMethod())) {

			URI requestURI = exchange.getRequestURI();
			Map<String, String> queryParams = ts.queryToMap(requestURI.getQuery());

			id = queryParams.get("id");

			if (validarCampos(exchange)) {

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
			}

		} else {
			exchange.sendResponseHeaders(405, 0);
		}

	}

	private boolean validarCampos(HttpExchange exchange) {

		if (id == null || id.isEmpty()) {
			enviarErro(exchange, "Informe o numero da OP no parametro ID");
			return false;
		}

		return true;
	}

	private static void enviarRespostaNotFound(HttpExchange exchange) throws IOException {
		exchange.sendResponseHeaders(404, 0);
		exchange.getResponseBody().close();
	}

	private static void enviarErro(HttpExchange exchange, String erro) {

		try {
			exchange.sendResponseHeaders(400, erro.length());
			OutputStream os = exchange.getResponseBody();
			os.write(erro.getBytes());
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}