package org.integratedmodelling.klab.hub.license.commands;

import org.integratedmodelling.klab.auth.Hub;
import org.integratedmodelling.klab.hub.config.LegacyLicenseConfig;
import org.integratedmodelling.klab.hub.license.LegacyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

import name.neuhalfen.projects.crypto.bouncycastle.openpgp.BouncyGPG;

public class NewLegacyConfiguration {

	private Hub hub;
	
	@Autowired
	LegacyLicenseConfig legacyLicenseConfig;
	
	public NewLegacyConfiguration(Hub hub) {
		this.hub = hub;
	}
	
	public LegacyConfiguration get() {
		LegacyConfiguration config = new LegacyConfiguration();
		config.setHubId(legacyLicenseConfig.getHubId());
		config.setKeyString(legacyLicenseConfig.getKey());
		config.setName(legacyLicenseConfig.getName());
		config.setEmail(legacyLicenseConfig.getEmail());
		
		
		BouncyGPG
	}

}
