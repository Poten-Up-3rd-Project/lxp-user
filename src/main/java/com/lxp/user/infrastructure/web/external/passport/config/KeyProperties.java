package com.lxp.user.infrastructure.web.external.passport.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@ConfigurationProperties(prefix = "passport.key")
public class KeyProperties {

    private String publicKeyString;

    @Getter
    private int durationMillis;

    public KeyProperties(String publicKeyString, int durationMillis) {
        this.publicKeyString = publicKeyString;
        this.durationMillis = durationMillis;
    }

    public PublicKey getPublicKey() throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

}
