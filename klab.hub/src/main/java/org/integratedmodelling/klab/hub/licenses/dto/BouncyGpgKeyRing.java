package org.integratedmodelling.klab.hub.licenses.dto;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.bouncycastle.openpgp.PGPException;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.BouncyGPG;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.algorithms.Feature;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.algorithms.PGPCompressionAlgorithms;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.algorithms.PGPHashAlgorithms;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.algorithms.PGPSymmetricEncryptionAlgorithms;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.generation.KeyFlag;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.generation.KeySpec;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.generation.KeySpecBuilder;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.generation.Passphrase;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.generation.type.RSAForEncryptionKeyType;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.generation.type.RSAForSigningKeyType;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.generation.type.length.RsaLength;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.keyrings.KeyringConfig;

public class BouncyGpgKeyRing {
	private RsaLength keyLength = RsaLength.RSA_4096_BIT;


	public KeyringConfig createKeyRing(String userId, String email, String passphrase) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, PGPException, IOException {
		return BouncyGPG
			.createKeyring()
				.withSubKey(createSubKey())
				.withSubKey(createAuthSubkey())
				.withSubKey(createEcryptSubkey())
				.withMasterKey(createMasterKey())
				.withPrimaryUserId(userId + " <" + email + ">")
				.withPassphrase(Passphrase.fromString(passphrase))
			.build();
	}
	
	private KeySpec createSubKey() {
		return KeySpecBuilder
				.newSpec(RSAForSigningKeyType.withLength(keyLength))
				.allowKeyToBeUsedTo(KeyFlag.SIGN_DATA)
				.withDefaultAlgorithms();
	}
	
	private KeySpec createAuthSubkey() {
		return KeySpecBuilder
				.newSpec(RSAForEncryptionKeyType.withLength(RsaLength.RSA_2048_BIT))
				.allowKeyToBeUsedTo(KeyFlag.AUTHENTICATION)
				.withDefaultAlgorithms();
	}

	private KeySpec createEcryptSubkey() {
		return KeySpecBuilder
                .newSpec(RSAForEncryptionKeyType.withLength(RsaLength.RSA_2048_BIT))
                .allowKeyToBeUsedTo(KeyFlag.ENCRYPT_COMMS, KeyFlag.ENCRYPT_STORAGE)
                .withDefaultAlgorithms();
	}
	
	private KeySpec createMasterKey() {
		return KeySpecBuilder.newSpec(
				RSAForSigningKeyType.withLength(RsaLength.RSA_3072_BIT))
				.allowKeyToBeUsedTo(KeyFlag.CERTIFY_OTHER)
				.withDetailedConfiguration()
				.withPreferredSymmetricAlgorithms(
                    PGPSymmetricEncryptionAlgorithms.recommendedAlgorithms())
				.withPreferredHashAlgorithms(
                    PGPHashAlgorithms.recommendedAlgorithms())
				.withPreferredCompressionAlgorithms(
					PGPCompressionAlgorithms.recommendedAlgorithms())
				.withFeature(Feature.MODIFICATION_DETECTION)
				.done();
	}

}
