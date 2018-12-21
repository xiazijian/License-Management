package com._4paradigm.prophet.LicenseMangement.cipher;


import com._4paradigm.prophet.LicenseMangement.entity.License;
import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static com._4paradigm.prophet.rest.utils.Serdes.deserializeFromJson;
import static com._4paradigm.prophet.rest.utils.Serdes.serializeAsJsonBytes;
import static com._4paradigm.prophet.rest.utils.Validator.validateObjectNotNull;
import static com._4paradigm.prophet.rest.utils.Validator.validateStringNotBlank;
import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
public class RSALicenseCipher implements LicenseCipher {

    private static final String ALGORITHM = "RSA";

    private volatile String privateKey;
    private volatile String publicKey;

    @Override
    public License decrypt(String content) throws GeneralSecurityException {
        validateStringNotBlank(content, "encrypted license");
        String pk = getPublicKey();

        byte[] data = null;
        try {
            data = Base64.getDecoder().decode(content.getBytes(UTF_8));
        } catch (IllegalArgumentException e) {
            String msg = "invalid base64 content";
            log.error(msg, e);
            throw new IllegalArgumentException(msg);
        }

        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(
                Base64.getDecoder().decode(pk.getBytes(UTF_8)));
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return deserializeFromJson(cipher.doFinal(data), License.class);
    }

    @Override
    public String encrypt(License license) throws Exception {
        validateObjectNotNull(license, "license");
        String pk = getPrivateKey();
        byte[] data = serializeAsJsonBytes(license);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(
                Base64.getDecoder().decode(pk.getBytes(UTF_8)));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return new String(Base64.getEncoder().encode(cipher.doFinal(data)), UTF_8);
    }

    private String getPrivateKey() {
        if (privateKey == null) {
            synchronized (this) {
                if (privateKey == null) {
                    try {
                        privateKey = Resources.toString(Resources.getResource("key.pri"), UTF_8);
                    } catch (IOException e) {
                        throw new RuntimeException("can't load private key");
                    }
                }
            }
        }
        return privateKey;
    }

    private String getPublicKey() {
        if (publicKey == null) {
            synchronized (this) {
                if (publicKey == null) {
                    try {
                        publicKey = Resources.toString(Resources.getResource("key.pub"), UTF_8);
                    } catch (IOException e) {
                        throw new RuntimeException("can't load public key");
                    }
                }
            }
        }
        return publicKey;
    }
}
