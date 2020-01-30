package org.integratedmodelling.klab.hub.license;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.auth.IEngineIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.auth.EngineUser;
import org.integratedmodelling.klab.auth.Hub;
import org.integratedmodelling.klab.auth.Node;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.hub.authentication.commands.GenerateHubReference;
import org.integratedmodelling.klab.hub.groups.MongoGroup;
import org.integratedmodelling.klab.hub.groups.MongoGroupAdapter;
import org.integratedmodelling.klab.hub.network.NetworkManager;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.security.NetworkKeyManager;
import org.integratedmodelling.klab.hub.users.ProfileResource;
import org.integratedmodelling.klab.hub.users.services.UserProfileService;
import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.HubReference;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.utils.IPUtils;
import org.joda.time.DateTime;

public class EngineAuthResponeFactory {
	
	public EngineAuthenticationResponse getRespone(EngineAuthenticationRequest request, String remoteAddr,
			LicenseConfiguration config, UserProfileService userProfileService, MongoGroupRepository groupRepository) {
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
				
			}
		default:
			break;
		}
		return null;
	}
	
	private EngineAuthenticationResponse localEngine(EngineAuthenticationRequest request, UserProfileService userProfileService, MongoGroupRepository groupRepository) {
		DateTime now = DateTime.now();
		DateTime tomorrow = now.plusDays(90);
		
		List<MongoGroup> mongoGroups = groupRepository.findAll();
		Set<Group> groups = new HashSet<>();
		
		ProfileResource profile = null;
		
		if(request.getUsername().equalsIgnoreCase("system")) {	
			profile = userProfileService.getUserProfile(request.getUsername());
		} else {
			profile = userProfileService.getUserProfile("hades");
		}
		
		mongoGroups.forEach(
				mongoGroup -> groups.add(new MongoGroupAdapter(mongoGroup).convertGroup()));
		
		EngineUser engine = localEngineUser(profile, groups);
		
		IdentityReference userIdentity = new IdentityReference(engine.getUsername(), engine.getEmailAddress(),
				now.toString());
		AuthenticatedIdentity authenticatedIdentity = new AuthenticatedIdentity(userIdentity, engine.getGroups(),
				tomorrow.toString(), engine.getId());

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

}
