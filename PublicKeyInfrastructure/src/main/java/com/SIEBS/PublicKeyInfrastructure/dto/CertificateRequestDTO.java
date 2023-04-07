package com.SIEBS.PublicKeyInfrastructure.dto;

import java.util.Date;

import com.SIEBS.PublicKeyInfrastructure.enumeration.CertificateType;

public class CertificateRequestDTO {

	private CertificateType type;
	private String issuer;
	private Date validFrom;
	private Date validTo;
	private String o;
	private String c;
	private String cn;
	private String on;
	private String name;
	private String surname;
	private String phoneNumber;
	private String email;
	
	
	
	public CertificateRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CertificateRequestDTO(CertificateType type, String issuer, Date validFrom, Date validTo, String cn,
			String o, String on, String c, String name, String surname, String phoneNumber, String email) {
		super();
		this.type = type;
		this.issuer = issuer;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.cn = cn;
		this.on = on;
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.o = o;
		this.c = c;
	}
	
	public String getO() {
		return o;
	}
	public void setO(String o) {
		this.o = o;
	}
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	public CertificateType getType() {
		return type;
	}
	public void setType(CertificateType type) {
		this.type = type;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public Date getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}
	public Date getValidTo() {
		return validTo;
	}
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getOn() {
		return on;
	}
	public void setOn(String on) {
		this.on = on;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
