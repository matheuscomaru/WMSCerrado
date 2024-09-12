package com.tecgesco.wmscerrado.model;

public class ClienteEndereco {

	private String latitude; // latitude do endereco
	private String longitude; // longitude do endereco
	private String place; // * logradouro do endereco
	private String addressNumber; // * numero do endereco
	private String neighborhood; // * bairro do endereco
	private String cityName; // * cidade do endereco
	private String stateCode; // * uf do endereco
	private String zipCode; // * cep do endereco
	private String countryName; // * pais do endereco

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getAddressNumber() {
		return addressNumber;
	}

	public void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String toJson() {
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"latitude\":\"").append(latitude).append("\",");
		json.append("\"longitude\":\"").append(longitude).append("\",");
		json.append("\"place\":\"").append(place).append("\",");
		json.append("\"addressNumber\":\"").append(addressNumber).append("\",");
		json.append("\"neighborhood\":\"").append(neighborhood).append("\",");
		json.append("\"cityName\":\"").append(cityName).append("\",");
		json.append("\"stateCode\":\"").append(stateCode).append("\",");
		json.append("\"zipCode\":\"").append(zipCode).append("\",");
		json.append("\"countryName\":\"").append(countryName).append("\"");
		json.append("}");
		return json.toString();
	}

}
