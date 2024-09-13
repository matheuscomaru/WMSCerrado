package com.tecgesco.wmscerrado.model;

public class ItemNota {

	private int itemNumber = 0; // * numero de sequencia do item
	private String productCode = ""; // * código de fábrica do prod
	private String productName = ""; // * nome do produto
	private String ean = ""; // * código ean do produto
	private double quantity = 0.0;// quantidade
	private double amountUnit = 0.0; // valor unnitário
	private double amountTotal = 0.0; // valor total
	private String measurementUnit = ""; // unidade de medida

	public int getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getAmountUnit() {
		return amountUnit;
	}

	public void setAmountUnit(double amountUnit) {
		this.amountUnit = amountUnit;
	}

	public double getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(double amountTotal) {
		this.amountTotal = amountTotal;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public String toJson() {
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"itemNumber\":").append(itemNumber).append(",");
		json.append("\"productCode\":\"").append(productCode).append("\",");
		json.append("\"productName\":\"").append(productName).append("\",");
		json.append("\"ean\":\"").append(ean).append("\",");
		json.append("\"quantity\":").append(quantity).append(",");
		json.append("\"amountUnit\":").append(amountUnit).append(",");
		json.append("\"amountTotal\":").append(amountTotal).append(",");
		json.append("\"measurementUnit\":\"").append(measurementUnit).append("\"");
		json.append("}");
		return json.toString();
	}

}
