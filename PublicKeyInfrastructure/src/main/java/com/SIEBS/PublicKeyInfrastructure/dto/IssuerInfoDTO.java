package com.SIEBS.PublicKeyInfrastructure.dto;

public class IssuerInfoDTO {

	private String serialNumber;
	private String commonName;
	
	public IssuerInfoDTO(String serialNumber, String commonName) {
		super();
		this.serialNumber = serialNumber;
		this.commonName = commonName;
	}
	public IssuerInfoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	
}
