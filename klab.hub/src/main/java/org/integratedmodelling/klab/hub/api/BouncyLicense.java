package org.integratedmodelling.klab.hub.api;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.util.Date;
import java.util.Properties;

import org.bouncycastle.bcpg.ArmoredInputStream;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.util.io.Streams;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.hub.exception.TokenGenerationException;

import name.neuhalfen.projects.crypto.bouncycastle.openpgp.BouncyGPG;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.keyrings.InMemoryKeyring;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.keyrings.KeyringConfigs;

public class BouncyLicense implements KlabLicense {	

	@Override
	public byte[] generate(Properties properties, LicenseConfiguration configuration) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			properties.setProperty(KlabCertificate.KEY_CERTIFICATE, encodedLicenseString(properties, configuration));
			properties.store(byteArrayOutputStream, "Certificate Generated On " + new Date());
			byteArrayOutputStream.close();
			return byteArrayOutputStream.toByteArray();
		} catch (IOException | PGPException | SignatureException | NoSuchAlgorithmException | NoSuchProviderException e) {
			throw new TokenGenerationException("Unable to generate properties string");
		}
	}

	private String encodedLicenseString(Properties properties, LicenseConfiguration configuration) throws IOException, PGPException, SignatureException, NoSuchAlgorithmException, NoSuchProviderException {
		Writer writer = new StringWriter();
		ArmoredKeyPair keys = configuration.getKeys();
		
		final InMemoryKeyring keyring = KeyringConfigs.forGpgExportedKeys(
				keyId -> configuration.getPassphrase().toCharArray());
		
		keyring.addPublicKey(keys.publicKey());
		keyring.addSecretKey(keys.privateKey());
		
		String generatedBy = String.format("Generated by %s on ", 
				configuration.getHubId()) + new Date();
		properties.store(writer, generatedBy);
		properties.getProperty(KlabCertificate.KEY_EMAIL);
		properties.getProperty(KlabCertificate.KEY_PARTNER_EMAIL);
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		ArmoredOutputStream armored = new ArmoredOutputStream(result);

	    try (
	        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(armored);
	        final OutputStream outputStream = BouncyGPG
	            .encryptToStream()
	            .withConfig(keyring)
	            .withStrongAlgorithms()
	            .toRecipients(properties.getProperty(KlabCertificate.KEY_PARTNER_EMAIL))
	            .andSignWith(configuration.getHubId() + " <" + configuration.getEmail() + ">")
	            .binaryOutput()
	            .andWriteTo(bufferedOutputStream);
	        final ByteArrayInputStream is = new ByteArrayInputStream(properties.toString().getBytes())
	    ) {
	      Streams.pipeAll(is, outputStream);
	    // It is very important that outputStream is closed before the result stream is read.
	    // The reason is that GPG writes the signature at the end of the stream.
	    // This is triggered by closing the stream.
	    // In this example outputStream is closed via the try-with-resources mechanism of Java
	    }

	    armored.close();
	    result.close();
     
        return new String(result.toByteArray(), StandardCharsets.US_ASCII);
	}

	@Override
	public Properties getPropertiesFromCipher(String cipher, LicenseConfiguration configuration) throws IOException, PGPException, NoSuchProviderException {
		
		ArmoredKeyPair keys = configuration.getKeys();
		
		final InMemoryKeyring keyring = KeyringConfigs.forGpgExportedKeys(
				keyId -> configuration.getPassphrase().toCharArray());
		
		keyring.addPublicKey(keys.publicKey());
		keyring.addSecretKey(keys.privateKey());
		
		final OutputStream output = new ByteArrayOutputStream();
		
		try (
			final InputStream armoredBais = new ByteArrayInputStream(cipher.getBytes());
			final ArmoredInputStream armoredInputStream = new ArmoredInputStream(armoredBais);
			final BufferedOutputStream bufferedOut = new BufferedOutputStream(output);
			
	        final InputStream plaintextStream = BouncyGPG
	                .decryptAndVerifyStream()
	                .withConfig(keyring)
	                .andRequireSignatureFromAllKeys(configuration.getHubId() + " <" + configuration.getEmail() + ">")
	                .fromEncryptedInputStream(armoredInputStream)
	    ) {
		      Streams.pipeAll(plaintextStream, bufferedOut);
		}
		
		output.close();
		
		final String decrypted_message = new String(((ByteArrayOutputStream) output).toByteArray());
		final String cleaned = decrypted_message.replaceAll("\\{|\\}", "");
        Properties result = new Properties();
        result.load(new StringReader(cleaned.replaceAll(",", "\n")));
        
        return result;
	}


}