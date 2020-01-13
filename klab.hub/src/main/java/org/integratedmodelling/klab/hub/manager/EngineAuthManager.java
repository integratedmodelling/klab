package org.integratedmodelling.klab.hub.manager;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.codec.DecoderException;
import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.auth.EngineUser;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.hub.authentication.HubAuthenticationManager;
import org.integratedmodelling.klab.hub.exception.AuthenticationFailedException;
import org.integratedmodelling.klab.hub.models.ProfileResource;
import org.integratedmodelling.klab.hub.models.User;
import org.integratedmodelling.klab.hub.network.NetworkManager;
import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.utils.IPUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EngineAuthManager {
	
	@Autowired
	LicenseManager licenseManager;
	
	@Autowired
	TokenManager tokenManager;
	
	@Autowired
	KlabUserManager klabUserManager;
	
	@Autowired
	JwtTokenManager jwtTokenManager;
	
	@Autowired
	HubAuthenticationManager hubAuthenticationManager;
	
	@Autowired
	NetworkManager networkManager;
	
	public EngineAuthenticationResponse processEngineCert(EngineAuthenticationRequest request, String ip) {
		switch (request.getLevel()) {
		case ANONYMOUS:
		case INSTITUTIONAL:
			break;
		case LEGACY:
			break;
		case TEST:
			if (IPUtils.isLocal(ip)) {
				return processLocalEngineUser(request);
			} else {
				break;	
			}
		case USER:
			if (IPUtils.isLocalhost(ip)) {
				//You are running locally with a hub, so it is assumed that the hub is a development hub
				return processLocalEngineUser(request);
			} else {
				return processEngineUser(request);
			}
		default:
			break;
		}
		return null;
	}

	private EngineAuthenticationResponse processEngineUser(EngineAuthenticationRequest request) {
		DateTime now = DateTime.now();
		DateTime tomorrow = now.plusDays(90);
		EngineUser engineUser = authenticateEngineCert(request);
		IdentityReference userIdentity = new IdentityReference(engineUser.getUsername(), engineUser.getEmailAddress(),
				now.toString());
		AuthenticatedIdentity authenticatedIdentity = new AuthenticatedIdentity(userIdentity, engineUser.getGroups(),
				tomorrow.toString(), engineUser.getId());
		return new EngineAuthenticationResponse(authenticatedIdentity, hubAuthenticationManager.getHubReference(),
				networkManager.getNodes(engineUser.getGroups()));
	}

	private EngineUser authenticateEngineCert(EngineAuthenticationRequest request) {
		Properties propertiesFromCertificate;
		try {
			propertiesFromCertificate = licenseManager.readCertFileContent(request.getCertificate());
			String username = propertiesFromCertificate.getProperty(KlabCertificate.KEY_USERNAME);
			String email = propertiesFromCertificate.getProperty(KlabCertificate.KEY_EMAIL);
			String expiryString = propertiesFromCertificate.getProperty(KlabCertificate.KEY_EXPIRATION);
			
			DateTime expiry = DateTime.parse(expiryString);
	        if (expiry.isBeforeNow()) {
	            String msg = String.format("The cert file submitted for user %s is expired.", username);
	            throw new AuthenticationFailedException(msg);
	        }
			
	        User user = klabUserManager.getUser(username);
	        Properties properties = licenseManager.getPropertiesString(user);
	        
	        propertiesFromCertificate.remove(KlabCertificate.KEY_EXPIRATION);
	        properties.remove(KlabCertificate.KEY_EXPIRATION);
	        if (propertiesFromCertificate.equals(properties)) {
	        	tokenManager.deleteExpiredTokens(username);
	            String token = jwtTokenManager.createEngineJwtToken(username);
	            ProfileResource profile = klabUserManager.getUserProfile(username);
	            klabUserManager.updateLastEngineConnection(profile.getUsername());
	            EngineUser engineUser = new EngineUser(username, null);
	            engineUser.setEmailAddress(email);
	            engineUser.setToken(token);
	            engineUser.getGroups().addAll(profile.getGroupsList());
	            return engineUser;
	        }
		} catch (IOException | PGPException | DecoderException e) {
			throw new AuthenticationFailedException(e.toString());
		}
        return null;
	}

	private EngineAuthenticationResponse processLocalEngineUser(EngineAuthenticationRequest request) {
		DateTime now = DateTime.now();
		DateTime tomorrow = now.plusDays(90);
		EngineUser engineUser = authenticateLocalEngineCert(request.getUsername());
		IdentityReference userIdentity = new IdentityReference(engineUser.getUsername(), engineUser.getEmailAddress(),
				now.toString());
		AuthenticatedIdentity authenticatedIdentity = new AuthenticatedIdentity(userIdentity, engineUser.getGroups(),
				tomorrow.toString(), engineUser.getId());
		Logging.INSTANCE.info("Local Engine Run on hub with User: " + engineUser.getUsername());
		return new EngineAuthenticationResponse(authenticatedIdentity, hubAuthenticationManager.getHubReference(),
				networkManager.getNodes(engineUser.getGroups()));
	}

	private EngineUser authenticateLocalEngineCert(String username) {
        String token = null;
        if (username.equals("system") ) {
        	token = jwtTokenManager.createEngineJwtToken(username);
        } else {
        	username = "hades";
        	token = jwtTokenManager.createEngineJwtToken(username);
        }
        tokenManager.deleteExpiredTokens(username);
        ProfileResource profile = klabUserManager.getUserProfile(username);
        klabUserManager.updateLastEngineConnection(profile.getUsername());
        EngineUser engineUser = new EngineUser(username, null);
        engineUser.setEmailAddress(profile.getEmail());
        engineUser.setToken(token);
        engineUser.getGroups().addAll(profile.getGroupsList());
        return engineUser;
	}
}
