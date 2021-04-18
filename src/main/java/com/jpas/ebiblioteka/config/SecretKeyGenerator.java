package com.jpas.ebiblioteka.config;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;

public class SecretKeyGenerator {

    private static String secretKey;

    public void generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        secretKey = keyGenerator.generateKey().toString();
    }

    public String getSecretKey() {
        return secretKey;
    }
}
