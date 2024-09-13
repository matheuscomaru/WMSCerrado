package com.tecgesco.wmscerrado.model;

public class ParcelaPagamentoNota {

	private int itemNumber = 0; // qual parcela que é
	private String emitDate = ""; // 2024-09-05T20:22:36.212Z", // data de emis
	private String dueDate = ""; // 2024-09-05T20:22:36.212Z", // data de vencim
	private double value = 0.0;

	public int getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getEmitDate() {
		return emitDate;
	}

	public void setEmitDate(String emitDate) {
		this.emitDate = emitDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String toJson() {
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"itemNumber\":").append(itemNumber).append(",");
		json.append("\"emitDate\":\"").append(emitDate).append("\",");
		json.append("\"dueDate\":\"").append(dueDate).append("\",");
		json.append("\"value\":").append(value);
		json.append("}");
		return json.toString();
	}

}
