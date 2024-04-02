package org.integratedmodelling.klab.hub.users.payload;

import org.integratedmodelling.klab.hub.users.dto.ProfileResource;

public class UpdateUserRequest {
	public ProfileResource profile;

	public ProfileResource getProfile() {
		return profile;
	}

	public void setProfile(ProfileResource profile) {
		this.profile = profile;
	}
	
}
