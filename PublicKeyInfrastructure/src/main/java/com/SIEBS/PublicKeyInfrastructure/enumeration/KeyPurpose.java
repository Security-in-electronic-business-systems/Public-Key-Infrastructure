package com.SIEBS.PublicKeyInfrastructure.enumeration;

import org.bouncycastle.asn1.x509.KeyUsage;

public enum KeyPurpose {
	//operacije za koje se sertifikat moze koristiti
	DIGITAL_SIGNATURE(KeyUsage.digitalSignature),
    NON_REPUDIATION(KeyUsage.nonRepudiation),
    KEY_ENCIPHERMENT(KeyUsage.keyEncipherment),
    DATA_ENCIPHERMENT(KeyUsage.dataEncipherment),
    KEY_AGREEMENT(KeyUsage.keyAgreement),
    KEY_CERT_SIGN(KeyUsage.keyCertSign),
    CRLSIGN(KeyUsage.cRLSign),
    ENCIPHER_ONLY(KeyUsage.encipherOnly),
    DECIPHER_ONLY(KeyUsage.decipherOnly);

    private int keyUsageId;

    KeyPurpose(int keyUsageId){
        this.keyUsageId = keyUsageId;
    }

    public int getKeyUsageId() {
        return keyUsageId;
    }
}
