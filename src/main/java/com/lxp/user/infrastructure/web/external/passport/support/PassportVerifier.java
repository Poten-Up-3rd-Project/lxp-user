package com.lxp.user.infrastructure.web.external.passport.support;

import com.lxp.user.infrastructure.web.external.passport.config.KeyProperties;
import com.lxp.user.infrastructure.web.external.passport.exception.InvalidPassportException;
import com.lxp.user.infrastructure.web.external.passport.model.PassportClaims;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Arrays;

@Component
public class PassportVerifier {

    private final PublicKey publicKey;

    public PassportVerifier(KeyProperties keyProperties) throws Exception {
        this.publicKey = keyProperties.getPublicKey();
    }

    public PassportClaims verify(String encodedPassport) {
        try {
            Claims claims = Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(encodedPassport)
                .getPayload();

            return new PassportClaims(
                claims.get("userId").toString(),
                Arrays.asList(claims.get("roles").toString().split(",")),
                claims.get("traceId").toString()
            );

        } catch (ExpiredJwtException e) {
            throw new InvalidPassportException("Expired passport");

        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidPassportException("Invalid passport");
        }
    }

}
