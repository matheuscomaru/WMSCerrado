package com.tecgesco.wmscerrado.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.tecgesco.wmscerrado.dao.OrdemProducaoDao;
import com.tecgesco.wmscerrado.dao.ProdutoDao;
import com.tecgesco.wmscerrado.model.OrdemProducao;
import com.tecgesco.wmscerrado.model.Produto;

public class ListarOrdensPcpHandler implements HttpHandler {

	OrdemProducao pcp = new OrdemProducao();
	OrdemProducaoDao pcpDao = new OrdemProducaoDao();

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		if ("GET".equals(exchange.getRequestMethod())) {

			exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
			exchange.sendResponseHeaders(200, 0);

			ArrayList<OrdemProducao> lista = new ArrayList<>();
			lista = pcpDao.getAll();

			String resultado = pcp.listToJson(lista);

			try (OutputStream os = exchange.getResponseBody()) {
				os.write(resultado.getBytes(StandardCharsets.UTF_8));

			}

		} else {
			exchange.sendResponseHeaders(405, 0);
		}
	}

}