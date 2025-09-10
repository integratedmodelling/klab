package org.integratedmodelling.klab.hub.security;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public enum KlabJWTAuthentication {
    
    INSTANCE;
    
    private static final int ALLOWED_CLOCK_SKEW_MS = 30000;
    
    public void checkKlabJWT(HttpServletRequest req) throws KlabAuthorizationException {
 
        String token = req.getHeader(API.HUB.LABELS.AUTHORIZATION);
        // we are not entering via Keycloack Beader Token, check our own JWT
        if (token == null) {
            /*
             * Check own jwtToken
             */
            
            String jwtToken =req.getHeader(API.HUB.LABELS.AUTHENTICATION);
            
            if (jwtToken == null) {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Not authenticated");
            }
            
            PublicKey publicKey = null;

            try {
                byte publicKeyData[] = Base64.getDecoder().decode(NetworkKeyManager.INSTANCE.getEncodedPublicKey());
                X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyData);
                KeyFactory kf = KeyFactory.getInstance("RSA");
                publicKey = kf.generatePublic(spec);
            } catch (Exception e) {
                throw new KlabAuthorizationException("invalid public key");
            }
            
            /*
            * Build a verifier for the token coming from any engine that has validated with
            * the authenticating hub.
            */

            /*
             * Build a verifier for the token coming from any engine that has validated with
             * the authenticating hub.
             */

            JwtConsumer jwtVerifier = new JwtConsumerBuilder().setSkipDefaultAudienceValidation()
                    .setAllowedClockSkewInSeconds(ALLOWED_CLOCK_SKEW_MS / 1000).setVerificationKey(publicKey).build();

            try {

                JwtClaims claims = jwtVerifier.processToClaims(jwtToken);
                String username = claims.getSubject();

                /*
                 * Expiration time (exp) - The "exp" (expiration time) claim identifies the
                 * expiration time on or after which the JWT must not be accepted for
                 * processing. The value should be in NumericDate[10][11] format.
                 */
                NumericDate expirationTime = claims.getExpirationTime();
                long now = System.currentTimeMillis();
                if (expirationTime.isBefore(NumericDate.fromMilliseconds(now - ALLOWED_CLOCK_SKEW_MS))) {
                    throw new KlabAuthorizationException("User " + username + " is using an expired authorization");
                }


            } catch (MalformedClaimException | InvalidJwtException e) {
                e.printStackTrace();
                throw new KlabAuthorizationException("JWT error", e);
            } catch (Exception e) {
                throw new KlabAuthorizationException("Error validating JWT", e);
            }
        }
    }
    
}
