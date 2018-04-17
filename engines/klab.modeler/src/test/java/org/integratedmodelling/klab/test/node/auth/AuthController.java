package org.integratedmodelling.klab.test.node.auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.utils.NameGenerator;
import org.integratedmodelling.klab.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.data.rest.resources.AuthenticatedIdentity;
import org.integratedmodelling.klab.data.rest.resources.IdentityReference;
import org.integratedmodelling.klab.data.rest.resources.NodeReference;
import org.integratedmodelling.klab.data.rest.resources.requests.AuthenticationRequest;
import org.integratedmodelling.klab.data.rest.resources.responses.AuthenticationResponse;
import org.integratedmodelling.klab.test.node.TestNode;
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
 * 
 * @author ferdinando.villa
 *
 */
@Controller
@Secured(Roles.PUBLIC)
@PropertySource("classpath:test.cert")
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

            Date now = new Date();
            Date tomorrow = new Date(now.getTime() + (1000 * 60 * 60 * 24));

            IdentityReference userIdentity = new IdentityReference(username, email, now);
            AuthenticatedIdentity identity = new AuthenticatedIdentity(userIdentity, new ArrayList<>(), tomorrow,
                    NameGenerator.newName());

            Set<INodeIdentity.Permission> permissions = new HashSet<>();
            NodeReference thisnode = new NodeReference(
                    TestNode.NODE_NAME, permissions, TestNode.PARTNER_IDENTITY,
                    Collections.singletonList(TestNode.NODE_URL), true, 20, 0, new ArrayList<>(), new ArrayList<>());

            return new ResponseEntity<AuthenticationResponse>(
                    new AuthenticationResponse(identity, Collections.singletonList(thisnode), TestNode.NODE_NAME),
                    HttpStatus.OK);
        }
        return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

}
