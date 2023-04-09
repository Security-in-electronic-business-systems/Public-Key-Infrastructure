package com.SIEBS.PublicKeyInfrastructure.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bouncycastle.asn1.x500.X500Name;
import org.springframework.stereotype.Component;

import java.security.PublicKey;

@Getter
@Setter
@NoArgsConstructor
public class Subject {
	private PublicKey publicKey;
    private X500Name x500Name;
    
    public Subject(PublicKey publicKey, X500Name bc) {
		this.publicKey = publicKey;
		this.x500Name = bc;
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

