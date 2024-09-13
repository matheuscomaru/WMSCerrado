package com.tecgesco.wmscerrado.model;

public class Cliente {

	private String identifier = ""; // * cnpj do cliente(sem pontuação)
	private String referenceId = ""; // * id do cliente no ERP
	private String fiscalName = ""; // * razao social do cliente
	private String fantasyName = ""; // * nome fantasia do cliente
	private String phone = ""; // * telefone do cliente
	private ClienteEndereco address = new ClienteEndereco(); // * endereco do cliente

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getFiscalName() {
		return fiscalName;
	}

	public void setFiscalName(String fiscalName) {
		this.fiscalName = fiscalName;
	}

	public String getFantasyName() {
		return fantasyName;
	}

	public void setFantasyName(String fantasyName) {
		this.fantasyName = fantasyName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public ClienteEndereco getAddress() {
		return address;
	}

	public void setAddress(ClienteEndereco address) {
		this.address = address;
	}

	public String toJson() {
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"identifier\":\"").append(identifier).append("\",");
		json.append("\"referenceId\":\"").append(referenceId).append("\",");
		json.append("\"fiscalName\":\"").append(fiscalName).append("\",");
		json.append("\"fantasyName\":\"").append(fantasyName).append("\",");
		json.append("\"phone\":\"").append(phone).append("\",");

		// Converte o objeto address em JSON
		json.append("\"address\":").append(address.toJson());

		json.append("}");
		return json.toString();
	}
}
