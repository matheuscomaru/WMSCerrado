package com.tecgesco.wmscerrado.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MeuHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		exchange.sendResponseHeaders(200, 0);
		exchange.getResponseHeaders().set("Content-Type", "text/plain");

		String htmlPath = "index.html";
		InputStream inputStream = getClass().getResourceAsStream(htmlPath);

		if (inputStream != null) {
			try (OutputStream os = exchange.getResponseBody()) {
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
			} finally {
				inputStream.close();
			}
		} else {
			exchange.sendResponseHeaders(404, 0);
		}
	}

}