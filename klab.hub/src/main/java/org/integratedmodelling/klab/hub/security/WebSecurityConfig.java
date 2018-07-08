package org.integratedmodelling.klab.hub.security;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String AUTHENTICATION_TOKEN_HEADER_NAME = "Authentication";

    /**
     * TODO incorporate this where? this allows us to assign our own HTTP response status code, by using an exception
     * class which is annotated with it directly. (otherwise we would rely on Spring's container logic to assign a
     * 'redirect' or other default status.)
     */
    AuthenticationFailureHandler authenticationFailureHandler = new AuthenticationFailureHandler() {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException e) throws IOException, ServletException {
            throw new KlabAuthorizationException("Authentication failed.");
        }
    };

    /**
     * The private key used for signing JWT tokens
     */
    protected PrivateKey privateKey;

    /**
     * The public key which is used for verifying JWT signatures (published via /jwks for other nodes)
     */
    protected PublicKey publicKey;

//    @Autowired
//    TokenHeaderProcessingFilter tokenHeaderProcessingFilter;

    /**
     * this is necessary so that AuthenticationManager injection points can be populated.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Logging.INSTANCE.info("HTTP config: disabling session management...");
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        http.csrf().disable();

//        logger.info("HTTP config: configuring token header authentication filter...");
//        http.addFilterBefore(tokenHeaderProcessingFilter, UsernamePasswordAuthenticationFilter.class);
    }

//    public abstract Collection<UserData> getInitialUsers();
//
//    public abstract String getIssuer();

//    /**
//     * A map of issuer > URL
//     * (where issuer is identical to what would be returned in the 'iss' claim of a JWT token)
//     * for all partner authentication issuers that have been approved by this node
//     */
//    public abstract Map<String, String> getJwksEndpointsByIssuer();
//
//    public Key getJwtPrivateKey() {
//        if (privateKey == null) {
//            try {
//                loadPrivateKey();
//            } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException | URISyntaxException e) {
//                String msg = "Failed to load private key";
//                logger.error(msg, e);
//                throw new CorruptOrBadDataException(msg, e);
//            }
//        }
//        return privateKey;
//    }
//
//    public abstract String getJwtPrivateKeyId();
//
//    public PublicKey getJwtPublicKey() {
//        if (publicKey == null) {
//            try {
//                loadPublicKey();
//            } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException | URISyntaxException e) {
//                String msg = "Failed to load public key";
//                logger.error(msg, e);
//                throw new CorruptOrBadDataException(msg, e);
//            }
//        }
//        return publicKey;
//    }

//    public Role getMappedRole(String role) {
//        return null;
//    }
//
//    protected abstract void loadPrivateKey()
//            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, URISyntaxException;
//
//    protected abstract void loadPublicKey()
//            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, URISyntaxException;
}