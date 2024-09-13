package com.tecgesco.wmscerrado.controller;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.tecgesco.wmscerrado.App;

public class MeuHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		String response = "TecGesco Gestão - API WMS Cerrado Versão " + App.getVersao();

		exchange.sendResponseHeaders(200, response.getBytes().length);
		exchange.getResponseHeaders().set("Content-Type", "text/plain");

		try (OutputStream os = exchange.getResponseBody()) {
			os.write(response.getBytes());
		}
	}
}