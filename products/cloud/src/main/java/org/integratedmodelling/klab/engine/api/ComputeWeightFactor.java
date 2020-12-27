package org.integratedmodelling.klab.engine.api;

import org.integratedmodelling.klab.engine.services.HubUserProfile;

public class ComputeWeightFactor {

	public static int compute(HubUserProfile profile) {
		return getProfileWeight(profile);
		
	}
	
	private static int getProfileWeight(HubUserProfile profile) {
		if(profile.getRoles().contains("ROLE_SYSTEM")){
			return 49;
		} else if (profile.getRoles().contains("ROLE_ADMINSTRATOR")) {
			return 24;
		} else {
			return 10;
		}
	}
}
