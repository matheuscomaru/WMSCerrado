package com.tecgesco.wmscerrado.model;

import java.util.ArrayList;

public class OrdemProducao {

	private String id;
	private String productionBatch = ""; // lote
	private String expirationDate = ""; // yyyy-MM-dd data de validade
	private String manufacturingDate = ""; // yyyy-MM-dd data de fabricaca
	private String note = ""; // observação
	private ArrayList<ItensOrdemProducao> items = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductionBatch() {
		return productionBatch;
	}

	public void setProductionBatch(String productionBatch) {
		this.productionBatch = productionBatch;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getManufacturingDate() {
		return manufacturingDate;
	}

	public void setManufacturingDate(String manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public ArrayList<ItensOrdemProducao> getItems() {
		return items;
	}

	public void setItems(ArrayList<ItensOrdemProducao> items) {
		this.items = items;
	}

	public String toJson() {
		StringBuilder sb = new StringBuilder();

		sb.append("{");
		sb.append("\"id\": \"" + this.getId() + "\",");
		sb.append("\"productionBatch\": \"" + this.getProductionBatch() + "\",");
		sb.append("\"expirationDate\": \"" + this.getExpirationDate() + "\",");
		sb.append("\"manufacturingDate\": \"" + this.getManufacturingDate() + "\",");
		sb.append("\"note\": \"" + this.getNote() + "\",");

		sb.append("\"items\": [");
		ArrayList<ItensOrdemProducao> items = this.getItems();

		for (int j = 0; j < items.size(); j++) {
			ItensOrdemProducao item = items.get(j);
			sb.append("{");
			sb.append("\"productId\": \"" + item.getProductId() + "\",");
			sb.append("\"quantity\": " + item.getQuantity());
			sb.append("}");
			if (j < items.size() - 1) {
				sb.append(",");
			}
		}

		sb.append("]");
		sb.append("}");

		return sb.toString();

	}

	public String listToJson(ArrayList<OrdemProducao> lista) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < lista.size(); i++) {
			OrdemProducao pcp = lista.get(i);
			sb.append("{");
			sb.append("\"id\": \"" + pcp.getId() + "\",");
			sb.append("\"productionBatch\": \"" + pcp.getProductionBatch() + "\",");
			sb.append("\"expirationDate\": \"" + pcp.getExpirationDate() + "\",");
			sb.append("\"manufacturingDate\": \"" + pcp.getManufacturingDate() + "\",");
			sb.append("\"note\": \"" + pcp.getNote() + "\",");

			sb.append("\"items\": [");
			ArrayList<ItensOrdemProducao> items = pcp.getItems();

			for (int j = 0; j < items.size(); j++) {
				ItensOrdemProducao item = items.get(j);
				sb.append("{");
				sb.append("\"productId\": \"" + item.getProductId() + "\",");
				sb.append("\"quantity\": " + item.getQuantity());
				sb.append("}");
				if (j < items.size() - 1) {
					sb.append(",");
				}
			}
			sb.append("]");
			sb.append("}");

			if (i < lista.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
