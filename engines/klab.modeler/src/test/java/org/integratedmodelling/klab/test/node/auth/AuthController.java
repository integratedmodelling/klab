package org.integratedmodelling.klab.test.node.auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import org.integratedmodelling.kim.utils.NameGenerator;
import org.integratedmodelling.klab.API;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.auth.IPartnerIdentity;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.data.rest.resources.NodeReference;
import org.integratedmodelling.klab.data.rest.resources.requests.AuthenticatedIdentity;
import org.integratedmodelling.klab.data.rest.resources.requests.AuthenticationRequest;
import org.integratedmodelling.klab.data.rest.resources.requests.AuthenticationResponse;
import org.integratedmodelling.klab.data.rest.resources.requests.IdentityReference;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(
            value = API.AUTHENTICATE,
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {

        if (request.getCertificate().equals(certificate)) {

            // good enough for me

            DateTime now = DateTime.now();
            DateTime tomorrow = now.plusDays(1);

            IdentityReference userIdentity = new IdentityReference(username, email, now.toString());
            AuthenticatedIdentity identity = new AuthenticatedIdentity(userIdentity, new ArrayList<>(), tomorrow.toString(),
                    NameGenerator.newName());

            INodeIdentity node = Klab.INSTANCE.getRootMonitor().getIdentity().getParentIdentity(INodeIdentity.class);
            IPartnerIdentity partner = Klab.INSTANCE.getRootMonitor().getIdentity()
                    .getParentIdentity(IPartnerIdentity.class);
            IdentityReference partnerIdentity = new IdentityReference(partner.getName(), partner.getEmailAddress(), now.toString());

            NodeReference thisnode = new NodeReference(node.getName(), new HashSet<>(node.getPermissions()),
                    partnerIdentity, new ArrayList<>(node.getUrls()), true, 20, 0, new ArrayList<>(),
                    new ArrayList<>());

            return new ResponseEntity<AuthenticationResponse>(
                    new AuthenticationResponse(identity, Collections.singletonList(thisnode), node.getName()),
                    HttpStatus.OK);
        }
        return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

}
