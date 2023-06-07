package com.SIEBS.PublicKeyInfrastructure.model;



import com.SIEBS.PublicKeyInfrastructure.enumeration.CertificateType;

import jakarta.persistence.*;

@Entity
@Table
public class CertificateBaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean revoked;
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private CertificateType certificateType;
    
    private String hierarchyChain;

    public CertificateBaseInfo() {
    }



	public CertificateBaseInfo(boolean revoked,  CertificateType certificateType, String serialNumber, String hierarchyChain) {
		super();
		this.revoked = revoked;
		this.serialNumber = serialNumber;
		this.certificateType = certificateType;
		this.hierarchyChain = hierarchyChain;
	}



	public String getSerialNumber() {
		return serialNumber;
	}

	public String getHierarchyChain() {
		return hierarchyChain;
	}

	public void setHierarchyChain(String hierarchyChain) {
		this.hierarchyChain = hierarchyChain;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

	public CertificateType getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(CertificateType certificateType) {
		this.certificateType = certificateType;
	}
    
    
}