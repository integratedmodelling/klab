package org.integratedmodelling.klab.hub.nodes.commands;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.UUID;

import org.apache.commons.text.RandomStringGenerator;
import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.auth.Hub;
import org.integratedmodelling.klab.hub.licenses.dto.ArmoredKeyPair;
import org.integratedmodelling.klab.hub.licenses.dto.BouncyConfiguration;
import org.integratedmodelling.klab.hub.licenses.dto.BouncyGpgKeyRing;

import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.keyrings.KeyringConfig;

public class NewBouncyConfiguration {
	
	public NewBouncyConfiguration(Hub hub) {
		super();
		this.hub = hub;
	}

	private Hub hub;
	
	public BouncyConfiguration get() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, PGPException, IOException {
		RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder()
				.withinRange(33, 90)
				.build();
		
		String passphrase = pwdGenerator.generate(36);
		
		BouncyConfiguration config = new BouncyConfiguration();
		
		config.setEmail(hub.getParentIdentity().getEmailAddress());
		config.setHubId(hub.getName());
		config.setKeyString(UUID.randomUUID().toString());
		config.setPassphrase(passphrase);
		config.setName(hub.getName());
		config.setHubUrl(hub.getUrls().iterator().next());
		
		KeyringConfig keyRing = new BouncyGpgKeyRing()
				.createKeyRing(hub.getName(),
						hub.getParentIdentity().getEmailAddress(),
						passphrase);
		
        ByteArrayOutputStream pubKeyRingBuffer = new ByteArrayOutputStream();
        keyRing.getPublicKeyRings().encode(pubKeyRingBuffer);
        pubKeyRingBuffer.close();
        byte[] publicKey = pubKeyRingBuffer.toByteArray();


        ByteArrayOutputStream secretKeyRingBuffer = new ByteArrayOutputStream();
        keyRing.getSecretKeyRings().encode(secretKeyRingBuffer);
        secretKeyRingBuffer.close();
        byte[] secretKey = secretKeyRingBuffer.toByteArray();
        
        ArmoredKeyPair keyPair =  ArmoredKeyPair.of(secretKey, publicKey);
        
        config.setKeys(keyPair);
        return config;
	}
	
}
