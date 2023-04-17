package org.integratedmodelling.klab.hub.api;

import java.io.IOException;
import java.security.NoSuchProviderException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.auth.EngineUser;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.hub.agreements.services.AgreementService;
import org.integratedmodelling.klab.hub.commands.GenerateHubReference;
import org.integratedmodelling.klab.hub.exception.AuthenticationFailedException;
import org.integratedmodelling.klab.hub.exception.LicenseConfigDoestNotExists;
import org.integratedmodelling.klab.hub.exception.LicenseExpiredException;
import org.integratedmodelling.klab.hub.exception.NoValidAgreementException;
import org.integratedmodelling.klab.hub.exception.UserDoesNotExistException;
import org.integratedmodelling.klab.hub.licenses.services.LicenseConfigService;
import org.integratedmodelling.klab.hub.network.NodeNetworkManager;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.tokens.services.UserAuthTokenService;
import org.integratedmodelling.klab.hub.users.services.UserProfileService;
import org.integratedmodelling.klab.hub.utils.IPUtils;
import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.integratedmodelling.klab.rest.HubNotificationMessage;
import org.integratedmodelling.klab.rest.HubNotificationMessage.ExtendedInfo;
import org.integratedmodelling.klab.rest.HubReference;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.rest.HubNotificationMessage.Parameters;
import org.integratedmodelling.klab.utils.Pair;

public class EngineAuthResponeFactory {
    
    private UserProfileService profileService;
    
    private MongoGroupRepository groupRepository;
    
    private LicenseConfigService configService;
    
    private UserAuthTokenService tokenService;
    
    private AgreementService agreementService;
	
    public EngineAuthResponeFactory(UserProfileService profileService,
            MongoGroupRepository groupRepository,
            LicenseConfigService configService,
            UserAuthTokenService tokenService,
            AgreementService agreementService) {
        this.profileService = profileService;
        this.groupRepository = groupRepository;
        this.configService = configService;
        this.tokenService = tokenService;
        this.agreementService = agreementService;
    }
    
	public EngineAuthenticationResponse getRespone(EngineAuthenticationRequest request, String remoteAddr) 
	        throws NoSuchProviderException, IOException, PGPException {
		switch (request.getLevel()) {
		case ANONYMOUS:
		case INSTITUTIONAL:
			break;
		case TEST:
			if (IPUtils.isLocal(remoteAddr)) {
				return localEngine(request);
			} else {
				break;	
			}
		case USER:
			/*if (IPUtils.isLocalhost(remoteAddr)) {
				//You are running locally with a hub, so it is assumed that the hub is a development hub
				return localEngine(request);
			} else {*/
				ProfileResource profile = profileService.getRawUserProfile(request.getName());
		        LicenseConfiguration config;
		        try {
		            config = configService.getConfigByKey(request.getKey());
		        } catch (LicenseConfigDoestNotExists e) {
		            config = null;
		        }
				EngineAuthenticationResponse response = remoteEngine(profile, request.getIdAgreement(), request.getCertificate(), config);
				TokenAuthentication token = tokenService.createToken(profile.getUsername(), TokenType.auth);
				response.setAuthentication(token.getTokenString());
	    		profile.setLastConnection(LocalDateTime.now());
	    		profileService.updateUserByProfile(profile);
	    		return response;
//	    		}
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
            throw new AuthenticationFailedException(String.format("User '%s' is locked.", profile.getUsername()));
        case deleted:
            throw new AuthenticationFailedException(String.format("User '%s' is deleted.", profile.getUsername()));
        case pendingActivation:
        case verified:
            throw new AuthenticationFailedException(String.format("User '%s' has not completed the activation process.", profile.getUsername()));
        case expired:
        default:
            throw new AuthenticationFailedException(String.format("User '%s' is not active.", profile.getUsername()));
        }
    }

	@SuppressWarnings("unchecked")
    private EngineAuthenticationResponse remoteEngine(ProfileResource profile, String idAgreement,
			String cipher, LicenseConfiguration config) throws NoSuchProviderException, IOException, PGPException, AuthenticationFailedException {
	    
	    ArrayList<HubNotificationMessage> messages = new ArrayList<HubNotificationMessage>();
	    
	    Properties cipherProperties = new CipherProperties().getCipherProperties(config, cipher);
	    
	    List<Agreement> validAgreements = null;
	    Agreement agreement = null;
	    
	    
	    //if agreement is null the certificate is old
	    if (idAgreement == null) {
	        HubNotificationMessage msg = HubNotificationMessage.MessageClass
                    .CERTIFICATE_WITHOUT_AGREEMENT.build("Used certificate is old. Please get a new certificate and replace it.", new Parameters((Pair<ExtendedInfo, Object>[])(new Pair[] {
                            new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.SHORT_MESSAGE, "Certificate without agreement.")
                          })));
            messages.add(msg);
	        validAgreements = profile.getAgreements().stream()
	                .map(AgreementEntry::getAgreement)
	                .filter(Agreement::isValid).collect(Collectors.toList());
	        if (validAgreements.isEmpty()) {
	            throw new NoValidAgreementException(profile.getUsername());
	        }
	    } else {
	        try {
	        agreement = agreementService.getAgreement(idAgreement);
	        } catch (Exception e) {
	            HubNotificationMessage msg = HubNotificationMessage.MessageClass
	                    .AGREEMENT_NOT_EXIST.build("Certificate's agreement doesn't exist. Please get a new certificate and replace it.", new Parameters((Pair<ExtendedInfo, Object>[])(new Pair[] {
	                            new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.SHORT_MESSAGE, "Agreement not exists.")
	                          })));
	            messages.add(msg);
            }
	    }
	    
	    
		Properties engineProperties = PropertiesFactory.fromProfile(profile, agreement, config).getProperties();		

        validateProfileAccountStatus(profile);        
        
        if (agreement == null) {
            for (Agreement validAgreement: validAgreements) {
                checkForExpiringAgreement(validAgreement, messages);
            }            
        } else {
            checkForExpiringAgreement(agreement, messages);
        }
        
        LocalDateTime expires = null;

        //The format time of expirationDate changes then taken account the 2 versions of it.
        try {
		expires = LocalDateTime.parse(cipherProperties.getProperty(KlabCertificate.KEY_EXPIRATION), 
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ"));
        } catch (Exception e) {
            expires = LocalDateTime.parse(cipherProperties.getProperty(KlabCertificate.KEY_EXPIRATION), 
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS"));
        }
		
		if(!expires.isAfter(LocalDateTime.now().plusDays(30))) { 
		    HubNotificationMessage msg = HubNotificationMessage.MessageClass
		            .EXPIRING_CERTIFICATE.build("License set to expire on: " + expires.toString(), new Parameters((Pair<ExtendedInfo, Object>[])(new Pair[] {
		                    new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.EXPIRATION_DATE, expires)
		                  })));

		    messages.add(msg);
		}
		
		if (expires.isAfter(LocalDateTime.now())) {
			engineProperties.remove(KlabCertificate.KEY_EXPIRATION);
	        cipherProperties.remove(KlabCertificate.KEY_EXPIRATION);
	        engineProperties.remove(KlabCertificate.KEY_PARTNER_HUB);
	        cipherProperties.remove(KlabCertificate.KEY_PARTNER_HUB);
	        if(engineProperties.equals(cipherProperties)) {
	        	EngineUser engine = remoteEngineUser(profile);
	    		IdentityReference userIdentity = new IdentityReference(engine.getUsername(), engine.getEmailAddress(),
	    				LocalDateTime.now().toString());
	    		AuthenticatedIdentity authenticatedIdentity = new AuthenticatedIdentity(userIdentity, engine.getGroups(),
	    				LocalDateTime.now().plusDays(90).toString(), engine.getId());
	    		
	    		ArrayList<GroupEntry> expired = profile.expiredGroupEntries();
	    		ArrayList<GroupEntry> expiring = profile.expiringGroupEntries();
	    		
	    		if(!expired.isEmpty()) {
	    		    expired.forEach(grp -> {
	    		        messages.add(
	    		                HubNotificationMessage.MessageClass.EXPIRED_GROUP.build("The group " + grp.getGroupName() + " has expired.", new Parameters((Pair<ExtendedInfo, Object>[])(new Pair[] {
	    		                        new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.EXPIRATION_DATE, grp.getExpiration()),
	    		                        new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.GROUP_NAME, grp.getGroupName())
	                            }))));
	    		    });
	    		}
	    		
	            if(!expiring.isEmpty()) {
	                expiring.forEach(grp -> {
	                    messages.add(
	                            HubNotificationMessage.MessageClass.EXPIRING_GROUP.build("The group " + grp.getGroupName() + " is expiring.", new Parameters((Pair<ExtendedInfo, Object>[])(new Pair[] {
                                        new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.EXPIRATION_DATE, grp.getExpiration()),
                                        new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.GROUP_NAME, grp.getGroupName())
                                }))));
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
	private void checkForExpiringAgreement(Agreement agreement, ArrayList<HubNotificationMessage> messages) {
	    final Instant nowPlus30Days = Instant.now().plus(30, ChronoUnit.DAYS);
        if (agreement.isExpirable() && agreement.getExpirationDate().toInstant().isBefore(nowPlus30Days)) {
            HubNotificationMessage msg = HubNotificationMessage.MessageClass
                    .EXPIRING_AGREEMENT.build("Agreement set to expire on: " + agreement.getExpirationDate(), new Parameters((Pair<ExtendedInfo, Object>[])(new Pair[] {
                            new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.EXPIRATION_DATE, agreement.getExpirationDate())
                    })));
            messages.add(msg);   
        }        
    }


    @SuppressWarnings("unchecked")
    private EngineAuthenticationResponse localEngine(EngineAuthenticationRequest request) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime tomorrow = now.plusDays(90);
		
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
                        HubNotificationMessage.MessageClass.EXPIRED_GROUP.build("The group " + grp.getGroupName() + " has expired.", new Parameters((Pair<ExtendedInfo, Object>[])(new Pair[] {
                                new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.EXPIRATION_DATE, grp.getExpiration()),
                                new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.GROUP_NAME, grp.getGroupName())
                        }))));
            });
        }
        
        if(!expiring.isEmpty()) {
            expiring.forEach(grp -> {
                messages.add(
                        HubNotificationMessage.MessageClass.EXPIRING_GROUP.build("The group " + grp.getGroupName() + " is expiring.", new Parameters((Pair<ExtendedInfo, Object>[])(new Pair[] {
                                new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.EXPIRATION_DATE, grp.getExpiration()),
                                new Pair<ExtendedInfo, Object>(HubNotificationMessage.ExtendedInfo.GROUP_NAME, grp.getGroupName())
                        }))));
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
