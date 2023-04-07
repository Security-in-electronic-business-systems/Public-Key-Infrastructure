package com.SIEBS.PublicKeyInfrastructure.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SIEBS.PublicKeyInfrastructure.dto.CertificateRequestDTO;
import com.SIEBS.PublicKeyInfrastructure.model.Certificate;
import com.SIEBS.PublicKeyInfrastructure.service.CertificateService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/certificate")
public class CertificateController {
	
	private CertificateService certificateService;
	
	public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }
	
	@CrossOrigin(origins = "*")
	@PostMapping("/save")
    public String save(@RequestBody CertificateRequestDTO certificateRequestDTO) {
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        return certificateService.generateAndSaveCertificate(certificateRequestDTO);
        
    }
	
	@CrossOrigin(origins = "http://127.0.0.1:5173")
	@PostMapping("/revokeCertificate")
    public void revokeCertificate(@RequestBody String serialNumber) {
    //    certificateService.revokeCertificate(serialNumber);
    }

}

