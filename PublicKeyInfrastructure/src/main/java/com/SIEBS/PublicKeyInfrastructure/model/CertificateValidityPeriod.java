package com.SIEBS.PublicKeyInfrastructure.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CertificateValidityPeriod {
    private Date startDate;
    private Date endDate;
}
