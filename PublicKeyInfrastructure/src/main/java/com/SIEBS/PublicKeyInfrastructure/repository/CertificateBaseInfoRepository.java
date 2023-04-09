package com.SIEBS.PublicKeyInfrastructure.repository;

import com.SIEBS.PublicKeyInfrastructure.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SIEBS.PublicKeyInfrastructure.model.CertificateBaseInfo;

import java.util.List;

@Repository
public interface CertificateBaseInfoRepository extends JpaRepository<CertificateBaseInfo, Long> {
    @Query(value = "select c from CertificateBaseInfo c where c.serialNumber = ?1")
    CertificateBaseInfo findBySerialNumber(String serialNumber);

}
