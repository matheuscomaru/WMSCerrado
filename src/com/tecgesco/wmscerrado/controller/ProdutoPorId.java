package com.tecgesco.wmscerrado.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.tecgesco.wmscerrado.dao.ProdutoDao;
import com.tecgesco.wmscerrado.model.Produto;

public class ProdutoPorId implements HttpHandler {

	Produto produto = new Produto();
	ProdutoDao produtoDao = new ProdutoDao();

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		Produto produto = new Produto();
		ProdutoDao produtoDao = new ProdutoDao();

		if ("GET".equals(exchange.getRequestMethod())) {

			String path = exchange.getRequestURI().getPath();

			String[] partes = path.split("/");

			if (partes.length >= 2) {

				String id = partes[2];
				String resposta;

				produto = produtoDao.getByCodigo(id);

				if (produto != null) {

					resposta = produto.toJson();
					System.out.println(resposta);
					exchange.sendResponseHeaders(200, 0);
					exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

					resposta = new String(resposta.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

					try (OutputStream os = exchange.getResponseBody()) {
						os.write(resposta.getBytes());

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