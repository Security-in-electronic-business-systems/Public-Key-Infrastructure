package com.SIEBS.PublicKeyInfrastructure.controller;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import com.SIEBS.PublicKeyInfrastructure.dto.CertificateResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import com.SIEBS.PublicKeyInfrastructure.dto.CertificateRequestDTO;
import com.SIEBS.PublicKeyInfrastructure.dto.IssuerInfoDTO;
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
	@CrossOrigin(origins = "*")
	@GetMapping("/getAll")
	public List<CertificateResponseDTO> getAll() throws CertificateException {
		List<X509Certificate> X509list = certificateService.getAll();
		List<CertificateResponseDTO> DTOlist = new ArrayList<>();
 		for (X509Certificate x509:X509list) {
			var certificate = certificateService.mapToDTO(x509);
			DTOlist.add(certificate);
		}
		 return DTOlist;
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/getBySerialNumber")
	public CertificateResponseDTO getBySerialNumber(@RequestBody String jsonString) throws CertificateException {
		int colonIndex = jsonString.indexOf(":");
		return certificateService.getBySerialNumber(jsonString.substring(colonIndex + 2,jsonString.length() - 2));
	}
	
	@CrossOrigin(origins = "http://127.0.0.1:5173")
	@PostMapping("/revokeCertificate")
    public void revokeCertificate(@RequestBody String serialNumber) {
    //    certificateService.revokeCertificate(serialNumber);
    }

	public List<IssuerInfoDTO> getAllValidIsuers(){
		return this.certificateService.getAllValidIsuers();
	}
}

