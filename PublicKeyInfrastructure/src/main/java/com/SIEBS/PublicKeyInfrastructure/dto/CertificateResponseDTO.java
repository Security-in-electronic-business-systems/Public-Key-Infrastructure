package com.SIEBS.PublicKeyInfrastructure.dto;

import com.SIEBS.PublicKeyInfrastructure.enumeration.CertificateType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.ConstructorParameters;

import org.bouncycastle.asn1.x509.Extension;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class CertificateResponseDTO {

    private BigInteger serialNumber;
    private String issuerCN;
    private String subjectCN;
    private String certificateType;
    //private boolean revoked;
    private Date validFrom;
    private Date validTo;
    List<String> extendedKeyUsage;
    boolean[] keyUsage;
    
	public BigInteger getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(BigInteger serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getIssuerCN() {
		return issuerCN;
	}
	public void setIssuerCN(String issuerCN) {
		this.issuerCN = issuerCN;
	}
	public String getSubjectCN() {
		return subjectCN;
	}
	public void setSubjectCN(String subjectCN) {
		this.subjectCN = subjectCN;
	}
	public String getCertificateType() {
		return certificateType;
	}
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
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
//	public CertificateResponseDTO(BigInteger serialNumber, String issuerCN, String subjectCN, String certificateType,
//			Date validFrom, Date validTo) {
//		super();
//		this.serialNumber = serialNumber;
//		this.issuerCN = issuerCN;
//		this.subjectCN = subjectCN;
//		this.certificateType = certificateType;
//		this.validFrom = validFrom;
//		this.validTo = validTo;
//	}
	public CertificateResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<String> getExtendedKeyUsage() {
		return extendedKeyUsage;
	}
	public void setExtendedKeyUsage(List<String> extendedKeyUsage) {
		this.extendedKeyUsage = extendedKeyUsage;
	}
	public boolean[] getKeyUsage() {
		return keyUsage;
	}
	public void setKeyUsage(boolean[] keyUsage) {
		this.keyUsage = keyUsage;
	}
	public CertificateResponseDTO(BigInteger serialNumber, String issuerCN, String subjectCN, String certificateType,
			Date validFrom, Date validTo, List<String> extendedKeyUsage, boolean[] keyUsage) {
		super();
		this.serialNumber = serialNumber;
		this.issuerCN = issuerCN;
		this.subjectCN = subjectCN;
		this.certificateType = certificateType;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.extendedKeyUsage = extendedKeyUsage;
		this.keyUsage = keyUsage;
	}
	

    
    
}
