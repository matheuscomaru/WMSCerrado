package com.tecgesco.wmscerrado.model;

public class ItensOrdemProducao {

	private String productId; // id do produto no ERP
	private int quantity;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
