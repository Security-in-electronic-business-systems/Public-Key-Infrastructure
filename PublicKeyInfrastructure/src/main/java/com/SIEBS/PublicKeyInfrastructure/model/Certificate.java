package com.SIEBS.PublicKeyInfrastructure.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.security.cert.X509Certificate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Certificate {

    private Subject subject;
    private Issuer issuer;
    private String serialNumber;
    private CertificateValidityPeriod validityPeriod;
    private X509Certificate x509Certificate;
    
}
