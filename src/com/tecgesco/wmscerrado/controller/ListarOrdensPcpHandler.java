package com.tecgesco.wmscerrado.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.tecgesco.wmscerrado.Tools;
import com.tecgesco.wmscerrado.dao.OrdemProducaoDao;
import com.tecgesco.wmscerrado.model.OrdemProducao;

public class ListarOrdensPcpHandler implements HttpHandler {

	OrdemProducao pcp = new OrdemProducao();
	OrdemProducaoDao pcpDao = new OrdemProducaoDao();

	Tools ts = new Tools();
	String datainicial;
	String datafinal;
	String situacao;
	String resposta;

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		if ("GET".equals(exchange.getRequestMethod())) {

			URI requestURI = exchange.getRequestURI();
			Map<String, String> queryParams = ts.queryToMap(requestURI.getQuery());

			datainicial = queryParams.get("datainicial");
			datafinal = queryParams.get("datafinal");
			situacao = queryParams.get("situacao");

			if (validarCampos(exchange)) {
				exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
				exchange.sendResponseHeaders(200, 0);
				ArrayList<OrdemProducao> lista = new ArrayList<>();
				lista = pcpDao.getAll(datainicial, datafinal, Integer.parseInt(situacao));

				String resultado = pcp.listToJson(lista);

				try (OutputStream os = exchange.getResponseBody()) {
					os.write(resultado.getBytes(StandardCharsets.UTF_8));

				}
			}
		} else {
			exchange.sendResponseHeaders(405, 0);
		}
	}

	private boolean validarCampos(HttpExchange exchange) {

		if (datainicial == null || datainicial.isEmpty()) {
			enviarErro(exchange, "Informe a data inicial");
			return false;
		}

		if (datafinal == null || datafinal.isEmpty()) {
			enviarErro(exchange, "Informe a data final");
			return false;
		}

		if (situacao == null || situacao.isEmpty()) {
			enviarErro(exchange, "Informe a situacao. 0: aberto 1:finalizada 2:estornada ");
			return false;
		}

		return true;
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