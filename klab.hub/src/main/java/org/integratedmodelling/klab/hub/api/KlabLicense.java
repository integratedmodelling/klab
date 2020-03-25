package org.integratedmodelling.klab.hub.api;

import java.io.IOException;
import java.security.NoSuchProviderException;
import java.util.Properties;

import org.bouncycastle.openpgp.PGPException;

interface KlabLicense {
	public byte[] generate(Properties properties, LicenseConfiguration configuration);
	public Properties getPropertiesFromCipher(String cipher, LicenseConfiguration configuration)
			throws IOException, PGPException, NoSuchProviderException;
	
}
