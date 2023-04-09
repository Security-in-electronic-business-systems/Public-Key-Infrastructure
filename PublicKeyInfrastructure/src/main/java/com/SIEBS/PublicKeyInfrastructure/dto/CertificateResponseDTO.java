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
}
