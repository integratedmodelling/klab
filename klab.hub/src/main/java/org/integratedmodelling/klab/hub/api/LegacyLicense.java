package org.integratedmodelling.klab.hub.api;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.Properties;

import org.bouncycastle.bcpg.ArmoredInputStream;
import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.hub.exception.TokenGenerationException;

import com.javax0.license3j.licensor.License;

public class LegacyLicense implements KlabLicense{

	@Override
	public byte[] generate(Properties properties, LicenseConfiguration configuration) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			properties.setProperty(KlabCertificate.KEY_CERTIFICATE, encodedLicenseString(properties, configuration));
			properties.store(byteArrayOutputStream, "Node Certificate Generated On " + new Date());
			byteArrayOutputStream.close();
			return byteArrayOutputStream.toByteArray();
		} catch (IOException | PGPException e) {
			// TODO Auto-generated catch block
			throw new TokenGenerationException("Unable to generate properties string");
		}
	}

	private String encodedLicenseString(Properties properties, LicenseConfiguration configuration) throws IOException, PGPException {
		Writer writer = new StringWriter();
		License license = new License();
		ArmoredKeyPair keys = configuration.getKeys();
		InputStream targetStream = new ByteArrayInputStream(keys.privateKey());
		
		String generatedBy = String.format("Generated by %s on ", 
				configuration.getHubId()) + new Date();
		properties.store(writer, generatedBy);
		license.setLicense(writer.toString());
		license.loadKey(targetStream, configuration.getHubId() + " <" + configuration.getEmail() + ">");
        String encodedLicenseString = license.encodeLicense(configuration.getPassphrase());
        return encodedLicenseString;
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