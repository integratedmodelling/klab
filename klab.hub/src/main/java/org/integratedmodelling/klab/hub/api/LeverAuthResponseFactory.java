package org.integratedmodelling.klab.hub.api;

import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.auth.ILeverIdentity;
import org.integratedmodelling.klab.auth.Hub;
import org.integratedmodelling.klab.auth.Lever;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.hub.commands.GenerateHubReference;
import org.integratedmodelling.klab.hub.license.controllers.LeverService;
import org.integratedmodelling.klab.hub.tokens.services.LeverAuthTokenService;
import org.integratedmodelling.klab.hub.utils.IPUtils;
import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.integratedmodelling.klab.rest.HubReference;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.rest.LeverAuthenticationRequest;
import org.integratedmodelling.klab.rest.LeverAuthenticationResponse;

public class LeverAuthResponseFactory {

	public LeverAuthenticationResponse getResponse(
			LeverAuthenticationRequest request,
			LicenseConfiguration config,
			LeverService leverService,
			LeverAuthTokenService tokenService) {
		
		switch (request.getLevel()) {
		case TEST:
			if (IPUtils.isLocal(request.getIp()) && request.getCycle() == null) {
				return local(request, tokenService);
			} else {
				break;	
			}			
		default:
			break;
		}
		return null;
	}

	private LeverAuthenticationResponse local(LeverAuthenticationRequest request, LeverAuthTokenService tokenService) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime tomorrow = now.plusDays(90);
		
		ILeverIdentity lever = authenticateLocal(request.getName());
		
		IdentityReference userIdentity = new IdentityReference(lever.getName()
				,lever.getParentIdentity().getEmailAddress(), now.toString());
		
		AuthenticatedIdentity authenticatedIdentity = 
				new AuthenticatedIdentity(
						userIdentity,
						new ArrayList<>(), 
						tomorrow.toString(),
						lever.getName());
		
		
		HubReference hub = new GenerateHubReference().execute();
		TokenAuthentication token = tokenService.createToken(request.getName(), TokenType.lever);
		LeverAuthenticationResponse response = new LeverAuthenticationResponse();
		response.setUserData(authenticatedIdentity);
		response.setHubReference(hub);
		response.setToken(token.getTokenString());
		
		return response;
	
		
	}

	private ILeverIdentity authenticateLocal(String name) {
		Hub hub = Authentication.INSTANCE.getAuthenticatedIdentity(Hub.class);
		ILeverIdentity lever = new Lever(hub.getName() + "." + name, hub.getParentIdentity());
		try {
			lever.getUrls().add("http://"+IPUtils.getLocalIp()+":8300/lever");
		} catch (SocketException e) {
			throw new KlabAuthorizationException(lever.getName());
		}
		return lever;
	}

}
