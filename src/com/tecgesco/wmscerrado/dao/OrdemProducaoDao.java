package com.tecgesco.wmscerrado.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.tecgesco.wmscerrado.ModuloConexao;
import com.tecgesco.wmscerrado.model.ItensOrdemProducao;
import com.tecgesco.wmscerrado.model.OrdemProducao;

public class OrdemProducaoDao {

	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	{
		conexao = ModuloConexao.getInstance();
	}

	public ArrayList<OrdemProducao> getAll(String datainicial, String datafinal, int situacao) {
		// 0: aberto 1:finalizada 2:estornada
		ArrayList<OrdemProducao> lista = new ArrayList<>();

		//@formatter:off
		String sql = "SELECT NUMORDEM,\r\n"
				+ "COALESCE(LOT.LOTE,'') AS LOTE, \r\n"
				+ "LOT.VALIDADE,\r\n"
				+ "LOT.FABRICACAO,\r\n"
				+ "L.OBS, \r\n"
				+ "PA.CODIGO AS CODPROD, \r\n"
				+ "PA.DESCRICAO, \r\n"
				+ "IPCP.QTDE \r\n"
				+ "FROM LANCORDEMPROD L \r\n"
				+ "JOIN ITENSLANCORDEMPROD IPCP ON IPCP.CHAVELANCORDEMPROD = L.CHAVE\r\n"
				+ "JOIN PRODUTO PA ON PA.CHAVE = IPCP.CHAVEPRODUTO \r\n"
				+ "LEFT JOIN LOTEPRODUTOS LOT ON LOT.CHAVE = IPCP.CHAVELOTEPROD \r\n"
				+ "WHERE L.ATIVO = 1 \r\n"
				+ "AND SITUACAO = 0 \r\n"
				+ "AND IPCP.ATIVO=1 \r\n"
				+ "AND IPCP.TIPO = 1\r\n"
				+ "AND L.CHAVEEMPRESA = 1 \r\n"
				+ "AND L.DATALANC BETWEEN '2024-08-01' AND '2025-08-01' \r\n"
				+ "AND L.SITUACAO = 0\r\n"
				+ "ORDER BY IPCP.CHAVELANCORDEMPROD DESC";
		//@formatter:on
		try {

			pst = conexao.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				OrdemProducao pcp = new OrdemProducao();
				ArrayList<ItensOrdemProducao> itens = new ArrayList<>();
				ItensOrdemProducao ipcp = new ItensOrdemProducao();

				pcp.setId(rs.getString("NUMORDEM"));
				pcp.setProductionBatch(rs.getString("LOTE"));
				pcp.setExpirationDate(rs.getString("VALIDADE"));
				pcp.setManufacturingDate(rs.getString("FABRICACAO"));
				pcp.setNote(rs.getString("OBS"));

				ipcp.setProductId(rs.getString("CODPROD"));
				ipcp.setQuantity(rs.getInt("QTDE"));
				itens.add(ipcp);
				pcp.setItems(itens);

				lista.add(pcp);

			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, e1);

		}

		return lista;

	}

	public OrdemProducao getByCodigo(String codigo) {

		//@formatter:off
		String sql = "SELECT NUMORDEM,COALESCE(LOT.LOTE,'') AS LOTE, \r\n"
				+ "LOT.VALIDADE,LOT.FABRICACAO,L.OBS, PA.CODIGO AS CODPROD, PA.DESCRICAO, IPCP.QTDE \r\n"
				+ "FROM LANCORDEMPROD L JOIN ITENSLANCORDEMPROD IPCP ON IPCP.CHAVELANCORDEMPROD = L.CHAVE\r\n"
				+ "JOIN PRODUTO PA ON PA.CHAVE = IPCP.CHAVEPRODUTO \r\n"
				+ "LEFT JOIN LOTEPRODUTOS LOT ON LOT.CHAVE = IPCP.CHAVELOTEPROD \r\n"
				+ "WHERE L.ATIVO = 1 \r\n"
				+ "AND SITUACAO = 0 \r\n"
				+ "AND IPCP.ATIVO=1 \r\n"
				+ "AND IPCP.TIPO = 1\r\n"
				+ "AND L.CHAVEEMPRESA = 1 \r\n"
				+ "AND L.NUMORDEM = LPAD(CAST(? AS VARCHAR(6)),6,'0') \r\n"
				+ "ORDER BY IPCP.CHAVELANCORDEMPROD DESC";
		//@formatter:on

		try {

			pst = conexao.prepareStatement(sql);
			pst.setString(1, codigo);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				OrdemProducao pcp = new OrdemProducao();
				ArrayList<ItensOrdemProducao> itens = new ArrayList<>();
				ItensOrdemProducao ipcp = new ItensOrdemProducao();

				pcp.setId(rs.getString("NUMORDEM"));
				pcp.setProductionBatch(rs.getString("LOTE"));
				pcp.setExpirationDate(rs.getString("VALIDADE"));
				pcp.setManufacturingDate(rs.getString("FABRICACAO"));
				pcp.setNote(rs.getString("OBS"));

				ipcp.setProductId(rs.getString("CODPROD"));
				ipcp.setQuantity(rs.getInt("QTDE"));
				itens.add(ipcp);
				pcp.setItems(itens);

				return pcp;

			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, e1);

		}

		return null;

	}

}
