package com.tecgesco.wmscerrado.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.tecgesco.wmscerrado.ModuloConexao;
import com.tecgesco.wmscerrado.model.Produto;

public class ProdutoDao {

	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	{
		conexao = ModuloConexao.getInstance();
	}

	public ArrayList<Produto> getAll() {

		ArrayList<Produto> lista = new ArrayList<>();

		String sql = "SELECT * FROM PRODUTO WHERE ATIVO=1 AND STATUS = 0 AND TIPO = 4";

		try {

			pst = conexao.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				Produto p = new Produto();
				p.setProductCode(rs.getString("CODIGO"));
				p.setDescription(rs.getString("DESCRICAO"));
				System.out.println(rs.getString("DESCRICAO"));
				p.setEan(rs.getString("CODBARRAS"));
				p.setDun("");
				p.setPackingInfo("");
				p.setFactoryCode(rs.getString("CODIGOFABRICA"));
				p.setDunQuantity(rs.getDouble("ITENSEMBALAGEM"));
				p.setGrossWeight(rs.getDouble("PESOBRUTO"));
				p.setNetWeight(rs.getDouble("PESOLIQUIDO"));

				lista.add(p);

			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, e1);

		}

		return lista;

	}

	public Produto getByCodigo(String codigo) {

		String sql = "SELECT * FROM PRODUTO WHERE ATIVO=1 AND STATUS = 0 AND TIPO = 4 AND CODIGO = LPAD(CAST(? AS VARCHAR(6)),6,'0')";

		try {

			pst = conexao.prepareStatement(sql);
			pst.setString(1, codigo);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				Produto p = new Produto();
				p.setProductCode(rs.getString("CODIGO"));
				p.setDescription(rs.getString("DESCRICAO"));
				System.out.println(rs.getString("DESCRICAO"));
				p.setEan(rs.getString("CODBARRAS"));
				p.setDun("");
				p.setPackingInfo("");
				p.setFactoryCode(rs.getString("CODIGOFABRICA"));
				p.setDunQuantity(rs.getDouble("ITENSEMBALAGEM"));
				p.setGrossWeight(rs.getDouble("PESOBRUTO"));
				p.setNetWeight(rs.getDouble("PESOLIQUIDO"));

				return p;

			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, e1);

		}

		return null;

	}

}
