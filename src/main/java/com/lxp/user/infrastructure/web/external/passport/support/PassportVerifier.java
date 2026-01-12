package com.lxp.user.infrastructure.web.external.passport.support;

import com.lxp.user.infrastructure.constants.PassportConstants;
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
                claims.get(PassportConstants.PASSPORT_USER_ID).toString(),
                Arrays.asList(claims.get(PassportConstants.PASSPORT_ROLE).toString().split(",")),
                claims.get(PassportConstants.PASSPORT_TRACE_ID).toString()
            );

        } catch (ExpiredJwtException e) {
            throw new InvalidPassportException("Expired passport");

        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidPassportException("Invalid passport");
        }
    }

}
