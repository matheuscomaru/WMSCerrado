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
import com.tecgesco.wmscerrado.Tools;
import com.tecgesco.wmscerrado.dao.CargaDao;
import com.tecgesco.wmscerrado.model.Carga;

public class ListarCargasFaturadasHandler implements HttpHandler {

	Carga carga = new Carga();
	CargaDao cargaDao = new CargaDao();
	Tools ts = new Tools();

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		if ("GET".equals(exchange.getRequestMethod())) {

			exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
			exchange.sendResponseHeaders(200, 0);

			URI requestURI = exchange.getRequestURI();
			Map<String, String> queryParams = ts.queryToMap(requestURI.getQuery());

			String datainicial = queryParams.get("datainicial");
			String datafinal = queryParams.get("datafinal");

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

}