package com.SIEBS.PublicKeyInfrastructure.service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.stereotype.Service;

import com.SIEBS.PublicKeyInfrastructure.dto.CertificateRequestDTO;
import com.SIEBS.PublicKeyInfrastructure.dto.IssuerInfoDTO;
import com.SIEBS.PublicKeyInfrastructure.enumeration.CertificateType;
import com.SIEBS.PublicKeyInfrastructure.keyStore.CertificateStorage;
import com.SIEBS.PublicKeyInfrastructure.model.Certificate;
import com.SIEBS.PublicKeyInfrastructure.model.CertificateChain;
import com.SIEBS.PublicKeyInfrastructure.model.Issuer;
import com.SIEBS.PublicKeyInfrastructure.model.Subject;

@Service
public class CertificateService {
	
	private final CertificateGenerator certificateGenerator;
	private final CertificateStorage certificateStorage;
	
	public CertificateService(CertificateGenerator certificateGenerator, CertificateStorage storage) {
		this.certificateGenerator = certificateGenerator;
		this.certificateStorage = storage;
	}
	
	public String generateAndSaveCertificate(CertificateRequestDTO certData) {
		KeyPair keyPairSubject = generateKeyPair();
		Subject subject = generateSubject(certData, keyPairSubject);
		Issuer issuer;
		if(certData.getType() == CertificateType.SELF_SIGNED) {
			issuer = new Issuer(keyPairSubject.getPrivate(), keyPairSubject.getPublic(), subject.getX500Name());
			CertificateChain cert = certificateGenerator.generateCertificate(subject, issuer, certData.getValidFrom(), certData.getValidTo());
			
			this.certificateStorage.writeInCAKeyStore(cert);
		}else {
			//nabavljas issuera na osnovu serijskog broja iz baze
			issuer = generateIssuer();
			
			CertificateChain cert = certificateGenerator.generateCertificate(subject, issuer, certData.getValidFrom(), certData.getValidTo());
			
			if(certData.getType() == CertificateType.INTERMEDIATE) {
				this.certificateStorage.writeInCAKeyStore(cert);
			}else {
				this.certificateStorage.writeInEEKeyStore(cert);
			}
		}
		
		return "";
	}
	
	public Subject generateSubject(CertificateRequestDTO certData, KeyPair keyPairSubject) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, certData.getCn());
        builder.addRDN(BCStyle.SURNAME, certData.getSurname());
        builder.addRDN(BCStyle.GIVENNAME, certData.getName());
        builder.addRDN(BCStyle.O, certData.getO());
        builder.addRDN(BCStyle.OU, certData.getOn());
        builder.addRDN(BCStyle.C, certData.getC());
        builder.addRDN(BCStyle.E, certData.getEmail());
        builder.addRDN(BCStyle.TELEPHONE_NUMBER, certData.getPhoneNumber());
        builder.addRDN(BCStyle.UID, UUID.randomUUID().toString());

        return new Subject(keyPairSubject.getPublic(), builder.build());
    }
	
	public Issuer generateIssuer() {
        KeyPair kp = generateKeyPair();
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "IT sluzba");
        builder.addRDN(BCStyle.SURNAME, "sluzba");
        builder.addRDN(BCStyle.GIVENNAME, "IT");
        builder.addRDN(BCStyle.O, "UNS-FTN");
        builder.addRDN(BCStyle.OU, "Katedra za informatiku");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "itsluzba@uns.ac.rs");
        builder.addRDN(BCStyle.UID, "654321");
        return new Issuer(kp.getPrivate(), kp.getPublic(), builder.build());
    }
	
	private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

	public List<IssuerInfoDTO> getAllValidIsuers() {
		List<X509Certificate> certificates = this.certificateStorage.getAllValidIsuers();
		return getIssuerInfo(certificates);
		
	}
	
	private List<IssuerInfoDTO> getIssuerInfo(List<X509Certificate> l){
		List<IssuerInfoDTO> retList = new ArrayList<IssuerInfoDTO>();
		for(X509Certificate c : l) {
			IssuerInfoDTO info = new IssuerInfoDTO(c.getSerialNumber().toString(), c.getSubjectX500Principal().getName());
			retList.add(info);
		}
		return retList;
	}

}
