package org.integratedmodelling.klab.hub.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.auth.EngineUser;
import org.integratedmodelling.klab.hub.authentication.HubAuthenticationManager;
import org.integratedmodelling.klab.hub.authentication.GroupManager;
import org.integratedmodelling.klab.hub.network.NetworkManager;
import org.integratedmodelling.klab.hub.security.KeyManager;
import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.rest.NodeAuthenticationRequest;
import org.integratedmodelling.klab.rest.NodeAuthenticationResponse;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

	@Autowired
	HubAuthenticationManager hubAuthenticationManager;

	@Autowired
	NetworkManager networkManager;
	
	@Autowired
	GroupManager groupManager;

	@RequestMapping(value = API.HUB.AUTHENTICATE_ENGINE, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> authenticateEngine(@RequestBody EngineAuthenticationRequest request,
			HttpServletRequest httpRequest) {

		EngineUser user = hubAuthenticationManager.authenticateEngineCertificate(request, httpRequest.getLocalAddr());

		if (user != null) {

			/*
			 * this matches the user to a persistent token signed by this hub and ensures
			 * all nodes are aware of the user.
			 */
			user = hubAuthenticationManager.authorizeUser(user);

			/*
			 * good enough for now. True auth must unencrypt and validate the unencrypted
			 * certificate with all the clear-text properties and key in addition to produce
			 * a (persisted) token.
			 */
			DateTime now = DateTime.now();
			DateTime tomorrow = now.plusDays(90);

			IdentityReference userIdentity = new IdentityReference(user.getUsername(), user.getEmailAddress(),
					now.toString());
			AuthenticatedIdentity authenticatedIdentity = new AuthenticatedIdentity(userIdentity, user.getGroups(),
					tomorrow.toString(), user.getId());

			/*
			 * TODO if user is new, propagate to authenticated servers
			 */

			return new ResponseEntity<EngineAuthenticationResponse>(
					new EngineAuthenticationResponse(authenticatedIdentity, hubAuthenticationManager.getHubReference(),
							networkManager.getNodes(user.getGroups())),
					HttpStatus.OK);
		}
		return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = API.HUB.AUTHENTICATE_NODE, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> authenticateNode(@RequestBody NodeAuthenticationRequest request,
			HttpServletRequest httpRequest) {

		INodeIdentity node = hubAuthenticationManager.authenticateNodeCertificate(request, httpRequest.getLocalAddr());

		if (node != null) {

			/*
			 * good enough for now. True auth must unencrypt and validate the unencrypted
			 * certificate with all the clear-text properties and key in addition to produce
			 * a (persisted) token.
			 */
			DateTime now = DateTime.now();
			DateTime tomorrow = now.plusDays(90);

			IdentityReference userIdentity = new IdentityReference(node.getName(),
					node.getParentIdentity().getEmailAddress(), now.toString());
			AuthenticatedIdentity authenticatedIdentity = new AuthenticatedIdentity(userIdentity,
					/* TODO groups */new ArrayList<Group>(), tomorrow.toString(), node.getId());

			Logging.INSTANCE.info("authorized pre-installed node " + node.getName());

			NodeAuthenticationResponse response = new NodeAuthenticationResponse(authenticatedIdentity,
					hubAuthenticationManager.getHubReference().getId(), groupManager.getGroups(),
					KeyManager.INSTANCE.getEncodedPublicKey());

			networkManager.notifyAuthorizedNode(node, hubAuthenticationManager.getHubReference(), true);

			return new ResponseEntity<NodeAuthenticationResponse>(response, HttpStatus.OK);
		}
		return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
	}

}
