package com.tecgesco.wmscerrado.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.tecgesco.wmscerrado.ModuloConexao;
import com.tecgesco.wmscerrado.model.Cliente;
import com.tecgesco.wmscerrado.model.ClienteEndereco;

public class ClienteDao {

	public Cliente getByCnpj(String cnpj) {
		Connection conexao = ModuloConexao.getInstance();
		Cliente cliente = new Cliente();
		ClienteEndereco endereco = new ClienteEndereco();

		// @formatter:off
		String sql = "SELECT REPLACE(REPLACE(REPLACE(CL.CNPJCPF,'.',''),'/',''),'-','') AS CNPJ, \r\n"
				+ "CL.CODIGO,CL.RAZAOSOCIAL, CL.FANTASIA, CL.CELULAR, CL.ENDERECO, CL.NUMERO, CL.BAIRRO,\r\n"
				+ "CID.CIDADE, CID.UF, CL.CEP\r\n"
				+ "FROM CLIFOR CL\r\n"
				+ "JOIN CIDADE CID ON CID.CHAVE = CL.CHAVECIDADE\r\n"
				+ "WHERE CL.ATIVO = 1 AND CL.STATUS = 0\r\n"
				+ "AND REPLACE(REPLACE(REPLACE(CL.CNPJCPF,'.',''),'/',''),'-','') = ?";
		// @formatter:on
		try {

			PreparedStatement pst = conexao.prepareStatement(sql);
			pst.setString(1, cnpj);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				cliente.setIdentifier(rs.getString("CNPJ"));
				cliente.setReferenceId(rs.getString("CODIGO"));
				cliente.setFiscalName(rs.getString("RAZAOSOCIAL"));
				cliente.setFantasyName(rs.getString("FANTASIA"));
				cliente.setPhone(rs.getString("CELULAR"));
				endereco.setLatitude("");
				endereco.setLongitude("");
				endereco.setPlace(rs.getString("ENDERECO"));
				endereco.setAddressNumber(rs.getString("NUMERO"));
				endereco.setNeighborhood(rs.getString("BAIRRO"));
				endereco.setCityName(rs.getString("CIDADE"));
				endereco.setStateCode(rs.getString("UF"));
				endereco.setZipCode(rs.getString("CEP"));
				endereco.setCountryName("BRASIL");
				cliente.setAddress(endereco);
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, e1);

		}

		return cliente;

	}

}
