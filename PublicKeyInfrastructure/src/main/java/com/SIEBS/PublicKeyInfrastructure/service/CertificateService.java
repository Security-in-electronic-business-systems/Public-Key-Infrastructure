package com.SIEBS.PublicKeyInfrastructure.service;

import java.io.ByteArrayInputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import com.SIEBS.PublicKeyInfrastructure.dto.CertificateRequestDTO;
import com.SIEBS.PublicKeyInfrastructure.dto.CertificateResponseDTO;
import com.SIEBS.PublicKeyInfrastructure.dto.IssuerInfoDTO;
import com.SIEBS.PublicKeyInfrastructure.enumeration.CertificateType;
import com.SIEBS.PublicKeyInfrastructure.keyStore.CertificateStorage;
import com.SIEBS.PublicKeyInfrastructure.model.CertificateBaseInfo;
import com.SIEBS.PublicKeyInfrastructure.model.CertificateChain;
import com.SIEBS.PublicKeyInfrastructure.model.Issuer;
import com.SIEBS.PublicKeyInfrastructure.model.Subject;
import com.SIEBS.PublicKeyInfrastructure.repository.CertificateBaseInfoRepository;


@Service
public class CertificateService {
	
	private final CertificateGenerator certificateGenerator;
	private final CertificateStorage certificateStorage;
	private final CertificateBaseInfoRepository certificateBaseInfoRepository;
	
	public CertificateService(CertificateGenerator certificateGenerator, CertificateStorage storage, CertificateBaseInfoRepository repo) {
		this.certificateGenerator = certificateGenerator;
		this.certificateStorage = storage;
		this.certificateBaseInfoRepository = repo;
	}
	
	public String generateAndSaveCertificate(CertificateRequestDTO certData) {
		KeyPair keyPairSubject = generateKeyPair();
		Subject subject = generateSubject(certData, keyPairSubject);
		Issuer issuer;
		String hierarchyChain = Long.toHexString(certificateBaseInfoRepository.count());
		if(certData.getType() == CertificateType.SELF_SIGNED) {
			issuer = new Issuer(keyPairSubject.getPrivate(), keyPairSubject.getPublic(), subject.getX500Name());
			CertificateChain cert = certificateGenerator.generateCertificate(subject, issuer, certData.getValidFrom(), certData.getValidTo());
			
			this.certificateStorage.writeInCAKeyStore(cert);
			CertificateBaseInfo certBaseInfo = new CertificateBaseInfo(false, CertificateType.SELF_SIGNED, cert.getCertificateChain()[0].getSerialNumber().toString(), hierarchyChain);
			this.certificateBaseInfoRepository.save(certBaseInfo);
			
		}else {
			//nabavljas issuera na osnovu serijskog broja iz key stora
			issuer = certificateStorage.readIssuerFromStore(certData.getIssuer());
			
			CertificateChain cert = certificateGenerator.generateCertificate(subject, issuer, certData.getValidFrom(), certData.getValidTo());
			hierarchyChain += "+" ;
			if(certData.getType() == CertificateType.INTERMEDIATE) {
				this.certificateStorage.writeInCAKeyStore(cert);
				CertificateBaseInfo certBaseInfo = new CertificateBaseInfo(false, CertificateType.INTERMEDIATE, cert.getCertificateChain()[0].getSerialNumber().toString(), hierarchyChain);
				this.certificateBaseInfoRepository.save(certBaseInfo);
			}else {
				this.certificateStorage.writeInEEKeyStore(cert);
				CertificateBaseInfo certBaseInfo = new CertificateBaseInfo(false, CertificateType.END_ENTITY, cert.getCertificateChain()[0].getSerialNumber().toString(), hierarchyChain);
				this.certificateBaseInfoRepository.save(certBaseInfo);
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

	public List<X509Certificate> getAll(){
		var certificates = certificateBaseInfoRepository.findAll();
		return findCertificates(certificates);
	}

	public List<X509Certificate> findCertificates(List<CertificateBaseInfo> certificates){
		ArrayList<X509Certificate> foundCertificates = new ArrayList<>();

		for (var certificate : certificates) {
			X509Certificate x509certificate = (X509Certificate) certificateStorage
					.readCertificateFromKeyStore(certificate.getSerialNumber());
			foundCertificates.add(x509certificate);
		}
		return foundCertificates;
	}

	public CertificateResponseDTO mapToDTO(X509Certificate x509) throws CertificateException {
		CertificateResponseDTO dto = new CertificateResponseDTO();
		dto.setIssuerCN(getIssuerCn(x509));
		dto.setSubjectCN(getSubjectCn(x509));
		dto.setCertificateType(x509.getType());
		dto.setValidTo(x509.getNotAfter());
		dto.setValidFrom(x509.getNotBefore());
		dto.setSerialNumber(x509.getSerialNumber());
		return dto;
	}
	
	private CertificateResponseDTO mapToDTOWithType(X509Certificate x509, CertificateType type) throws CertificateException {
		CertificateResponseDTO dto = new CertificateResponseDTO();
		dto.setIssuerCN(getIssuerCn(x509));
		dto.setSubjectCN(getSubjectCn(x509));
		dto.setCertificateType(type.toString());
		dto.setValidTo(x509.getNotAfter());
		dto.setValidFrom(x509.getNotBefore());
		dto.setSerialNumber(x509.getSerialNumber());
		return dto;
	}

	public static String getIssuerCn(X509Certificate cert) throws CertificateException {
		X509CertificateHolder certHolder = null;
		try {
			certHolder = new JcaX509CertificateHolder(cert);
			X500Name issuer = certHolder.getIssuer();
			return issuer.getRDNs(org.bouncycastle.asn1.x500.style.BCStyle.CN)[0].getFirst().getValue().toString();
		} catch (CertificateEncodingException e) {
			throw new CertificateException("Failed to get issuer CN", e);
		}
	}

	public static String getSubjectCn(X509Certificate cert) throws CertificateEncodingException {
		X509CertificateHolder certHolder = null;
		try {
			certHolder = new JcaX509CertificateHolder(cert);
			X500Name subject = certHolder.getSubject();
			return subject.getRDNs(org.bouncycastle.asn1.x500.style.BCStyle.CN)[0].getFirst().getValue().toString();
		} catch (CertificateEncodingException e) {
			throw new CertificateEncodingException("Failed to get subject CN", e);
		}
	}

	public CertificateResponseDTO getBySerialNumber(String serialNum) throws CertificateException {
		CertificateBaseInfo certificate = certificateBaseInfoRepository.findBySerialNumber(serialNum);
		if (certificate != null){
			X509Certificate x509certificate = (X509Certificate) certificateStorage
					.readCertificateFromKeyStore(certificate.getSerialNumber());
			return mapToDTOWithType(x509certificate, certificate.getCertificateType());
		}else{
			return null;
		}
	}
	
	public boolean validate (String serialNum) {
		CertificateBaseInfo certificate = certificateBaseInfoRepository.findBySerialNumber(serialNum);
		boolean valid = true;
		if (certificate != null){
			// da li je sertifikat povucen
			if (certificate.isRevoked()) {
				valid = false;
			}
			
			X509Certificate x509certificate = (X509Certificate) certificateStorage
					.readCertificateFromKeyStore(certificate.getSerialNumber());
			
			// provera da li je istekao sertifikat ili da li jos uvek nije validan
			 try {
				 x509certificate.checkValidity();
		        } catch (CertificateExpiredException e) {
		        	valid = false;
		        } catch (CertificateNotYetValidException e) {
		        	valid = false;
		        }
			 			 			 
		} else {
			valid = false;
		}
		return valid;		
	}
	
	public void revoke (String serialNum) {
		CertificateBaseInfo certificate = certificateBaseInfoRepository.findBySerialNumber(serialNum);
		certificate.setRevoked(true);
		certificateBaseInfoRepository.save(certificate);
        List<CertificateBaseInfo> childCerts = certificateBaseInfoRepository.findByChainIdLike(certificate.getHierarchyChain());
        for (var child : childCerts) child.setRevoked(true);
		
	}

	public X509Certificate getX509BySerialNumber(String serialNum){
		X509Certificate x509certificate = (X509Certificate) certificateStorage
				.readCertificateFromKeyStore(serialNum);
		return x509certificate;
	}

}
