package com.SIEBS.PublicKeyInfrastructure.service;

import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.Extension;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.stereotype.Service;

import com.SIEBS.PublicKeyInfrastructure.dto.CertificateRequestDTO;
import com.SIEBS.PublicKeyInfrastructure.dto.CertificateResponseDTO;
import com.SIEBS.PublicKeyInfrastructure.dto.IssuerInfoDTO;
import com.SIEBS.PublicKeyInfrastructure.enumeration.CertificateType;
import com.SIEBS.PublicKeyInfrastructure.enumeration.ExtendedKeyPurpose;
import com.SIEBS.PublicKeyInfrastructure.enumeration.KeyPurpose;
import com.SIEBS.PublicKeyInfrastructure.keyStore.CertificateStorage;
import com.SIEBS.PublicKeyInfrastructure.model.Certificate;
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
	
	public String generateAndSaveCertificate(CertificateRequestDTO certData) throws IOException {
		KeyPair keyPairSubject = generateKeyPair();
		Subject subject = generateSubject(certData, keyPairSubject);
		Issuer issuer;
		List<org.bouncycastle.asn1.x509.Extension> extensions = findExtensionsFromRequest(certData);
		if(certData.getType() == CertificateType.SELF_SIGNED) {
			issuer = new Issuer(keyPairSubject.getPrivate(), keyPairSubject.getPublic(), subject.getX500Name());
			CertificateChain cert = certificateGenerator.generateCertificate(subject, issuer, certData.getValidFrom(), certData.getValidTo(), extensions);
			
			this.certificateStorage.writeInCAKeyStore(cert);
			CertificateBaseInfo certBaseInfo = new CertificateBaseInfo(false, CertificateType.SELF_SIGNED, cert.getCertificateChain()[0].getSerialNumber().toString());
			this.certificateBaseInfoRepository.save(certBaseInfo);
			
		}else {
			//nabavljas issuera na osnovu serijskog broja iz key stora
			issuer = certificateStorage.readIssuerFromStore(certData.getIssuer());
			
			CertificateChain cert = certificateGenerator.generateCertificate(subject, issuer, certData.getValidFrom(), certData.getValidTo(), extensions);
			
			if(certData.getType() == CertificateType.INTERMEDIATE) {
				this.certificateStorage.writeInCAKeyStore(cert);
				CertificateBaseInfo certBaseInfo = new CertificateBaseInfo(false, CertificateType.INTERMEDIATE, cert.getCertificateChain()[0].getSerialNumber().toString());
				this.certificateBaseInfoRepository.save(certBaseInfo);
			}else {
				this.certificateStorage.writeInEEKeyStore(cert);
				CertificateBaseInfo certBaseInfo = new CertificateBaseInfo(false, CertificateType.END_ENTITY, cert.getCertificateChain()[0].getSerialNumber().toString());
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
		dto.setExtendedKeyUsage(x509.getExtendedKeyUsage());
		dto.setKeyUsage(x509.getKeyUsage());
		
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

	public X509Certificate getX509BySerialNumber(String serialNum){
		X509Certificate x509certificate = (X509Certificate) certificateStorage
				.readCertificateFromKeyStore(serialNum);
		return x509certificate;
	}

	
	public List<org.bouncycastle.asn1.x509.Extension> findExtensionsFromRequest(CertificateRequestDTO certificateRequestDTO) throws IOException{
		List<org.bouncycastle.asn1.x509.Extension> extensions = new ArrayList<>();
		List<KeyPurposeId> extendedKeyUsageIds = new ArrayList<>();
		
		System.out.println("CONVERT EXTENSION STARTED...");
		if(certificateRequestDTO.isClientAuth() == true){
			extendedKeyUsageIds.add(ExtendedKeyPurpose.CLIENT_AUTH.getKeyPurposeId());
		}
		if(certificateRequestDTO.isServerAuth() == true){
			extendedKeyUsageIds.add(ExtendedKeyPurpose.SERVER_AUTH.getKeyPurposeId());
		}
		if(certificateRequestDTO.isCodeSign() == true){
			extendedKeyUsageIds.add(ExtendedKeyPurpose.CODE_SIGNING.getKeyPurposeId());
		}
		if(certificateRequestDTO.isEmailProtection() == true){
			extendedKeyUsageIds.add(ExtendedKeyPurpose.EMAIL_PROTECTION.getKeyPurposeId());
		}
		if(certificateRequestDTO.isTimeStamping() == true){
			extendedKeyUsageIds.add(ExtendedKeyPurpose.TIME_STAMPING.getKeyPurposeId());
		}
		if(certificateRequestDTO.isOcspSigning() == true){
			extendedKeyUsageIds.add(ExtendedKeyPurpose.OCSP_SIGNING.getKeyPurposeId());
		}
		
		
		List<Integer> keyUsageIds = new ArrayList<>();
		
		if(certificateRequestDTO.isDigitalSignature() == true){
			keyUsageIds.add(KeyPurpose.DIGITAL_SIGNATURE.getKeyUsageId());
		}
		if(certificateRequestDTO.isNonRepudiation() == true){
			keyUsageIds.add(KeyPurpose.NON_REPUDIATION.getKeyUsageId());
		}
		if(certificateRequestDTO.isKeyEnciphement() == true){
			keyUsageIds.add(KeyPurpose.KEY_ENCIPHERMENT.getKeyUsageId());
		}
		if(certificateRequestDTO.isDataEnciphement() == true){
			keyUsageIds.add(KeyPurpose.DATA_ENCIPHERMENT.getKeyUsageId());
		}
		if(certificateRequestDTO.isKeyAgriment() == true){
			keyUsageIds.add(KeyPurpose.KEY_AGREEMENT.getKeyUsageId());
		}
		if(certificateRequestDTO.isKeyCertSign() == true){
			keyUsageIds.add(KeyPurpose.KEY_CERT_SIGN.getKeyUsageId());
		}
		if(certificateRequestDTO.isEnhipterOnly() == true){
			keyUsageIds.add(KeyPurpose.ENCIPHER_ONLY.getKeyUsageId());
		}
		if(certificateRequestDTO.isDecipherOnly() == true){
			keyUsageIds.add(KeyPurpose.DECIPHER_ONLY.getKeyUsageId());
		}
		if(extendedKeyUsageIds.isEmpty() && keyUsageIds.isEmpty()) {
			return null;
		}
		
		if (extendedKeyUsageIds.isEmpty()==false) {
			org.bouncycastle.asn1.x509.Extension extendedExtension = new org.bouncycastle.asn1.x509.Extension(
					org.bouncycastle.asn1.x509.Extension.extendedKeyUsage, 
					certificateRequestDTO.isCriticalExtended(),
					new DEROctetString(new ExtendedKeyUsage(extendedKeyUsageIds.toArray(new KeyPurposeId[extendedKeyUsageIds.size()]))));
			extensions.add(extendedExtension);
		}
		
		if (!keyUsageIds.isEmpty()) {
		    int usage = 0;
		    for (Integer id : keyUsageIds) {
		        usage |= id;
		    }
		    org.bouncycastle.asn1.x509.Extension keyUsageExtension = new org.bouncycastle.asn1.x509.Extension(
		            org.bouncycastle.asn1.x509.Extension.keyUsage, 
		            certificateRequestDTO.isCritical(), 
		            new DEROctetString(new KeyUsage(usage)));
		    extensions.add(keyUsageExtension);
		}

		return extensions;
	}
	
	
	
}
