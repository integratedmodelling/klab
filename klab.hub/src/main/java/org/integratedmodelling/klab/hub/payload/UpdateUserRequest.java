package org.integratedmodelling.klab.hub.payload;

import org.integratedmodelling.klab.hub.api.ProfileResource;

public class UpdateUserRequest {
	public ProfileResource profile;

	public ProfileResource getProfile() {
		return profile;
	}

	public void setProfile(ProfileResource profile) {
		this.profile = profile;
	}
	
}
