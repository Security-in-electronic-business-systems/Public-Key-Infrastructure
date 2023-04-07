package com.SIEBS.PublicKeyInfrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SIEBS.PublicKeyInfrastructure.model.CertificateBaseInfo;

@Repository
public interface CertificateBaseInfoRepository extends JpaRepository<CertificateBaseInfo, Long> {

	
}
