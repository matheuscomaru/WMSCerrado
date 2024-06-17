package com.tecgesco.wmscerrado.model;

import java.util.ArrayList;

public class Produto {

	private String productCode; // Código/Id do produto no ER
	private String description; // Descrição do produto no ER
	private String ean; // Código de barras EAN do produto'
	private String dun; // Código de barras DUN do produto'
	private String packingInfo; // Informação da embalagem'
	private String factoryCode; // Código de fábrica'
	private double dunQuantity = 0; // Quantidade no DUN'
	private double grossWeight = 0;// Peso bruto'
	private double netWeight = 0; // Peso líquido'
	private int palletDataQuantity; // qtde palete

	public int getPalletDataQuantity() {
		return palletDataQuantity;
	}

	public void setPalletDataQuantity(int palletDataQuantity) {
		this.palletDataQuantity = palletDataQuantity;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public String getDun() {
		return dun;
	}

	public void setDun(String dun) {
		this.dun = dun;
	}

	public String getPackingInfo() {
		return packingInfo;
	}

	public void setPackingInfo(String packingInfo) {
		this.packingInfo = packingInfo;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public double getDunQuantity() {
		return dunQuantity;
	}

	public void setDunQuantity(double dunQuantity) {
		this.dunQuantity = dunQuantity;
	}

	public double getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(double grossWeight) {
		this.grossWeight = grossWeight;
	}

	public double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(double netWeight) {
		this.netWeight = netWeight;
	}

	public String toJson() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"productCode\": \"" + this.productCode + "\",");
		sb.append("\"description\": \"" + this.description + "\",");
		sb.append("\"ean\": \"" + this.ean + "\",");
		sb.append("\"dun\": \"" + this.dun + "\",");
		sb.append("\"packingInfo\": \"" + this.packingInfo + "\",");
		sb.append("\"factoryCode\": \"" + this.factoryCode + "\",");
		sb.append("\"dunQuantity\": " + this.dunQuantity + ",");
		sb.append("\"grossWeight\": " + this.grossWeight + ",");
		sb.append("\"netWeight\": " + this.netWeight + ",");
		sb.append("\"palletDataQuantity\": " + this.palletDataQuantity);
		sb.append("}");
		return sb.toString();
	}

	public String listToJson(ArrayList<Produto> produtos) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < produtos.size(); i++) {
			Produto produto = produtos.get(i);
			sb.append("{");
			sb.append("\"productCode\": \"" + produto.getProductCode() + "\",");
			sb.append("\"description\": \"" + produto.getDescription() + "\",");
			sb.append("\"ean\": \"" + produto.getEan() + "\",");
			sb.append("\"dun\": \"" + produto.getDun() + "\",");
			sb.append("\"packingInfo\": \"" + produto.getPackingInfo() + "\",");
			sb.append("\"factoryCode\": \"" + produto.getFactoryCode() + "\",");
			sb.append("\"dunQuantity\": " + produto.getDunQuantity() + ",");
			sb.append("\"grossWeight\": " + produto.getGrossWeight() + ",");
			sb.append("\"netWeight\": " + produto.getNetWeight() + ",");
			sb.append("\"palletDataQuantity\": " + this.palletDataQuantity);
			sb.append("}");
			if (i < produtos.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

}
