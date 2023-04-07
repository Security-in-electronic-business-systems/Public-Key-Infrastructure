package com.SIEBS.PublicKeyInfrastructure.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bouncycastle.asn1.x500.X500Name;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;

@Getter
@Setter
public class Issuer {
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private X500Name x500Name;
	public Issuer(PrivateKey privateKey, PublicKey publicKey, X500Name x500Name) {
		super();
		this.privateKey = privateKey;
		this.publicKey = publicKey;
		this.x500Name = x500Name;
	}
	public Issuer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}
	public PublicKey getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}
	public X500Name getX500Name() {
		return x500Name;
	}
	public void setX500Name(X500Name x500Name) {
		this.x500Name = x500Name;
	}
    
    
}
