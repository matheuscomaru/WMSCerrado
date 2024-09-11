package com.tecgesco.wmscerrado.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.tecgesco.wmscerrado.dao.CargaDao;
import com.tecgesco.wmscerrado.dao.NotaDao;
import com.tecgesco.wmscerrado.model.Carga;
import com.tecgesco.wmscerrado.model.Nota;

public class ListarNotasHandler implements HttpHandler {

	Nota nota = new Nota();
	NotaDao notaDao = new NotaDao();

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		if ("GET".equals(exchange.getRequestMethod())) {

			exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
			exchange.sendResponseHeaders(200, 0);

			URI requestURI = exchange.getRequestURI();
			Map<String, String> queryParams = queryToMap(requestURI.getQuery());

			String id = queryParams.get("id");

			ArrayList<Nota> lista = new ArrayList<>();
			lista = notaDao.getByLote(id);

			String resultado = nota.listToJson(lista);

			try (OutputStream os = exchange.getResponseBody()) {
				os.write(resultado.getBytes(StandardCharsets.UTF_8));

			}

		} else {
			exchange.sendResponseHeaders(405, 0);
		}
	}

	private Map<String, String> queryToMap(String query) {
		Map<String, String> result = new HashMap<>();
		if (query == null) {
			return result;
		}
		for (String param : query.split("&")) {
			String[] pair = param.split("=");
			if (pair.length > 1) {
				result.put(pair[0], pair[1]);
			} else {
				result.put(pair[0], "");
			}
		}
		return result;
	}

}