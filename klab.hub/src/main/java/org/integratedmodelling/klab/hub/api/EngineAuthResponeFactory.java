package org.integratedmodelling.klab.hub.api;

import java.io.IOException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.auth.EngineUser;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;
import org.integratedmodelling.klab.hub.commands.GenerateHubReference;
import org.integratedmodelling.klab.hub.exception.AuthenticationFailedException;
import org.integratedmodelling.klab.hub.exception.LicenseConfigDoestNotExists;
import org.integratedmodelling.klab.hub.exception.LicenseExpiredException;
import org.integratedmodelling.klab.hub.exception.UserDoesNotExistException;
import org.integratedmodelling.klab.hub.licenses.services.LicenseConfigService;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.network.NodeNetworkManager;
import org.integratedmodelling.klab.hub.tokens.services.UserAuthTokenService;
import org.integratedmodelling.klab.hub.users.services.UserProfileService;
import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.integratedmodelling.klab.rest.HubNotificationMessage;
import org.integratedmodelling.klab.rest.HubReference;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.rest.HubNotificationMessage.ExtendedInfo;
import org.integratedmodelling.klab.hub.utils.IPUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class EngineAuthResponeFactory {
    
    private UserProfileService profileService;
    
    private MongoGroupRepository groupRepository;
    
    private LicenseConfigService configService;
    
    private UserAuthTokenService tokenService;
	
    public EngineAuthResponeFactory(UserProfileService profileService,
            MongoGroupRepository groupRepository,
            LicenseConfigService configService,
            UserAuthTokenService tokenService) {
        this.profileService = profileService;
        this.groupRepository = groupRepository;
        this.configService = configService;
        this.tokenService = tokenService;
    }
    
	public EngineAuthenticationResponse getRespone(EngineAuthenticationRequest request, String remoteAddr) 
	        throws NoSuchProviderException, IOException, PGPException {
		switch (request.getLevel()) {
		case ANONYMOUS:
		case INSTITUTIONAL:
			break;
		case LEGACY:
			break;
		case TEST:
			if (IPUtils.isLocal(remoteAddr)) {
				return localEngine(request);
			} else {
				break;	
			}
		case USER:
			if (IPUtils.isLocalhost(remoteAddr)) {
				//You are running locally with a hub, so it is assumed that the hub is a development hub
				return localEngine(request);
			} else {
				ProfileResource profile = profileService.getRawUserProfile(request.getName());
		        LicenseConfiguration config;
		        try {
		            config = configService.getConfigByKey(request.getKey());
		        } catch (LicenseConfigDoestNotExists e) {
		            config = null;
		        }
				EngineAuthenticationResponse response = remoteEngine(profile, request.getCertificate(), config);
				TokenAuthentication token = tokenService.createToken(profile.getUsername(), TokenType.auth);
				response.setAuthentication(token.getTokenString());
	    		profile.setLastConnection(DateTime.now());
	    		profileService.updateUserByProfile(profile);
	    		return response;
			}
		default:
			break;
		}
		return null;
	}
	
    private void validateProfileAccountStatus(ProfileResource profile) throws AuthenticationFailedException {
        switch(profile.accountStatus) {
        case active:
            return;
        case locked:
            throw new AuthenticationFailedException("User profile is locked.");
        case deleted:
            throw new AuthenticationFailedException("User profile is deleted.");
        case pendingActivation:
        case verified:
            throw new AuthenticationFailedException("User profile has not completed the activation process.");
        case expired:
        default:
            throw new AuthenticationFailedException("User profile is not active.");
        }
    }

    private void validateAgreement(ProfileResource profile) throws AuthenticationFailedException {
        if (profile.getAgreements().isEmpty()) {
            // TODO check how we want to treat profiles without agreement
            return;
        }
        // TODO for now, we just assume that there is a single agreement per profile.
        Agreement agreement = profile.getAgreements().get(0);
        if (agreement.isRevoked()) {
            throw new AuthenticationFailedException("Agreement has been revoked.");
        }
        if (agreement.isExpired()) {
            throw new AuthenticationFailedException("Agreement is expired.");
        }
    }

	@SuppressWarnings("unchecked")
    private EngineAuthenticationResponse remoteEngine(ProfileResource profile,
			String cipher, LicenseConfiguration config) throws NoSuchProviderException, IOException, PGPException, AuthenticationFailedException {
		Properties engineProperties = PropertiesFactory.fromProfile(profile, config).getProperties();
		Properties cipherProperties = new CipherProperties().getCipherProperties(config, cipher);
		ArrayList<HubNotificationMessage> messages = new ArrayList<HubNotificationMessage>();
		
        validateProfileAccountStatus(profile);
        validateAgreement(profile);
		
		DateTime expires = DateTime.parse(cipherProperties.getProperty(KlabCertificate.KEY_EXPIRATION), 
                DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ"));
		
		if(!expires.isAfter(DateTime.now().plusDays(30))) {
		    
		    HubNotificationMessage msg = HubNotificationMessage.MessageClass
		            .EXPIRING_CERTIFICATE.build("License set to expire on: " + expires.toString(), (Pair<ExtendedInfo, Object>[])(new Pair[] {
		                    new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.EXPIRATION_DATE, expires)
		                  }));
		    
		            //EXPIRING_CERTIFICATE.get( "License set to expire on: " + expires.toString());
//		    HubNotificationMessage msg = new HubNotificationMessage.
//		            new HubNotificationMessage(HubNotificationMessage.WARNING.EXPIRING_CERTIFICATE, 
//		           );
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
	    		
	    		ArrayList<GroupEntry> expired = profile.expiredGroupEntries();
	    		ArrayList<GroupEntry> expiring = profile.expiringGroupEntries();
	    		
	    		if(!expired.isEmpty()) {
	    		    expired.forEach(grp -> {
	    		        messages.add(
	    		                HubNotificationMessage.MessageClass.EXPIRED_GROUP.build("The group " + grp.getGroupName() + " has expired.", (Pair<ExtendedInfo, Object>[])(new Pair[] {
	    		                        new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.EXPIRATION_DATE, grp.getExperation()),
	    		                        new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.GROUP_NAME, grp.getGroupName())
	                            })));
	    		    });
	    		}
	    		
	            if(!expiring.isEmpty()) {
	                expiring.forEach(grp -> {
	                    messages.add(
	                            HubNotificationMessage.MessageClass.EXPIRING_GROUP.build("The group " + grp.getGroupName() + " is expiring.", (Pair<ExtendedInfo, Object>[])(new Pair[] {
                                        new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.EXPIRATION_DATE, grp.getExperation()),
                                        new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.GROUP_NAME, grp.getGroupName())
                                })));
	                 });
	            }
	    		
	    		Logging.INSTANCE.info("Remote Engine Run on hub with User: " + engine.getUsername());
	    		HubReference hub = new GenerateHubReference().execute();
	    		EngineAuthenticationResponse resp = new EngineAuthenticationResponse(authenticatedIdentity, hub,
	    				NodeNetworkManager.INSTANCE.getNodeReferences());
	    		
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

	@SuppressWarnings("unchecked")
    private EngineAuthenticationResponse localEngine(EngineAuthenticationRequest request) {
		DateTime now = DateTime.now();
		DateTime tomorrow = now.plusDays(90);
		
		ProfileResource profile = null;
		try {
			profile = profileService.getRawUserProfile(request.getName());
		} catch (UserDoesNotExistException ex) {
			Logging.INSTANCE.info("No user found locally, defaulting to hades.");
			profile = profileService.getRawUserProfile("hades");
		}
		ArrayList<HubNotificationMessage> messages = new ArrayList<HubNotificationMessage>();
		ArrayList<GroupEntry> expired = profile.expiredGroupEntries();
        ArrayList<GroupEntry> expiring = profile.expiringGroupEntries();
        
        if(!expired.isEmpty()) {
            expired.forEach(grp -> {
                messages.add(
                        HubNotificationMessage.MessageClass.EXPIRED_GROUP.build("The group " + grp.getGroupName() + " has expired.", (Pair<ExtendedInfo, Object>[])(new Pair[] {
                                new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.EXPIRATION_DATE, grp.getExperation()),
                                new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.GROUP_NAME, grp.getGroupName())
                        })));
            });
        }
        
        if(!expiring.isEmpty()) {
            expiring.forEach(grp -> {
                messages.add(
                        HubNotificationMessage.MessageClass.EXPIRING_GROUP.build("The group " + grp.getGroupName() + " is expiring.", (Pair<ExtendedInfo, Object>[])(new Pair[] {
                                new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.EXPIRATION_DATE, grp.getExperation()),
                                new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.GROUP_NAME, grp.getGroupName())
                        })));
             });
        }
		
		EngineUser engine = localEngineUser(profile);
		
		IdentityReference userIdentity = new IdentityReference(engine.getUsername(), engine.getEmailAddress(),
				now.toString());
		AuthenticatedIdentity authenticatedIdentity = new AuthenticatedIdentity(userIdentity, engine.getGroups(),
				tomorrow.toString(), engine.getId());
		
		profile.setLastConnection(now);
		profileService.updateUserByProfile(profile);

		Logging.INSTANCE.info("Local Engine Run on hub with User: " + engine.getUsername());
		HubReference hub = new GenerateHubReference().execute();
		EngineAuthenticationResponse resp = new EngineAuthenticationResponse(authenticatedIdentity, hub,
                NodeNetworkManager.INSTANCE.getNodeReferences());;
		if (!messages.isEmpty()) {
            resp.setMessages(messages);
        }
		return resp;
	}
	
	private EngineUser localEngineUser(ProfileResource profile) {
		EngineUser engine = new EngineUser(profile.getUsername(), null);
		String token = new JwtToken().createEngineJwtToken(profile);
		engine.setEmailAddress(profile.getEmail());
		engine.setToken(token);
		engine.getGroups().addAll(profile.getGroupsList());
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
