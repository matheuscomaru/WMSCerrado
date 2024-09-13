package com.tecgesco.wmscerrado.model;

public class PagamentoNota {

	private String id = ""; // * identificação do pagamento, exemplo P
	private String billingId = ""; // * id da cobrança
	private String description = ""; // * descrição

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBillingId() {
		return billingId;
	}

	public void setBillingId(String billingId) {
		this.billingId = billingId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toJson() {
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"id\":\"").append(id).append("\",");
		json.append("\"billingId\":\"").append(billingId).append("\",");
		json.append("\"description\":\"").append(description).append("\"");
		json.append("}");
		return json.toString();
	}
}
