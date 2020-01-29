package org.integratedmodelling.klab.hub.listeners;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.hub.config.LegacyLicenseConfig;
import org.integratedmodelling.klab.hub.license.BouncyConfiguration;
import org.integratedmodelling.klab.hub.license.LegacyConfiguration;
import org.integratedmodelling.klab.hub.license.LicenseConfiguration;
import org.integratedmodelling.klab.hub.license.commands.GenerateLicenseFactory;
import org.integratedmodelling.klab.hub.repository.LicenseConfigRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import name.neuhalfen.projects.crypto.bouncycastle.openpgp.BouncyGPG;

@Component
public class LicenseStartupEvent {
	
	public LicenseStartupEvent(LicenseConfigRepository repository, LegacyLicenseConfig legacy) {
		super();
		this.repository = repository;
		this.legacy = legacy;
		BouncyGPG.registerProvider();
		
	}
	private LicenseConfigRepository repository;
	private LegacyLicenseConfig legacy;
	
	@EventListener
	public void startup(ContextRefreshedEvent event) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, PGPException, IOException {
		
		if(repository.findAll().isEmpty()) {
			LicenseConfiguration config =
					new GenerateLicenseFactory()
						.getConfiguration(BouncyConfiguration.class);
			repository.insert(config);
		}
		
		if(legacy.getKey() != null && !repository.findByKeyString(legacy.getKey()).isPresent()) {
			LicenseConfiguration config =
					new GenerateLicenseFactory()
						.getConfiguration(LegacyConfiguration.class);
		}
	}
	

}
