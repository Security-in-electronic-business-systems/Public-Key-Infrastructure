package com.SIEBS.PublicKeyInfrastructure.dto;

import com.SIEBS.PublicKeyInfrastructure.enumeration.CertificateType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.ConstructorParameters;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CertificateResponseDTO {

    private BigInteger serialNumber;
    private String issuerCN;
    private String subjectCN;
    private String certificateType;
    //private boolean revoked;
    private Date validFrom;
    private Date validTo;
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
	public CertificateResponseDTO(BigInteger serialNumber, String issuerCN, String subjectCN, String certificateType,
			Date validFrom, Date validTo) {
		super();
		this.serialNumber = serialNumber;
		this.issuerCN = issuerCN;
		this.subjectCN = subjectCN;
		this.certificateType = certificateType;
		this.validFrom = validFrom;
		this.validTo = validTo;
	}
	public CertificateResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
