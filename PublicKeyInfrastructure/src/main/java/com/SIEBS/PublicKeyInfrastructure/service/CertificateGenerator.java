package com.SIEBS.PublicKeyInfrastructure.service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.UUID;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Component;

import com.SIEBS.PublicKeyInfrastructure.dto.CertificateRequestDTO;
import com.SIEBS.PublicKeyInfrastructure.model.Certificate;
import com.SIEBS.PublicKeyInfrastructure.model.CertificateChain;
import com.SIEBS.PublicKeyInfrastructure.model.CertificateValidityPeriod;
import com.SIEBS.PublicKeyInfrastructure.model.Issuer;
import com.SIEBS.PublicKeyInfrastructure.model.Subject;

import java.math.BigInteger;
import java.nio.ByteBuffer;

@Component
public class CertificateGenerator {

	private JcaContentSignerBuilder builder;

    public CertificateGenerator() {
        Security.addProvider(new BouncyCastleProvider());
        this.builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
        this.builder.setProvider("BC");
    }
    
    public CertificateChain generateCertificate(Subject subject, Issuer issuer, Date validFrom, Date validTo) {
    	try {
            ContentSigner contentSigner = builder.build(issuer.getPrivateKey());
            X509v3CertificateBuilder certificateBuilder =
                    new JcaX509v3CertificateBuilder(
                    		issuer.getX500Name(),
                    		generateSerialNumber(),
                            validFrom,
                            validTo,
                            subject.getX500Name(),
                            subject.getPublicKey());

            X509CertificateHolder certificateHolder = certificateBuilder.build(contentSigner);
            JcaX509CertificateConverter certificateConverter = new JcaX509CertificateConverter().setProvider("BC");
            return new CertificateChain(certificateConverter.getCertificate(certificateHolder), issuer.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return null;
    }
    
    private BigInteger generateSerialNumber() {
    	long now = System.currentTimeMillis();
    	return new BigInteger(Long.toString(now));
    }
}
