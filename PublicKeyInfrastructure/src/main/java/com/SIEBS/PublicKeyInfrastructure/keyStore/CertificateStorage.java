package com.SIEBS.PublicKeyInfrastructure.keyStore;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import com.SIEBS.PublicKeyInfrastructure.config.KeyStoreConfig;
import com.SIEBS.PublicKeyInfrastructure.model.CertificateChain;
import com.SIEBS.PublicKeyInfrastructure.model.Issuer;

public class CertificateStorage {

	private  KeyStoreConfig config;
	private  KeyStore EEKeystore;
    private  KeyStore CAKeystore;

    public CertificateStorage(KeyStoreConfig config)
            throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException
    {
    	try {
    		//formiramo instancu key store fajlova za CA 
    		EEKeystore = KeyStore.getInstance("JKS", "SUN");
    		CAKeystore = KeyStore.getInstance("JKS", "SUN");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        try {
            this.EEKeystore.load(new FileInputStream(config.getEEKeyStore()), config.getKeyStorePassword().toCharArray());
        }
        catch (FileNotFoundException e) {
            this.EEKeystore.load(null, null);
        }

        try {
            this.CAKeystore.load(new FileInputStream(config.getCAKeyStore()), config.getKeyStorePassword().toCharArray());
        }
        catch (FileNotFoundException e) {
            this.CAKeystore.load(null, null);
        }

        this.config = config;
    }
    
    public void saveEEKeyStore() {
        try {
            this.EEKeystore.store(new FileOutputStream(config.getEEKeyStore()), config.getKeyStorePassword().toCharArray());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeInEEKeyStore(CertificateChain certificateChain) {
        try {
            this.EEKeystore.setKeyEntry(certificateChain.getCertificateChain()[0].getSerialNumber().toString(), certificateChain.getPrivateKey(), certificateChain.getCertificateChain()[0].getSerialNumber().toString().toCharArray(), certificateChain.getCertificateChain());
            saveEEKeyStore();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }
    
    public void saveCAKeyStore() {
        try {
            this.CAKeystore.store(new FileOutputStream(config.getCAKeyStore()), config.getKeyStorePassword().toCharArray());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeInCAKeyStore(CertificateChain certificateChain) {
        try {
            this.CAKeystore.setKeyEntry(certificateChain.getCertificateChain()[0].getSerialNumber().toString(), certificateChain.getPrivateKey(), certificateChain.getCertificateChain()[0].getSerialNumber().toString().toCharArray(), certificateChain.getCertificateChain());
            saveCAKeyStore();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }
    
    public Issuer readIssuerFromStore(String alias) {
        try {
            //Iscitava se sertifikat koji ima dati alias
            Certificate cert = this.CAKeystore.getCertificate(alias);

            //Iscitava se privatni kljuc vezan za javni kljuc koji se nalazi na sertifikatu sa datim aliasom
            
            //alias mi je serijski broj, za sad sam stavio da mi je to i password za kljuc, ispravicu to
            PrivateKey privateKey = (PrivateKey) this.CAKeystore.getKey(alias, alias.toCharArray());

            X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
            return new Issuer(privateKey, cert.getPublicKey(), issuerName);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } 
        return null;
    }
    
    public Certificate readCertificateFromKeyStore(String alias) {
        try {
        	if(this.EEKeystore.isKeyEntry(alias)) {
                Certificate cert = this.EEKeystore.getCertificate(alias);
                return cert;
            }else {
            	Certificate cert = this.CAKeystore.getCertificate(alias);
                return cert;
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public PrivateKey readPrivateKey(String alias, String pass) throws NoSuchAlgorithmException {
        try {
            if(this.CAKeystore.isKeyEntry(alias)) {
                PrivateKey pk = (PrivateKey) this.CAKeystore.getKey(alias, alias.toCharArray());
                return pk;
            }else {
            	PrivateKey pk = (PrivateKey) this.EEKeystore.getKey(alias, alias.toCharArray());
                return pk;
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
