package org.integratedmodelling.klab.hub.commands;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.bouncycastle.openpgp.PGPException;
import org.hamcrest.core.IsInstanceOf;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.auth.Hub;
import org.integratedmodelling.klab.hub.api.BouncyConfiguration;
import org.integratedmodelling.klab.hub.api.BouncyLicense;
import org.integratedmodelling.klab.hub.api.LegacyConfiguration;
import org.integratedmodelling.klab.hub.api.LicenseConfiguration;

public class GenerateLicenseFactory {
	public LicenseConfiguration getConfiguration(Class<? extends LicenseConfiguration> clazz) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, PGPException, IOException {
		if(clazz.getName() == BouncyConfiguration.class.getName()) {
			Hub hub = Authentication.INSTANCE.getAuthenticatedIdentity(Hub.class);
			BouncyConfiguration config = new NewBouncyConfiguration(hub).get();
			return config;
		}
		if(clazz.getName() == LegacyConfiguration.class.getName()){
			LegacyConfiguration config = new NewLegacyConfiguration().get();
			return config;
		}
		return null;
	}
	
}
