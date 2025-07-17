package org.integratedmodelling.klab.hub.users.controllers;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.hub.emails.EmailConfig;
import org.integratedmodelling.klab.hub.emails.services.EmailManager;
import org.integratedmodelling.klab.hub.exception.MailAddressNotAllowedException;
import org.integratedmodelling.klab.hub.security.NetworkKeyManager;
import org.integratedmodelling.klab.rest.KlabEmail;
import org.integratedmodelling.klab.rest.KlabEmail.EmailType;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Controller for email related actions
 * Include template management
 * @author Enrico Girotto
 *
 */
@RestController
public class UserEmailController {

    @Autowired
    EmailManager emailManager;

    @Autowired
    private EmailConfig emailConfig;
    
    private static final int ALLOWED_CLOCK_SKEW_MS = 30000;

    /**
     * Send an email. Used by apps in engines.
     * @param sendEmail
     * @return confirmation or error
     */
    @PostMapping(value = API.HUB.USER_SEND_EMAIL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity< ? > sendEmail(@RequestBody KlabEmail email) {
        
        /* Authenticate with bearer token or our jwtToken */
        
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                .getHeader(API.HUB.LABELS.AUTHORIZATION);

        if (token == null) {
            /*
             * Check own jwtToken
             */
            
            String jwtToken = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                    .getHeader("Authentication");
            
            if (jwtToken == null) {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Not authenticated to send an email.");
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
                   throw new KlabAuthorizationException("user " + username + " is using an expired authorization");
               }


           } catch (MalformedClaimException | InvalidJwtException e) {
               e.printStackTrace();
           } catch (Exception e) {
               Logging.INSTANCE.error("Error validating JWT", e);
           }

        }

        // / Currently, the only email that can be used is the one used for SMTP. Therefore no
        // customization is available
        email.from = emailConfig.senderEmail();

        Set<String> recipients = new HashSet<String>();
        Set<String> replayToRecipients = new HashSet<String>();
        if (email.to != null && email.to.size() > 0) {
            for(String userEmail : email.to) {
                if (userEmail.length() > 0) {
                    if (!userEmail.contains("@")) {
                        userEmail = userEmail + "@" + emailConfig.defaultDomain();
                    }
                    if (Arrays.asList(emailConfig.getAllowedEmailAddresses()).contains(userEmail)) {
                        recipients.add(userEmail);
                    } else {
                        throw new MailAddressNotAllowedException(userEmail);
                    }
                }
            }
            if (recipients.size() == 0) {
                recipients.add(emailConfig.defaultRecipient());
            }
        } else {
            recipients.add(emailConfig.defaultRecipient());
        }
        if (email.replayTo != null && email.replayTo.size() > 0) {
            for(String replayTo : email.replayTo) {
                replayToRecipients.add(replayTo);
            }
        }

        emailManager.send(email.from, recipients, replayToRecipients, email.subject, email.content, email.type != EmailType.TEXT);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);

    }
}
