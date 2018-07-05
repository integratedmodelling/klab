package org.integratedmodelling.klab.test.node.auth;

import org.integratedmodelling.klab.api.auth.Roles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

/**
 * Mock authentication controller, will only authenticate the exact certificate in the classpath by comparing 
 * strings instead of decrypting a certificate.
 * <p>
 * Relies on the certificate string in testnode.cert being identical to that passed in the authentication request (it 
 * will be if the certificate of the authenticating party is read from testengine.cert). Everything else is accepted as is.
 * <p>
 * @author ferdinando.villa
 *
 */
@Controller
@Secured(Roles.PUBLIC)
@PropertySource("classpath:testengine.cert")
public class AuthController {

    @Value("${klab.certificate}")
    private String certificate;

    @Value("${klab.username}")
    private String username;

    @Value("${klab.user.email}")
    private String email;

}
