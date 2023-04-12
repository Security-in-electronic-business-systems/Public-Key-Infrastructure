package com.SIEBS.PublicKeyInfrastructure.controller;

import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import com.SIEBS.PublicKeyInfrastructure.dto.CertificateResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import com.SIEBS.PublicKeyInfrastructure.dto.CertificateRequestDTO;
import com.SIEBS.PublicKeyInfrastructure.dto.IssuerInfoDTO;
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
	
	@CrossOrigin(origins = "*")
	@PostMapping("/revokeCertificate")
    public void revokeCertificate(@RequestBody String serialNumber) {
    //    certificateService.revokeCertificate(serialNumber);
    }
	
	@CrossOrigin(origins = "*")
	@GetMapping("/validate/{serialNumber}")
	public ResponseEntity<String> validate(@PathVariable String serialNumber) {
		String response = "Validation faild";
		if (certificateService.validate(serialNumber))	
			response = "Validation successful for certificate with serial number " + serialNumber;
	    return ResponseEntity.ok(response);
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/revoke/{serialNumber}")
	public void revoke(@PathVariable String serialNumber) {		
		certificateService.revoke(serialNumber);
	}

	public List<IssuerInfoDTO> getAllValidIsuers(){
		return this.certificateService.getAllValidIsuers();
	}

	@GetMapping("download/{serialNumber}")
	public ResponseEntity<?> download(@PathVariable String serialNumber){
		X509Certificate certificate = certificateService.getX509BySerialNumber(serialNumber);
		HttpHeaders headers = new HttpHeaders();

		headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename ="
				+ certificate.getSerialNumber().toString() + ".cer");

		try {
			ByteArrayResource resource =
					new ByteArrayResource(certificate.getEncoded());
			return ResponseEntity.ok()
					.headers(headers)
					.contentType(MediaType.parseMediaType("application/octet-stream"))
					.body(resource);

		}catch (CertificateEncodingException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}

