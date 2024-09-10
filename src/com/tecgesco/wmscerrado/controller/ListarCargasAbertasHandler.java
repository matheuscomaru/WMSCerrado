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
import com.tecgesco.wmscerrado.model.Carga;

public class ListarCargasAbertasHandler implements HttpHandler {

	Carga carga = new Carga();
	CargaDao cargaDao = new CargaDao();

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		System.out.println("chegou");

		if ("GET".equals(exchange.getRequestMethod())) {

			exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
			exchange.sendResponseHeaders(200, 0);

			// Extraindo os parâmetros da URL
			URI requestURI = exchange.getRequestURI();
			Map<String, String> queryParams = queryToMap(requestURI.getQuery());

			String datainicial = queryParams.get("datainicial");
			String datafinal = queryParams.get("datafinal");

			System.out.println("datainicial.:" + datainicial);
			System.out.println("datafinal.:" + datafinal);

			ArrayList<Carga> lista = new ArrayList<>();
			lista = cargaDao.getBySituacaoData(0, datainicial, datafinal);

			String resultado = carga.listToJson(lista);

			try (OutputStream os = exchange.getResponseBody()) {
				os.write(resultado.getBytes(StandardCharsets.UTF_8));

			}

		} else {
			exchange.sendResponseHeaders(405, 0);
		}
	}

	// Função auxiliar para converter a query string em um mapa
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