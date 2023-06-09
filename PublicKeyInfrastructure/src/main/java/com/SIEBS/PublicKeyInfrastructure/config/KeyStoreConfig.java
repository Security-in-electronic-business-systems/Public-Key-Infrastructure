package com.SIEBS.PublicKeyInfrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyStoreConfig {
    @Value("${pki.ca.keystore}")
    private String CAKeyStore;

    @Value("${pki.ee.keystore}")
    private String EEKeyStore;

    @Value("${pki.keystore.password}")
    private String keyStorePassword;
    
    /*public KeyStoreConfig() {
    	//this.keyStorePassword = System.getProperty("KEY_STORE_PASSWORD");
    }*/

    public String getCAKeyStore() {
        return CAKeyStore;
    }

    public void setCAKeyStore(String CAKeyStore) {
        this.CAKeyStore = CAKeyStore;
    }

    public String getEEKeyStore() {
        return EEKeyStore;
    }

    public void setEEKeyStore(String EEKeyStore) {
        this.EEKeyStore = EEKeyStore;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = System.getenv("KEY_STORE_PASSWORD");
    }
}
