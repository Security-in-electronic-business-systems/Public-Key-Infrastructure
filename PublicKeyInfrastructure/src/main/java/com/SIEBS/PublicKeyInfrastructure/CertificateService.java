package com.SIEBS.PublicKeyInfrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SIEBS.PublicKeyInfrastructure.repository.CertificateBaseInfoRepository;



@Service
public class CertificateService {
	
	
	  private final CertificateBaseInfoRepository certificateBaseInfoRepository;
	  
	  @Autowired
	  public CertificateService(CertificateBaseInfoRepository certificateBaseInfoRepository) {
		  this.certificateBaseInfoRepository = certificateBaseInfoRepository; 
	  }
	  
	  
	 
}
