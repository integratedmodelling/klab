package org.integratedmodelling.klab.hub.licenses.services;


import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.generation.KeyFlag;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.generation.KeySpecBuilder;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.generation.KeySpec;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.generation.type.RSAForEncryptionKeyType;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.generation.type.RSAForSigningKeyType;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.generation.type.length.RsaLength;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.keyrings.KeyringConfig;

public class BouncyGpgServiceImpl implements BouncyGpgService {
	
	private RsaLength keyLength = RsaLength.RSA_4096_BIT;

	@Override
	public KeyringConfig createKeyRing() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private KeySpec createSubKey() {
		return KeySpecBuilder
				.newSpec(RSAForSigningKeyType.withLength(keyLength))
				.allowKeyToBeUsedTo(KeyFlag.SIGN_DATA)
				.withDefaultAlgorithms();
	}
	
	private KeySpec createEncryptionSubkey() {
		return KeySpecBuilder
				.newSpec(RSAForEncryptionKeyType.withLength(RsaLength.RSA_2048_BIT))
				.allowKeyToBeUsedTo(KeyFlag.AUTHENTICATION)
				.withDefaultAlgorithms();
	}

	private KeySpec createAuthenticationSubkey() {
		
	}
    KeySpecBuilder
            .newSpec(RSAForEncryptionKeyType.withLength(RsaLength.RSA_2048_BIT))
            .allowKeyToBeUsedTo(KeyFlag.AUTHENTICATION)
            .withDefaultAlgorithms();

    final KeySpec encryptionSubey = KeySpecBuilder
            .newSpec(RSAForEncryptionKeyType.withLength(RsaLength.RSA_2048_BIT))
            .allowKeyToBeUsedTo(KeyFlag.ENCRYPT_COMMS, KeyFlag.ENCRYPT_STORAGE)
            .withDefaultAlgorithms();

    final KeySpec masterKey = KeySpecBuilder.newSpec(
            RSAForSigningKeyType.withLength(RsaLength.RSA_3072_BIT)
    )
            .allowKeyToBeUsedTo(KeyFlag.CERTIFY_OTHER)
            .withDetailedConfiguration()
            .withPreferredSymmetricAlgorithms(
                    PGPSymmetricEncryptionAlgorithms.recommendedAlgorithms()
            )
            .withPreferredHashAlgorithms(
                    PGPHashAlgorithms.recommendedAlgorithms()
            )
            .withPreferredCompressionAlgorithms(
                    PGPCompressionAlgorithms.recommendedAlgorithms()
            )
            .withFeature(Feature.MODIFICATION_DETECTION)
            .done();

    final KeyringConfig complexKeyRing = BouncyGPG
            .createKeyring()
            .withSubKey(signingSubey)
            .withSubKey(authenticationSubey)
            .withSubKey(encryptionSubey)
            .withMasterKey(masterKey)
            .withPrimaryUserId("Juliet Capulet <juliet@example.com>")
            .withPassphrase(Passphrase.fromString("O Romeo, Romeo! Wherefore art thou Romeo?"))
            .build();

    return complexKeyRing;
}
