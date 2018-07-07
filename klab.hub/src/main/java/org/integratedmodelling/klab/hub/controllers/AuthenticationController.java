package org.integratedmodelling.klab.hub.controllers;

import java.util.stream.Collectors;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.hub.authentication.AuthenticationManager;
import org.integratedmodelling.klab.hub.network.NetworkManager;
import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.IdentityReference;
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
	AuthenticationManager authenticationManager;

	@Autowired
	NetworkManager networkManager;
	
	@RequestMapping(value = API.HUB.AUTHENTICATE_ENGINE, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> authenticate(@RequestBody EngineAuthenticationRequest request) {

		IUserIdentity user = authenticationManager.authenticateEngineCertificate(request.getCertificate(), request.getLevel());

		if (user != null) {

			/*
			 * good enough for now. True auth must unencrypt and validate the unencrypted
			 * certificate with all the clear-text properties and key in addition to produce
			 * a (persisted) token.
			 */
			DateTime now = DateTime.now();
			DateTime tomorrow = now.plusDays(1);

			IdentityReference userIdentity = new IdentityReference(user.getUsername(), user.getEmailAddress(),
					now.toString());
			AuthenticatedIdentity authenticatedIdentity = new AuthenticatedIdentity(userIdentity,
					user.getGroups().stream().map(g -> new Group(g)).collect(Collectors.toSet()), tomorrow.toString(),
					user.getId());

			/*
			 * TODO if user is new, propagate to authenticated servers
			 */

			return new ResponseEntity<EngineAuthenticationResponse>(
					new EngineAuthenticationResponse(authenticatedIdentity, networkManager.getNodes(user.getGroups()), ""),
					HttpStatus.OK);
		}
		return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
	}

	// This is for node authentication
	// AuthenticatedIdentity identity = new AuthenticatedIdentity(userIdentity, new
	// ArrayList<>(),
	// tomorrow.toString(), NameGenerator.newName());
	// IPartnerIdentity partner = Klab.INSTANCE.getRootMonitor().getIdentity()
	// .getParentIdentity(IPartnerIdentity.class);
	// IdentityReference partnerIdentity = new IdentityReference(partner.getName(),
	// partner.getEmailAddress(),
	// now.toString());
	// INodeIdentity node = new Node("", partner);
	//
	// NodeReference thisnode = new NodeReference();
	//
	// thisnode.setId(node.getName());
	// thisnode.setPartner(partnerIdentity);
	// thisnode.getPermissions().addAll(node.getPermissions());
	// thisnode.getUrls().addAll(node.getUrls());
	// thisnode.setOnline(true);
	// thisnode.setRetryPeriodMinutes(20);

}
