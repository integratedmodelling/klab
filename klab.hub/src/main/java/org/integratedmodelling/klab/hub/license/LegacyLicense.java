package org.integratedmodelling.klab.hub.license;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.NoSuchProviderException;
import java.util.Properties;

import org.bouncycastle.bcpg.ArmoredInputStream;
import org.bouncycastle.openpgp.PGPException;

import com.javax0.license3j.licensor.License;

public class LegacyLicense implements KlabLicense{

	@Override
	public byte[] generate(Properties properties, LicenseConfiguration configuration) {
		// We no longer make legacy certificates.
		return null;
	}

	@Override
	public Properties getPropertiesFromCipher(String cipher, LicenseConfiguration configuration)
			throws IOException, PGPException, NoSuchProviderException {
		License license = new License();
		
		ArmoredKeyPair keys = configuration.getKeys();
		InputStream targetStream = new ByteArrayInputStream(keys.publicKey());
		InputStream armoredBais = new ByteArrayInputStream(cipher.getBytes());
		ArmoredInputStream armoredInputStream = new ArmoredInputStream(armoredBais);
		
		license.loadKeyRing(targetStream, configuration.getDigest());
		license.setLicenseEncoded(armoredInputStream,"UTF-8");
        String propertiesString = license.getLicenseString();
        Properties result = new Properties();
        result.load(new StringReader(propertiesString));
        return result;
	}

}
