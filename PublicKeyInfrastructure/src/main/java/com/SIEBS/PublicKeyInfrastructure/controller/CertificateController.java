package com.SIEBS.PublicKeyInfrastructure.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SIEBS.PublicKeyInfrastructure.CertificateService;
import com.SIEBS.PublicKeyInfrastructure.dto.CertificateRequestDTO;
import com.SIEBS.PublicKeyInfrastructure.model.Certificate;



@RestController
@RequestMapping("/api/certificate")
public class CertificateController {
	
	private CertificateService certificateService;
	
	public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }
	
	@CrossOrigin(origins = "http://127.0.0.1:5173")
	@PostMapping
    public Certificate save(@RequestBody CertificateRequestDTO certificateRequestDTO) {
        return null;
    }

}
