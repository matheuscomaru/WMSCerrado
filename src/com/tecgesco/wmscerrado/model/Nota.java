package com.tecgesco.wmscerrado.model;

import java.util.ArrayList;

public class Nota {

	private int chave;
	private String senderId; // * cnpj do emissor(sem pontuação)
	private String senderName; // * nome do emissor
	private String receiverId; // * cnpj do desinatário(sem pontuaç
	private String receiverName; // * nome do destinatário
	private String orderId; // * número do pedido no ERP
	private int sequence;// * sequencia de entrega
	private String key; // * chave da nota

	private int number; // * número da nota
	private int series; // * série da nota
	private double amount; // * valor da nota
	private double weight; // * peso da nota
	private double quantity;

	private ArrayList<ItemNota> items = new ArrayList<>();
	private PagamentoNota paymentInfo = new PagamentoNota(); // * dados de pagamento da nota
	private ArrayList<ParcelaPagamentoNota> installments = new ArrayList<>(); // parcelas da nota (opcional)

	public int getChave() {
		return chave;
	}

	public void setChave(int chave) {
		this.chave = chave;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSeries() {
		return series;
	}

	public void setSeries(int series) {
		this.series = series;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public ArrayList<ItemNota> getItems() {
		return items;
	}

	public void setItems(ArrayList<ItemNota> items) {
		this.items = items;
	}

	public PagamentoNota getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(PagamentoNota paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public ArrayList<ParcelaPagamentoNota> getInstallments() {
		return installments;
	}

	public void setInstallments(ArrayList<ParcelaPagamentoNota> installments) {
		this.installments = installments;
	}

	public String toJson() {
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"senderId\":\"").append(senderId).append("\",");
		json.append("\"senderName\":\"").append(senderName).append("\",");
		json.append("\"receiverId\":\"").append(receiverId).append("\",");
		json.append("\"receiverName\":\"").append(receiverName).append("\",");
		json.append("\"orderId\":\"").append(orderId).append("\",");
		json.append("\"sequence\":").append(sequence).append(",");
		json.append("\"key\":\"").append(key).append("\",");
		json.append("\"number\":").append(number).append(",");
		json.append("\"series\":").append(series).append(",");
		json.append("\"amount\":").append(amount).append(",");
		json.append("\"weight\":").append(weight).append(",");
		json.append("\"quantity\":").append(quantity).append(",");

		// Convertendo os itens
		json.append("\"items\":[");
		for (int i = 0; i < items.size(); i++) {
			json.append(items.get(i).toJson());
			if (i < items.size() - 1) {
				json.append(",");
			}
		}
		json.append("],");

		// Convertendo as informações de pagamento
		json.append("\"paymentInfo\":[");
		json.append(paymentInfo.toJson());
		json.append("],");

		// Convertendo as parcelas
		json.append("\"installments\":[");
		for (

				int i = 0; i < installments.size(); i++) {
			json.append(installments.get(i).toJson());
			if (i < installments.size() - 1) {
				json.append(",");
			}
		}
		json.append("]");

		json.append("}");
		return json.toString();
	}

	// Método para converter uma lista de objetos Nota em JSON
	public static String listToJson(ArrayList<Nota> lista) {
		StringBuilder jsonArray = new StringBuilder();
		jsonArray.append("[");
		for (int i = 0; i < lista.size(); i++) {
			jsonArray.append(lista.get(i).toJson());
			if (i < lista.size() - 1) {
				jsonArray.append(",");
			}
		}
		jsonArray.append("]");
		return jsonArray.toString();
	}

}
