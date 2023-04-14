package org.integratedmodelling.klab.hub.service.implementation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.codec.DecoderException;
import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.api.MongoNode;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.config.LegacyLicenseConfig;
import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.integratedmodelling.klab.hub.service.LicenseServiceLegacy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javax0.license3j.licensor.License;


@Component
public class LicenseServiceLegacyImpl implements LicenseServiceLegacy {
	
	@Autowired
	LinkConfig linkConfig;
	
	@Autowired
	LegacyLicenseConfig licenseConfig;
	
	public static final int CERT_FILE_TTL_DAYS = 365;
	
	private final String ENGINE_CERT_FILE_NAME = KlabCertificate.DEFAULT_ENGINE_CERTIFICATE_FILENAME;
	
	private final String NODE_CERT_FILE_NAME = KlabCertificate.DEFAULT_NODE_CERTIFICATE_FILENAME;

	@Override
	public byte[] generateCert(User user, Agreement agreement) {
		try {
			return generateCertFile(user, agreement);
		} catch (Exception e) {
			throw new KlabException(e);
		}
	}
	
	@Override
	public byte[] generateCert(MongoNode node) {
		try {
			return generateCertFile(node);
		} catch (Exception e) {
			throw new KlabException(e);
		}
	}
	
	@Override
	public byte[] generateCertFile(User user, Agreement agreement) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException, PGPException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		Properties properties = getPropertiesString(user, agreement);
		properties.setProperty(KlabCertificate.KEY_CERTIFICATE, encodedLicenseString(properties));
		properties.store(byteArrayOutputStream, "Engine Certificate Generated On " + new Date());
		return byteArrayOutputStream.toByteArray();
	}
	
	@Override
	public byte[] generateCertFile(MongoNode node) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException, PGPException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		Properties properties = getPropertiesString(node);
		properties.setProperty(KlabCertificate.KEY_CERTIFICATE, encodedLicenseString(properties));
		properties.store(byteArrayOutputStream, "Node Certificate Generated On " + new Date());
		return byteArrayOutputStream.toByteArray();
	}

	@Override
	public Properties getPropertiesString(User user, Agreement agreement) {
        Properties properties = new Properties();   
        LocalDateTime expires = LocalDateTime.now().plusDays(CERT_FILE_TTL_DAYS/2);
		properties.setProperty(KlabCertificate.KEY_EXPIRATION, expires.toString());	
		properties.setProperty(KlabCertificate.KEY_USERNAME, user.getUsername());
		properties.setProperty(KlabCertificate.KEY_AGREEMENT, agreement.getId());
		properties.setProperty(KlabCertificate.KEY_EMAIL, user.getEmail());
		properties.setProperty(KlabCertificate.KEY_SIGNATURE, licenseConfig.getKey());
		properties.setProperty(KlabCertificate.KEY_PARTNER_NAME, "integratedmodelling.org");
		properties.setProperty(KlabCertificate.KEY_PARTNER_EMAIL, "info@integratedmodelling.org");
		properties.setProperty(KlabCertificate.KEY_PARTNER_HUB, linkConfig.getSiteUrl().toString());
		properties.setProperty(KlabCertificate.KEY_NODENAME, "im");
		properties.setProperty(KlabCertificate.KEY_CERTIFICATE_TYPE, KlabCertificate.Type.ENGINE.toString());
		properties.setProperty(KlabCertificate.KEY_CERTIFICATE_LEVEL, KlabCertificate.Level.USER.toString());
		return properties;
	}
	
    @Override
	public Properties getPropertiesString(MongoNode node) throws IOException {
        Properties properties = new Properties();   
        LocalDateTime expires = LocalDateTime.now().plusDays(CERT_FILE_TTL_DAYS/2);
		properties.setProperty(KlabCertificate.KEY_EXPIRATION, expires.toString());	
		properties.setProperty(KlabCertificate.KEY_NODENAME, node.getName());
		properties.setProperty(KlabCertificate.KEY_EMAIL, node.getEmail());
		properties.setProperty(KlabCertificate.KEY_SIGNATURE, licenseConfig.getKey());
		properties.setProperty(KlabCertificate.KEY_PARTNER_NAME, "integratedmodelling.org");
		properties.setProperty(KlabCertificate.KEY_PARTNER_EMAIL, "info@integratedmodelling.org");
		properties.setProperty(KlabCertificate.KEY_PARTNER_HUB, linkConfig.getSiteUrl().toString());
		properties.setProperty("klab.wordlview", "im");
		properties.setProperty(KlabCertificate.KEY_URL, node.getUrl());
		properties.setProperty(KlabCertificate.KEY_CERTIFICATE_TYPE, KlabCertificate.Type.NODE.toString());
		properties.setProperty(KlabCertificate.KEY_CERTIFICATE_LEVEL, KlabCertificate.Level.INSTITUTIONAL.toString());
		return properties;
    }
	
    private String encodedLicenseString(Properties properties) 
			throws IOException, PGPException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException {
		License license = new License();
		Writer stringWriter = new StringWriter();
        String generatedBy = String.format("Generated by %s on ", linkConfig.getSiteName()) + new Date();
        properties.store(stringWriter, generatedBy);
        license.setLicense(stringWriter.toString());
        license.loadKey(licenseConfig.getSecRing().getFilename(),licenseConfig.getUserId());
        String encodedLicenseString = license.encodeLicense(licenseConfig.getPassword());
        return encodedLicenseString;
	}
	
    @Override
	public Properties readCertFileContent(String certFileContent) throws IOException, PGPException, DecoderException {
        License license = new License();
        license.loadKeyRing(licenseConfig.getPubRing().getFilename(), licenseConfig.getPubRing().getDigest());
        license.setLicenseEncoded(certFileContent);
        String propertiesString = license.getLicenseString();
        Properties result = new Properties();
        result.load(new StringReader(propertiesString));
        return result;
    }

	@Override
	public String get_ENGINE_CERT_FILE_NAME() {
		return ENGINE_CERT_FILE_NAME;
	}
	
	@Override
	public String get_NODE_CERT_FILE_NAME() {
		return NODE_CERT_FILE_NAME;
	}
}
