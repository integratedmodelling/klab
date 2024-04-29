package org.integratedmodelling.klab.hub.licenses.services;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.bcpg.sig.Features;
import org.bouncycastle.bcpg.sig.KeyFlags;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPKeyPair;
import org.bouncycastle.openpgp.PGPKeyRingGenerator;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureSubpacketGenerator;
import org.bouncycastle.openpgp.PGPSignatureSubpacketVector;
import org.bouncycastle.openpgp.operator.PBESecretKeyEncryptor;
import org.bouncycastle.openpgp.operator.bc.BcPBESecretKeyEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDigestCalculatorProvider;
import org.bouncycastle.openpgp.operator.bc.BcPGPKeyPair;
import org.integratedmodelling.klab.hub.licenses.dto.ArmoredKeyPair;
import org.springframework.stereotype.Service;

@Service
public class PgpKeyServiceImpl implements PgpKeyService {
	
	private BigInteger publicExponent = BigInteger.valueOf(0x10001);
	private SecureRandom random = new SecureRandom();
	private int certainty = 12; 
	private int s2kcount = 0xc0;
	
    public ArmoredKeyPair generateKeys(int keySize, String userIdName, String userIdEmail, String passphrase) throws PGPException {

        Date now = new Date();

        RSAKeyPairGenerator keyPairGenerator = keyPairGenerator(keySize);

        PGPKeyPair encryptionKeyPair = encryptionKeyPair(now, keyPairGenerator);
        PGPSignatureSubpacketVector encryptionKeySignature = encryptionKeySignature();

        PGPKeyPair signingKeyPair = signingKeyPair(keyPairGenerator, now);
        PGPSignatureSubpacketVector signingKeySignature = signingKeySignature();

        PGPKeyRingGenerator keyRingGenerator = new PGPKeyRingGenerator(
                PGPSignature.POSITIVE_CERTIFICATION,
                signingKeyPair,
                userIdName + " <" + userIdEmail + ">",
                new BcPGPDigestCalculatorProvider().get(HashAlgorithmTags.SHA1),
                signingKeySignature,
                null,
                new BcPGPContentSignerBuilder(signingKeyPair.getPublicKey().getAlgorithm(), HashAlgorithmTags.SHA1),
                secretKeyEncryptor(passphrase)
        );
        keyRingGenerator.addSubKey(encryptionKeyPair, encryptionKeySignature, null);

        try {
            return ArmoredKeyPair.of(
                    generateArmoredSecretKeyRing(keyRingGenerator).toByteArray(),
                    generateArmoredPublicKeyRing(keyRingGenerator).toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private RSAKeyPairGenerator keyPairGenerator(int keySize) {
        RSAKeyPairGenerator keyPairGenerator = new RSAKeyPairGenerator();
        keyPairGenerator.init(new RSAKeyGenerationParameters(publicExponent, random, keySize, certainty));
        return keyPairGenerator;
    }

    private PGPKeyPair encryptionKeyPair(Date now, RSAKeyPairGenerator rsaKeyPairGenerator) throws PGPException {
        return new BcPGPKeyPair(PGPPublicKey.RSA_GENERAL, rsaKeyPairGenerator.generateKeyPair(), now);
    }

    private PGPKeyPair signingKeyPair(RSAKeyPairGenerator rsaKeyPairGenerator, Date date) throws PGPException {
        return new BcPGPKeyPair(PGPPublicKey.RSA_SIGN, rsaKeyPairGenerator.generateKeyPair(), date);
    }

    private PGPSignatureSubpacketVector encryptionKeySignature() {
        PGPSignatureSubpacketGenerator encryptionKeySignatureGenerator = new PGPSignatureSubpacketGenerator();
        encryptionKeySignatureGenerator.setKeyFlags(false, KeyFlags.ENCRYPT_COMMS | KeyFlags.ENCRYPT_STORAGE);
        return encryptionKeySignatureGenerator.generate();
    }

    private PGPSignatureSubpacketVector signingKeySignature() {
        PGPSignatureSubpacketGenerator signingKeySignatureGenerator = new PGPSignatureSubpacketGenerator();
        signingKeySignatureGenerator.setKeyFlags(false, KeyFlags.SIGN_DATA | KeyFlags.CERTIFY_OTHER); // GPG seems to generate keys with ENCRYPT_COMMS and ENCRYPT_STORAGE flags. However this is the signing key, so I'd avoid setting those flags. Omitting them does not seem to have an impact on the functioning of BouncyGPG...
        signingKeySignatureGenerator.setPreferredSymmetricAlgorithms(false, new int[]{SymmetricKeyAlgorithmTags.AES_256});
        signingKeySignatureGenerator.setPreferredHashAlgorithms(false, new int[]{HashAlgorithmTags.SHA512});
        signingKeySignatureGenerator.setFeature(false, Features.FEATURE_MODIFICATION_DETECTION);
        return signingKeySignatureGenerator.generate();
    }

    private PBESecretKeyEncryptor secretKeyEncryptor(String passphrase) throws PGPException {
        return new BcPBESecretKeyEncryptorBuilder(
                PGPEncryptedData.AES_256,
                new BcPGPDigestCalculatorProvider().get(HashAlgorithmTags.SHA256),
                s2kcount)
                .build(passphrase.toCharArray());
    }

    private ByteArrayOutputStream generateArmoredSecretKeyRing(PGPKeyRingGenerator keyRingGenerator) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (
                ArmoredOutputStream armoredOutputStream = new ArmoredOutputStream(outputStream);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(armoredOutputStream)
        ) {
            keyRingGenerator.generateSecretKeyRing().encode(bufferedOutputStream);
        }
        return outputStream;
    }

    private ByteArrayOutputStream generateArmoredPublicKeyRing(PGPKeyRingGenerator keyRingGenerator) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (
                ArmoredOutputStream armoredOutputStream = new ArmoredOutputStream(outputStream);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(armoredOutputStream)
        ) {
            keyRingGenerator.generatePublicKeyRing().encode(bufferedOutputStream, true);
        }
        return outputStream;
    }
}
