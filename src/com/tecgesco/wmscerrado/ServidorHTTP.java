package com.tecgesco.wmscerrado;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.tecgesco.wmscerrado.controller.ListarProdutosHandler;
import com.tecgesco.wmscerrado.controller.MeuHandler;
import com.tecgesco.wmscerrado.controller.ProdutoPorId;

public class ServidorHTTP {

	public static void iniciarServidor(int porta) throws IOException {

		HttpServer server = HttpServer.create(new InetSocketAddress(porta), 0);

		server.createContext("/", new MeuHandler());
		server.createContext("/listarprodutos", new ListarProdutosHandler());
		server.createContext("/produto/", new ProdutoPorId());

		System.out.println("Servidor iniciado na porta " + porta);
		server.start();
	}

}