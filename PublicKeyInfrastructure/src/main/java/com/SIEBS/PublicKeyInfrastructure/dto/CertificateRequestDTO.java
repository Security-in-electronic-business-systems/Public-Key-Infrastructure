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
	
	private boolean serverAuth;
	private boolean clientAuth;
	private boolean codeSign;
	private boolean emailProtection;
	private boolean timeStamping;
	private boolean ocspSigning;

	private boolean digitalSignature;
	private boolean nonRepudiation;
	private boolean keyEnciphement;
	private boolean dataEnciphement;
	private boolean keyAgriment;
	private boolean keyCertSign;
	private boolean enhipterOnly;
	private boolean decipherOnly;
	
	public CertificateRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
//	public CertificateRequestDTO(CertificateType type, String issuer, Date validFrom, Date validTo, String cn,
//			String o, String on, String c, String name, String surname, String phoneNumber, String email) {
//		super();
//		this.type = type;
//		this.issuer = issuer;
//		this.validFrom = validFrom;
//		this.validTo = validTo;
//		this.cn = cn;
//		this.on = on;
//		this.name = name;
//		this.surname = surname;
//		this.phoneNumber = phoneNumber;
//		this.email = email;
//		this.o = o;
//		this.c = c;
//	}

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

	public boolean isServerAuth() {
		return serverAuth;
	}

	public void setServerAuth(boolean serverAuth) {
		this.serverAuth = serverAuth;
	}

	public boolean isClientAuth() {
		return clientAuth;
	}

	public void setClientAuth(boolean clientAuth) {
		this.clientAuth = clientAuth;
	}

	public boolean isCodeSign() {
		return codeSign;
	}

	public void setCodeSign(boolean codeSign) {
		this.codeSign = codeSign;
	}

	public boolean isEmailProtection() {
		return emailProtection;
	}

	public void setEmailProtection(boolean emailProtection) {
		this.emailProtection = emailProtection;
	}

	public boolean isTimeStamping() {
		return timeStamping;
	}

	public void setTimeStamping(boolean timeStamping) {
		this.timeStamping = timeStamping;
	}

	public boolean isOcspSigning() {
		return ocspSigning;
	}

	public void setOcspSigning(boolean ocspSigning) {
		this.ocspSigning = ocspSigning;
	}

	public boolean isDigitalSignature() {
		return digitalSignature;
	}

	public void setDigitalSignature(boolean digitalSignature) {
		this.digitalSignature = digitalSignature;
	}

	public boolean isNonRepudiation() {
		return nonRepudiation;
	}

	public void setNonRepudiation(boolean nonRepudiation) {
		this.nonRepudiation = nonRepudiation;
	}

	public boolean isKeyEnciphement() {
		return keyEnciphement;
	}

	public void setKeyEnciphement(boolean keyEnciphement) {
		this.keyEnciphement = keyEnciphement;
	}

	public boolean isDataEnciphement() {
		return dataEnciphement;
	}

	public void setDataEnciphement(boolean dataEnciphement) {
		this.dataEnciphement = dataEnciphement;
	}

	public boolean isKeyAgriment() {
		return keyAgriment;
	}

	public void setKeyAgriment(boolean keyAgriment) {
		this.keyAgriment = keyAgriment;
	}

	public boolean isKeyCertSign() {
		return keyCertSign;
	}

	public void setKeyCertSign(boolean keyCertSign) {
		this.keyCertSign = keyCertSign;
	}

	public boolean isEnhipterOnly() {
		return enhipterOnly;
	}

	public void setEnhipterOnly(boolean enhipterOnly) {
		this.enhipterOnly = enhipterOnly;
	}

	public boolean isDecipherOnly() {
		return decipherOnly;
	}

	public void setDecipherOnly(boolean decipherOnly) {
		this.decipherOnly = decipherOnly;
	}
	public CertificateRequestDTO(CertificateType type, String issuer, Date validFrom, Date validTo, String o, String c,
			String cn, String on, String name, String surname, String phoneNumber, String email, boolean serverAuth,
			boolean clientAuth, boolean codeSign, boolean emailProtection, boolean timeStamping, boolean ocspSigning,
			boolean digitalSignature, boolean nonRepudiation, boolean keyEnciphement, boolean dataEnciphement,
			boolean keyAgriment, boolean keyCertSign, boolean enhipterOnly, boolean decipherOnly) {
		super();
		this.type = type;
		this.issuer = issuer;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.o = o;
		this.c = c;
		this.cn = cn;
		this.on = on;
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.serverAuth = serverAuth;
		this.clientAuth = clientAuth;
		this.codeSign = codeSign;
		this.emailProtection = emailProtection;
		this.timeStamping = timeStamping;
		this.ocspSigning = ocspSigning;
		this.digitalSignature = digitalSignature;
		this.nonRepudiation = nonRepudiation;
		this.keyEnciphement = keyEnciphement;
		this.dataEnciphement = dataEnciphement;
		this.keyAgriment = keyAgriment;
		this.keyCertSign = keyCertSign;
		this.enhipterOnly = enhipterOnly;
		this.decipherOnly = decipherOnly;
	}		
}
