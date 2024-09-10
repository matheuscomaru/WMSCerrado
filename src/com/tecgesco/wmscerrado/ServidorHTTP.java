package com.tecgesco.wmscerrado;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.tecgesco.wmscerrado.controller.ListarCargasAbertasHandler;
import com.tecgesco.wmscerrado.controller.ListarOrdensPcpHandler;
import com.tecgesco.wmscerrado.controller.ListarProdutosHandler;
import com.tecgesco.wmscerrado.controller.MeuHandler;
import com.tecgesco.wmscerrado.controller.OpPorId;
import com.tecgesco.wmscerrado.controller.ProdutoPorId;

public class ServidorHTTP {

	public static void iniciarServidor(int porta) throws IOException {

		HttpServer server = HttpServer.create(new InetSocketAddress(porta), 0);

		server.createContext("/", new MeuHandler());
		server.createContext("/listarprodutos", new ListarProdutosHandler());
		server.createContext("/produto/", new ProdutoPorId());
		server.createContext("/listarops", new ListarOrdensPcpHandler());
		server.createContext("/op/", new OpPorId());
		server.createContext("/listarcargasfaturadas", new ListarCargasAbertasHandler());

		System.out.println("Servidor iniciado na porta " + porta);
		server.start();
	}

}