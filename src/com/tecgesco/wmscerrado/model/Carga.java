package com.tecgesco.wmscerrado.model;

import java.util.ArrayList;

public class Carga {

	private String responsibleId = ""; // * id do motorista
	private String responsibleName = ""; // * nome do motorista
	private String helperId = ""; // id do ajudate
	private String helperName = ""; // nome do ajudante
	private String referenceId = ""; // * id da carga no erp
	private Route route = new Route();
	private Vehicle vehicle = new Vehicle();
	private String note = ""; // observação

	public String getResponsibleId() {
		return responsibleId;
	}

	public void setResponsibleId(String responsibleId) {
		this.responsibleId = responsibleId;
	}

	public String getResponsibleName() {
		return responsibleName;
	}

	public void setResponsibleName(String responsibleName) {
		this.responsibleName = responsibleName;
	}

	public String getHelperId() {
		return helperId;
	}

	public void setHelperId(String helperId) {
		this.helperId = helperId;
	}

	public String getHelperName() {
		return helperName;
	}

	public void setHelperName(String helperName) {
		this.helperName = helperName;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	// Método para converter um objeto em JSON usando StringBuilder
	public String toJson() {
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"responsibleId\":\"").append(responsibleId).append("\",");
		json.append("\"responsibleName\":\"").append(responsibleName).append("\",");
		json.append("\"helperId\":\"").append(helperId).append("\",");
		json.append("\"helperName\":\"").append(helperName).append("\",");
		json.append("\"referenceId\":\"").append(referenceId).append("\",");
		json.append("\"route\":{");
		json.append("\"id\":\"").append(route.id).append("\",");
		json.append("\"description\":\"").append(route.description).append("\"},");
		json.append("\"vehicle\":{");
		json.append("\"id\":\"").append(vehicle.id).append("\",");
		json.append("\"description\":\"").append(vehicle.description).append("\"},");
		json.append("\"note\":\"").append(note).append("\"");
		json.append("}");
		return json.toString();
	}

	// Método para converter uma lista de objetos em JSON usando StringBuilder
	public static String listToJson(ArrayList<Carga> list) {
		StringBuilder jsonArray = new StringBuilder();
		jsonArray.append("[");

		for (int i = 0; i < list.size(); i++) {
			jsonArray.append(list.get(i).toJson());
			if (i < list.size() - 1) {
				jsonArray.append(",");
			}
		}

		jsonArray.append("]");
		return jsonArray.toString();
	}

}
