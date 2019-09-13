package org.integratedmodelling.klab.hub.service;

import java.util.Map;

import org.integratedmodelling.klab.hub.models.ProfileResource;
import org.integratedmodelling.klab.hub.models.User;

public interface ProfileService {
	 public abstract Map<String, ProfileResource> getAllProfiles();
	 public abstract void decorate(User user);
	 public abstract ProfileResource getUserProfile(String username);
}
