package com.tecgesco.wmscerrado.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.tecgesco.wmscerrado.ModuloConexao;
import com.tecgesco.wmscerrado.model.ItemNota;
import com.tecgesco.wmscerrado.model.Nota;
import com.tecgesco.wmscerrado.model.PagamentoNota;
import com.tecgesco.wmscerrado.model.ParcelaPagamentoNota;

public class NotaDao {

	public ArrayList<Nota> getByLote(String lote) {
		Connection conexao = ModuloConexao.getInstance();
		ArrayList<Nota> lista = new ArrayList<>();

		// @formatter:off
		String sql = "SELECT NF.CHAVE,REPLACE(REPLACE(REPLACE(EMP.CNPJCPF,'.',''),'/',''),'-','') AS CNPJEMP,\r\n"
				+ "EMP.RAZAOSOCIAL AS NOMEEMP,\r\n"
				+ "REPLACE(REPLACE(REPLACE(CL.CNPJCPF,'.',''),'/',''),'-','') AS CNPJCPF,\r\n"
				+ "CL.RAZAOSOCIAL,PS.PEDIDO,0 AS SEQUENCIA,NF.CHAVEACESSONFE,\r\n"
				+ "NF.NUMERONF, NF.SERIENF, NF.TOTALNF, NF.PESOBRUTO, NF.QTDEVOLUME,\r\n"
				+ "CASE PS.FORMAPGTO \r\n"
				+ "WHEN -1 THEN 'SEMPGTO'\r\n"
				+ "WHEN 0 THEN 'DINHEIRO'\r\n"
				+ "WHEN 3 THEN 'BOLETO'\r\n"
				+ "WHEN 6 THEN 'BANCO'\r\n"
				+ "WHEN 11 THEN 'PIX'\r\n"
				+ "ELSE 'OUTROS' END AS FORMAPGTO,\r\n"
				+ "COALESCE(PGTO.CODIGO,'') AS CODPGTO, COALESCE(PGTO.DESCRICAO,'') AS DESCPGTO\r\n"
				+ "FROM PEDIDOSAIDA PS\r\n"
				+ "JOIN NFSAIDA NF ON PS.CHAVE = NF.CHAVEPEDIDO\r\n"
				+ "JOIN LOTEPEDSAIDA LOT ON LOT.CHAVE = PS.CHAVELOTEPEDSAIDA \r\n"
				+ "JOIN CLIFOR CL ON CL.CHAVE = PS.CHAVECLIFOR\r\n"
				+ "JOIN EMPRESA EMP ON EMP.CHAVE = PS.CHAVEEMPRESA\r\n"
				+ "LEFT JOIN CONDPGTO PGTO ON PGTO.CHAVE = PS.CHAVECONDPGTO\r\n"
				+ "WHERE LOT.ATIVO = 1 \r\n"
				+ "AND NF.STATUS = 1\r\n"
				+ "AND NF.ATIVO = 1\r\n"
				+ "AND PS.ATIVO = 1\r\n"
				+ "AND LOT.LOTE = ?";
		// @formatter:on
		try {

			PreparedStatement pst = conexao.prepareStatement(sql);
			pst.setString(1, lote);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				Nota nota = new Nota();
				PagamentoNota pgto = new PagamentoNota();
				nota.setChave(rs.getInt("CHAVE"));
				nota.setSenderId(rs.getString("CNPJEMP"));
				nota.setSenderName(rs.getString("NOMEEMP"));
				nota.setReceiverId(rs.getString("CNPJCPF"));
				nota.setReceiverName(rs.getString("RAZAOSOCIAL"));
				nota.setOrderId(rs.getString("PEDIDO"));
				nota.setSequence(rs.getInt("SEQUENCIA"));
				nota.setKey(rs.getString("CHAVEACESSONFE"));
				nota.setNumber(rs.getInt("NUMERONF"));
				nota.setSeries(rs.getInt("SERIENF"));
				nota.setAmount(rs.getDouble("TOTALNF"));
				nota.setWeight(rs.getDouble("PESOBRUTO"));
				nota.setQuantity(rs.getInt("QTDEVOLUME"));
				pgto.setId(rs.getString("FORMAPGTO").trim());
				pgto.setBillingId(rs.getString("CODPGTO").trim());
				pgto.setDescription(rs.getString("DESCPGTO").trim());
				nota.setPaymentInfo(pgto);

				lista.add(nota);

			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, e1);

		}

		return populaComplementos(lista);

	}

	public ArrayList<ItemNota> getItens(int chavenf) {
		Connection conexao = ModuloConexao.getInstance();
		ArrayList<ItemNota> lista = new ArrayList<>();

		// @formatter:off
		String sql = "SELECT  ROW_NUMBER() OVER (ORDER BY INF.CHAVE) AS sequencial, \r\n"
				+ "PR.CODIGO , PR.DESCRICAO , PR.CODBARRAS , INF.QTDE , INF.VLRUNIT , INF.VLRTOTAL, UNID.UNMAIOR AS UNIDADE \r\n"
				+ "FROM ITENSNFSAIDA INF\r\n"
				+ "JOIN PRODUTO PR ON INF.CHAVEPRODUTO = PR.CHAVE\r\n"
				+ "JOIN UNIDADE UNID ON UNID.CHAVE = PR.CHAVEUNIDADE \r\n"
				+ "WHERE INF.ATIVO = 1 AND CHAVENFSAIDA = ?";
		// @formatter:on
		try {

			PreparedStatement pst = conexao.prepareStatement(sql);
			pst.setInt(1, chavenf);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				ItemNota item = new ItemNota();
				item.setItemNumber(rs.getInt("sequencial"));
				item.setProductCode(rs.getString("CODIGO"));
				item.setProductName(rs.getString("DESCRICAO"));
				item.setEan(rs.getString("CODBARRAS"));
				item.setQuantity(rs.getDouble("QTDE"));
				item.setAmountUnit(rs.getDouble("VLRUNIT"));
				item.setAmountTotal(rs.getDouble("VLRTOTAL"));
				item.setMeasurementUnit(rs.getString("UNIDADE"));

				lista.add(item);

			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, e1);

		}

		return lista;

	}

	public ArrayList<ParcelaPagamentoNota> getParcelas(int chavenf) {

		Connection conexao = ModuloConexao.getInstance();

		ArrayList<ParcelaPagamentoNota> lista = new ArrayList<>();

		// @formatter:off
		String sql = "SELECT REC.PARCELA ,\r\n"
				+ "REC.DATACONTA, REC.VENCIMENTO, REC.VALOR \r\n"
				+ "FROM RECEBER REC\r\n"
				+ "WHERE REC.ATIVO= 1 AND REC.CHAVEDOC = ?\r\n"
				+ "ORDER BY REC.PARCELA";
		// @formatter:on
		try {

			PreparedStatement pst = conexao.prepareStatement(sql);
			pst.setInt(1, chavenf);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				ParcelaPagamentoNota item = new ParcelaPagamentoNota();
				item.setItemNumber(rs.getInt("PARCELA"));
				item.setEmitDate(rs.getString("DATACONTA"));
				item.setDueDate(rs.getString("VENCIMENTO"));
				item.setValue(rs.getDouble("VALOR"));
				lista.add(item);

			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, e1);

		}

		return lista;

	}

	private ArrayList<Nota> populaComplementos(ArrayList<Nota> lista) {

		for (Nota nota : lista) {
			nota.setItems(getItens(nota.getChave()));
			nota.setInstallments(getParcelas(nota.getChave()));
		}

		return lista;
	}

}
