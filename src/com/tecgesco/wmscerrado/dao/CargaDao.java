package com.tecgesco.wmscerrado.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.tecgesco.wmscerrado.ModuloConexao;
import com.tecgesco.wmscerrado.model.Carga;
import com.tecgesco.wmscerrado.model.Route;
import com.tecgesco.wmscerrado.model.Vehicle;

public class CargaDao {

	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	{
		conexao = ModuloConexao.getInstance();
	}

	public ArrayList<Carga> getBySituacaoData(int situacao, String dataInicial, String dataFinal) {

		ArrayList<Carga> lista = new ArrayList<>();

		// @formatter:off
		String sql = "SELECT * FROM (\r\n"
				+ "SELECT MOTO.CODIGO AS CODMOTO, MOTO.APELIDO, TMS.CODIGO AS CARGA ,\r\n"
				+ "COALESCE((SELECT FIRST 1 DISTINCT(ROTA.CODIGO)\r\n"
				+ "FROM PEDIDOSAIDA PS \r\n"
				+ "JOIN CLIFOR CL ON CL.CHAVE = PS.CHAVECLIFOR \r\n"
				+ "JOIN CIDADEROTA CIDROT ON CIDROT.CHAVECIDADE = CL.CHAVECIDADE \r\n"
				+ "JOIN ROTA ON CIDROT.CHAVEROTA = ROTA.CHAVE\r\n"
				+ "WHERE PS.CHAVELOTEPEDSAIDA = LOT.CHAVE ),'000000') AS CODROTA,\r\n"
				+ "COALESCE((SELECT FIRST 1 DISTINCT(ROTA.DESCRICAO)\r\n"
				+ "FROM PEDIDOSAIDA PS \r\n"
				+ "JOIN CLIFOR CL ON CL.CHAVE = PS.CHAVECLIFOR \r\n"
				+ "JOIN CIDADEROTA CIDROT ON CIDROT.CHAVECIDADE = CL.CHAVECIDADE \r\n"
				+ "JOIN ROTA ON CIDROT.CHAVEROTA = ROTA.CHAVE\r\n"
				+ "WHERE PS.CHAVELOTEPEDSAIDA = LOT.CHAVE ),'SEM ROTA') AS NOMEROTA,\r\n"
				+ "VEIC.CODIGO AS CODVEICULO, VEIC.VEICULO, TMS.OBS,\r\n"
				+ "(SELECT COUNT(*) FROM PEDIDOSAIDA PS\r\n"
				+ "WHERE PS.CHAVELOTEPEDSAIDA = LOT.CHAVE\r\n"
				+ "AND PS.ATIVO = 1) AS QTDEPED,\r\n"
				+ "(SELECT COUNT(NF.CHAVE) FROM PEDIDOSAIDA PS\r\n"
				+ "JOIN NFSAIDA NF ON PS.CHAVE = NF.CHAVEPEDIDO\r\n"
				+ "WHERE PS.CHAVELOTEPEDSAIDA = LOT.CHAVE\r\n"
				+ "AND PS.ATIVO = 1 AND NF.ATIVO = 1 AND NF.STATUS = 1) AS QTDENOT\r\n"
				+ "FROM LOTEPEDSAIDA LOT\r\n"
				+ "JOIN TMSFRETES TMS ON TMS.CODIGO = LOT.LOTE\r\n"
				+ "JOIN TMSMOTORISTA MOTO ON MOTO.CHAVE = TMS.CHAVEMOTORISTA \r\n"
				+ "JOIN TMSVEICULOS VEIC ON VEIC.CHAVE = TMS.CHAVEVEICULO \r\n"
				+ "WHERE LOT.ATIVO = 1 AND TMS.ATIVO = 1\r\n"
				+ "AND TMS.SITUACAO = ?\r\n"
				+ "AND TMS.DATASAIDA BETWEEN ? AND ?\r\n"
				+ "ORDER BY TMS.CODIGO)\r\n"
				+ "WHERE QTDEPED = QTDENOT";
		// @formatter:on
		try {

			pst = conexao.prepareStatement(sql);
			pst.setInt(1, situacao);
			pst.setString(2, dataInicial);
			pst.setString(3, dataFinal);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				Carga c = new Carga();
				Route route = new Route();
				Vehicle vehicle = new Vehicle();

				c.setResponsibleId(rs.getString("CODMOTO"));
				c.setResponsibleName(rs.getString("APELIDO"));
				c.setReferenceId(rs.getString("CARGA"));
				route.setId(rs.getString("CODROTA"));
				route.setDescription(rs.getString("NOMEROTA"));
				c.setRoute(route);
				vehicle.setId(rs.getString("CODVEICULO"));
				vehicle.setDescription(rs.getString("VEICULO"));
				c.setVehicle(vehicle);
				c.setNote(rs.getString("OBS"));
				lista.add(c);

			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, e1);

		}

		return lista;

	}

}
