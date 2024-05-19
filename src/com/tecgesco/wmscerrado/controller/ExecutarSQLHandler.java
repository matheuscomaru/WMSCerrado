package com.tecgesco.wmscerrado.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.tecgesco.wmscerrado.ModuloConexao;

public class ExecutarSQLHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		if ("GET".equals(exchange.getRequestMethod())) {

			String query = exchange.getRequestURI().getQuery();
			Map<String, String> params = extractParams(query);

			String sql = params.get("sql");
			String cnpj = params.get("cnpj");

			if (!params.get("authorization").equals("2BEE7157F896F37AA27BC613091DD32A")) {
				String resultado = "Não autorizado. Informe o authorization";
				exchange.sendResponseHeaders(400, resultado.length());
				try (OutputStream os = exchange.getResponseBody()) {
					os.write(resultado.getBytes());
				}
				return;
			}

			if (sql != null && sql.trim().toUpperCase().startsWith("SELECT")) {
				String resultado = executarConsultaSQL(cnpj, sql);

				exchange.sendResponseHeaders(200, 0);
				exchange.getResponseHeaders().set("Content-Type", "application/json");

				try (OutputStream os = exchange.getResponseBody()) {
					os.write(resultado.getBytes());
				}
			} else {
				String resultado = "Só é permitido usar SELECT";
				exchange.sendResponseHeaders(400, resultado.length());
				try (OutputStream os = exchange.getResponseBody()) {
					os.write(resultado.getBytes());
				}
			}
		} else {
			exchange.sendResponseHeaders(405, 0);
		}
	}

	private Map<String, String> extractParams(String query) {
		Map<String, String> paramMap = new HashMap<>();

		if (query != null && !query.isEmpty()) {
			String[] params = query.split("&");

			for (String param : params) {
				String[] keyValue = param.split("=");
				if (keyValue.length == 2) {
					String key = keyValue[0];
					String value = keyValue[1];
					System.out.println("quebra.:" + value);

					try {
						value = java.net.URLDecoder.decode(value, "UTF-8");

						if (key.equals("sql")) {
							value = value.replace("@@", "=");
						}

					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					paramMap.put(key, value);
				}
			}
		}

		return paramMap;
	}

	private String executarConsultaSQL(String cnpj, String sqlQuery) {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		ModuloConexao conexao = new ModuloConexao();

		System.out.println("Conectando ao banco de dados...");
		connection = conexao.getInstance();
		if (connection == null) {
			System.out.println("Não foi poss�vel conectar ao banco de dados");
			return "{\"error\":\"Não foi poss�vel conectar ao banco de dados\"}";
		}

		System.out.println("Conectado com sucesso.");

		sqlQuery = sqlQuery.replace("concat", "||");

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlQuery);

			StringBuilder jsonResult = new StringBuilder("[");
			while (resultSet.next()) {
				jsonResult.append("{");
				for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
					jsonResult.append("\"").append(resultSet.getMetaData().getColumnLabel(i)).append("\":\"")
							.append(resultSet.getString(i)).append("\",");
				}
				jsonResult.deleteCharAt(jsonResult.length() - 1); // Remove a v�rgula extra
				jsonResult.append("},");
			}
			if (jsonResult.length() > 1) {
				jsonResult.deleteCharAt(jsonResult.length() - 1); // Remove a v�rgula extra
			}
			jsonResult.append("]");

			connection.close();
			resultSet.close();
			statement.close();

			// Set UTF-8 encoding
			return new String(jsonResult.toString().replace("||", "concat").getBytes(StandardCharsets.UTF_8),
					StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"error\":\"Erro ao executar a consulta SQL\"}";
		}
	}

}