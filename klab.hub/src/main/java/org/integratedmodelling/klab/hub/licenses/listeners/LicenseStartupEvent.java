package org.integratedmodelling.klab.hub.licenses.listeners;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.apache.commons.codec.DecoderException;
import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.hub.licenses.dto.BouncyConfiguration;
import org.integratedmodelling.klab.hub.licenses.dto.LicenseConfiguration;
import org.integratedmodelling.klab.hub.nodes.commands.GenerateLicenseFactory;
import org.integratedmodelling.klab.hub.repository.LicenseConfigRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import name.neuhalfen.projects.crypto.bouncycastle.openpgp.BouncyGPG;

/*
 * This components will create a new license key for utilization of the hub on startup, if none exists.
 * This is essential for deploying new hubs, and not having to include complicated setup configurations
 * that require the user generation of pgp keys.  It also cuts down on the number of secret files that
 * are needed to deploy the hub.  
 * 
 * ALso greatly reduces the complication in development of testing license generation 
 * and verification.
 * 
 * Problem  is that during testing the framework firesoff an context refresh before the context has
 * been built.  This took an hour and half of my time to discover and fix.
 * 
 * @author: Steve
 */
@Component
@Profile("production")
public class LicenseStartupEvent {
	
	public LicenseStartupEvent(LicenseConfigRepository repository) {
		super();
		this.repository = repository;
		BouncyGPG.registerProvider();
		
	}
	private LicenseConfigRepository repository;
	
	@Async
	@EventListener
	public void startup(LicenseStartupReady event) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, PGPException, IOException, DecoderException, URISyntaxException {
		
		if(repository.findAll().isEmpty()) {
			LicenseConfiguration config =
					new GenerateLicenseFactory()
						.getConfiguration(BouncyConfiguration.class);
			config.setDefaultConfig(true);
			repository.insert(config);
		}
	}
		
	public void startup() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, PGPException, IOException, DecoderException {
			
			if(repository.findAll().isEmpty()) {
				LicenseConfiguration config =
						new GenerateLicenseFactory()
							.getConfiguration(BouncyConfiguration.class);
				config.setDefaultConfig(true);
				repository.insert(config);
			}
	} 

}
