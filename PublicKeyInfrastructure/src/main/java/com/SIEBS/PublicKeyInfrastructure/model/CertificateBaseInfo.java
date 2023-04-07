package com.SIEBS.PublicKeyInfrastructure.model;



import jakarta.persistence.*;

@Entity
@Table
public class CertificateBaseInfo {
    @Id
    @SequenceGenerator(
            name = "bloodbank_sequence",
            sequenceName = "bloodbank_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bloodbank_sequence"
    )
    private Long id;

    private boolean revoked;

    @Enumerated(EnumType.STRING)
    private CertificateType certificateType;

    public CertificateBaseInfo() {
    }

	public CertificateBaseInfo(Long id, boolean revoked, CertificateType certificateType) {
		super();
		this.id = id;
		this.revoked = revoked;
		this.certificateType = certificateType;
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