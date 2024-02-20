package org.integratedmodelling.klab.hub.licenses.dto;

import java.io.IOException;
import java.security.NoSuchProviderException;
import java.util.Properties;

import org.bouncycastle.openpgp.PGPException;

public interface KlabLicense {
	public byte[] generate(Properties properties, LicenseConfiguration configuration);
	public Properties getPropertiesFromCipher(String cipher, LicenseConfiguration configuration)
			throws IOException, PGPException, NoSuchProviderException;
	
}
