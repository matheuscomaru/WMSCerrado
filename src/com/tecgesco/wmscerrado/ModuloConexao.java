package com.tecgesco.wmscerrado;

import java.sql.Connection;
import java.sql.DriverManager;

public class ModuloConexao {

	static ConfigDao configDao = new ConfigDao();
	private static Connection db = null;

	public static Connection getInstance() {

		if (db == null) {
			db = conector();
			return db;
		} else {
			return db;
		}
	}

	public static Connection conector() {

		java.sql.Connection conexao = null;

		String url;
		String user = "sysdba";
		String password = "@CHx2021$";
		String cnpj = configDao.lerConfig("db.cnpj");

		try {

			url = "jdbc:firebirdsql:LOCALHOST/3050:C:\\CHSISTEMAS\\" + cnpj + "\\DADOS\\DADOS.FDB?encoding=ISO8859_1";
			String driver = "org.firebirdsql.jdbc.FBDriver";
			Class.forName(driver);
			conexao = DriverManager.getConnection(url, user, password);

			return conexao;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
