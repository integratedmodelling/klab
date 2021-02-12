package org.integratedmodelling.klab.hub.api;

import java.io.IOException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.auth.EngineUser;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.hub.api.adapters.MongoGroupAdapter;
import org.integratedmodelling.klab.hub.commands.GenerateHubReference;
import org.integratedmodelling.klab.hub.exception.LicenseExpiredException;
import org.integratedmodelling.klab.hub.network.NetworkManager;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.users.services.UserProfileService;
import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.HubNotificationMessage;
import org.integratedmodelling.klab.rest.HubReference;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.utils.IPUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class EngineAuthResponeFactory {
	
	public EngineAuthenticationResponse getRespone(EngineAuthenticationRequest request, String remoteAddr,
			LicenseConfiguration config, UserProfileService userProfileService, MongoGroupRepository groupRepository) throws NoSuchProviderException, IOException, PGPException {
		switch (request.getLevel()) {
		case ANONYMOUS:
		case INSTITUTIONAL:
			break;
		case LEGACY:
			break;
		case TEST:
			if (IPUtils.isLocal(remoteAddr)) {
				return localEngine(request, userProfileService, groupRepository);
			} else {
				break;	
			}
		case USER:
			if (IPUtils.isLocalhost(remoteAddr)) {
				//You are running locally with a hub, so it is assumed that the hub is a development hub
				return localEngine(request, userProfileService, groupRepository);
			} else {
				ProfileResource profile = userProfileService.getRawUserProfile(request.getName());
				EngineAuthenticationResponse response = remoteEngine(profile, request.getCertificate(), config);
	    		profile.setLastConnection(DateTime.now());
	    		userProfileService.updateUserByProfile(profile);
	    		return response;
			}
		default:
			break;
		}
		return null;
	}
	
	private EngineAuthenticationResponse remoteEngine(ProfileResource profile,
			String cipher, LicenseConfiguration config) throws NoSuchProviderException, IOException, PGPException {
		Properties engineProperties = PropertiesFactory.fromProfile(profile, config).getProperties();
		Properties cipherProperties = new CipherProperties().getCipherProperties(config, cipher);
		ArrayList<HubNotificationMessage> messages = new ArrayList<HubNotificationMessage>();
		
		DateTime expires = DateTime.parse(cipherProperties.getProperty(KlabCertificate.KEY_EXPIRATION), 
                DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ"));
		
		
		if(!expires.isAfter(DateTime.now().plusDays(30))) {
		    HubNotificationMessage msg = new HubNotificationMessage(HubNotificationMessage.WARNING.EXPIRING_CERTIFICATE, 
		            "License set to expire on: " + expires.toString());
		    messages.add(msg);
		}
		
		
		if (expires.isAfterNow()) {
			engineProperties.remove(KlabCertificate.KEY_EXPIRATION);
	        cipherProperties.remove(KlabCertificate.KEY_EXPIRATION);
	        engineProperties.remove(KlabCertificate.KEY_PARTNER_HUB);
	        cipherProperties.remove(KlabCertificate.KEY_PARTNER_HUB);
	        if(engineProperties.equals(cipherProperties)) {
	        	EngineUser engine = remoteEngineUser(profile);
	    		IdentityReference userIdentity = new IdentityReference(engine.getUsername(), engine.getEmailAddress(),
	    				DateTime.now().toString());
	    		AuthenticatedIdentity authenticatedIdentity = new AuthenticatedIdentity(userIdentity, engine.getGroups(),
	    				DateTime.now().plusDays(90).toString(), engine.getId());
	    		
	    		ArrayList<String> expired = profile.expiredGroupEntries();
	    		ArrayList<String> expiring = profile.expiredGroupEntries();
	    		
	    		if(!expired.isEmpty()) {
	    		    expired.forEach(grp -> {
	    		        messages.add(new HubNotificationMessage(HubNotificationMessage.ERROR.EXPIRED_GROUP,
	    		                "The group " + grp + "has expired."));
	    		    });
	    		}
	    		
	            if(!expiring.isEmpty()) {
	                expiring.forEach(grp -> {
	                    messages.add(new HubNotificationMessage(HubNotificationMessage.WARNING.EXPIRING_GROUP,
	                            "The group " + grp + "is expiring."));
	                 });
	            }
	    		
	    		Logging.INSTANCE.info("Remote Engine Run on hub with User: " + engine.getUsername());
	    		HubReference hub = new GenerateHubReference().execute();
	    		EngineAuthenticationResponse resp = new EngineAuthenticationResponse(authenticatedIdentity, hub,
	    				NetworkManager.INSTANCE.getNodes(engine.getGroups()));
	    		
	    		if (!messages.isEmpty()) {
	    			resp.setMessages(messages);
	    		}
	    		
	    		return resp;
	        }
		} else {
		    //should we send an email?
			throw new LicenseExpiredException(profile.getUsername());
		}
		return null;
	}

	private EngineAuthenticationResponse localEngine(EngineAuthenticationRequest request, UserProfileService userProfileService, MongoGroupRepository groupRepository) {
		DateTime now = DateTime.now();
		DateTime tomorrow = now.plusDays(90);
		
		List<MongoGroup> mongoGroups = groupRepository.findAll();
		Set<Group> groups = new HashSet<>();
		
		ProfileResource profile = null;
		
		if(request.getName().equalsIgnoreCase("system")) {	
			profile = userProfileService.getRawUserProfile(request.getName());
		} else {
			profile = userProfileService.getRawUserProfile("hades");
		}
		
		mongoGroups.forEach(
				mongoGroup -> groups.add(new MongoGroupAdapter(mongoGroup).convertGroup()));
		
		EngineUser engine = localEngineUser(profile, groups);
		
		IdentityReference userIdentity = new IdentityReference(engine.getUsername(), engine.getEmailAddress(),
				now.toString());
		AuthenticatedIdentity authenticatedIdentity = new AuthenticatedIdentity(userIdentity, engine.getGroups(),
				tomorrow.toString(), engine.getId());
		
		profile.setLastConnection(now);
		userProfileService.updateUserByProfile(profile);

		Logging.INSTANCE.info("Local Engine Run on hub with User: " + engine.getUsername());
		HubReference hub = new GenerateHubReference().execute();
		return new EngineAuthenticationResponse(authenticatedIdentity, hub,
				NetworkManager.INSTANCE.getNodes(engine.getGroups()));
	}
	
	private EngineUser localEngineUser(ProfileResource profile, Set<Group> groups) {
		EngineUser engine = new EngineUser(profile.getUsername(), null);
		String token = new JwtToken().createEngineJwtToken(profile);
		engine.setEmailAddress(profile.getEmail());
		engine.setToken(token);
		engine.getGroups().addAll(groups);
		return engine;
	}
	
	private EngineUser remoteEngineUser(ProfileResource profile) {
		EngineUser engine = new EngineUser(profile.getUsername(), null);
		String token = new JwtToken().createEngineJwtToken(profile);
		engine.setEmailAddress(profile.getEmail());
		engine.setToken(token);
		engine.getGroups().addAll(profile.getGroupsList());
		return engine;
	}

}
