package org.integratedmodelling.klab.hub.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStore.Entry;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;

public enum NetworkKeyManager {

	INSTANCE;

	private KeyPair keyPair;
	private String publicKeyBase64;
	private String subjectDN;

	public PublicKey getPublicKey() {
		return keyPair.getPublic();
	}
	
	public PrivateKey getPrivateKey() {
		return keyPair.getPrivate();
	}
	
	public String getEncodedPublicKey() {
		return publicKeyBase64;
	}

	/** 
	 * Ensure we have a key pair and it is linked to the server key from the 
	 * certificate. The key is saves as a certificate so it persists across
	 * runs.
	 */
	public boolean initialize(String serverKey, String subjectDN) {

		this.subjectDN = subjectDN;
		boolean ret = false;
		
		File directory = Configuration.INSTANCE.getDataPath("hub");
		File certificate = new File(directory + File.separator + "certificate.pkcs12");
		char[] password = serverKey.toCharArray();

		if (certificate.exists()) {
			try {
				keyPair = loadFromPKCS12(certificate, password);
			} catch (Throwable e) {
				Logging.INSTANCE.error(e);
				FileUtils.deleteQuietly(certificate);
				// sorry
				keyPair = null;
			}
		}

		if (keyPair == null) {

			Logging.INSTANCE.info("generating server security keys");

			keyPair = generateKeyPair();
			try {
				storeToPKCS12(certificate, password, keyPair);
			} catch (Exception e) {
				throw new KlabInternalErrorException(e);
			}
			ret = true;
		}

		this.publicKeyBase64 = DatatypeConverter.printBase64Binary(keyPair.getPublic().getEncoded());
		
		return ret;
	}

	private KeyPair generateKeyPair() {
		
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(2048, new SecureRandom());
			KeyPair pair = generator.generateKeyPair();
			return pair;
		} catch (Exception e) {
			throw new KlabInternalErrorException(e);
		}
	}

	private Certificate selfSign(KeyPair keyPair, String subjectDN)
			throws OperatorCreationException, CertificateException, IOException {
		Provider bcProvider = new BouncyCastleProvider();
		Security.addProvider(bcProvider);

		long now = System.currentTimeMillis();
		Date startDate = new Date(now);

		X500Name dnName = new X500Name(subjectDN);

		// Using the current timestamp as the certificate serial number
		BigInteger certSerialNumber = new BigInteger(Long.toString(now));

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		// 1 Yr validity
		calendar.add(Calendar.YEAR, 1);

		Date endDate = calendar.getTime();

		// Use appropriate signature algorithm based on your keyPair algorithm.
		String signatureAlgorithm = "SHA256WithRSA";

		SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded());

		X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(dnName, certSerialNumber, startDate,
				endDate, dnName, subjectPublicKeyInfo);

		ContentSigner contentSigner = new JcaContentSignerBuilder(signatureAlgorithm).setProvider(bcProvider)
				.build(keyPair.getPrivate());

		X509CertificateHolder certificateHolder = certificateBuilder.build(contentSigner);

		Certificate selfSignedCert = new JcaX509CertificateConverter().getCertificate(certificateHolder);

		return selfSignedCert;
	}
	
	private KeyPair loadFromPKCS12(File file, char[] password) throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, FileNotFoundException, IOException, UnrecoverableEntryException {

		KeyStore pkcs12KeyStore = KeyStore.getInstance("PKCS12");

		try (FileInputStream fis = new FileInputStream(file);) {
			pkcs12KeyStore.load(fis, password);
		}

		KeyStore.ProtectionParameter param = new KeyStore.PasswordProtection(password);
		Entry entry = pkcs12KeyStore.getEntry(subjectDN, param);
		if (!(entry instanceof PrivateKeyEntry)) {
			throw new KlabAuthorizationException("certificate is obsolete or invalid");
		}
		PrivateKeyEntry privKeyEntry = (PrivateKeyEntry) entry;
		PublicKey publicKey = privKeyEntry.getCertificate().getPublicKey();
		PrivateKey privateKey = privKeyEntry.getPrivateKey();
		return new KeyPair(publicKey, privateKey);
	}

	private void storeToPKCS12(File file, char[] password, KeyPair generatedKeyPair)
			throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException,
			FileNotFoundException, OperatorCreationException {

		Certificate selfSignedCertificate = selfSign(generatedKeyPair, "CN=" + subjectDN);

		KeyStore pkcs12KeyStore = KeyStore.getInstance("PKCS12");
		pkcs12KeyStore.load(null, null);

		KeyStore.Entry entry = new PrivateKeyEntry(generatedKeyPair.getPrivate(),
				new Certificate[] { selfSignedCertificate });
		KeyStore.ProtectionParameter param = new KeyStore.PasswordProtection(password);

		pkcs12KeyStore.setEntry(subjectDN, entry, param);

		try (FileOutputStream fos = new FileOutputStream(file)) {
			pkcs12KeyStore.store(fos, password);
		}
	}

}