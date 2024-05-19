package com.tecgesco.wmscerrado.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.tecgesco.wmscerrado.dao.ProdutoDao;
import com.tecgesco.wmscerrado.model.Produto;

public class ListarProdutosHandler implements HttpHandler {

	Produto produto = new Produto();
	ProdutoDao produtoDao = new ProdutoDao();

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		if ("GET".equals(exchange.getRequestMethod())) {

			exchange.sendResponseHeaders(200, 0);
			exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

			ArrayList<Produto> listaProdutos = new ArrayList<>();
			listaProdutos = produtoDao.getAll();

			String resultado = new String(produto.listToJson(listaProdutos).getBytes(StandardCharsets.UTF_8),
					StandardCharsets.UTF_8);

			try (OutputStream os = exchange.getResponseBody()) {
				os.write(resultado.getBytes());

			}

		} else {
			exchange.sendResponseHeaders(405, 0);
		}
	}

}