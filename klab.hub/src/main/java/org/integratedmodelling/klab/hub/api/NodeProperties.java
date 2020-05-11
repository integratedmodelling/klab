package org.integratedmodelling.klab.hub.api;

import java.util.Properties;

import org.integratedmodelling.klab.auth.KlabCertificate;
import org.joda.time.DateTime;

public class NodeProperties implements IProperties {
	public static final int CERT_FILE_TTL_DAYS = 365/2;
	
	public NodeProperties(MongoNode node, LicenseConfiguration config) {
		if(config.getClass().getName() == BouncyConfiguration.class.getName()) {
			BouncyNodeProperties(node, (BouncyConfiguration) config);
		}
		if(config.getClass().getName() == LegacyConfiguration.class.getName()) {
			LegacyNodeProperties(node, (LegacyConfiguration) config);
		}
	}
	
	private void BouncyNodeProperties(MongoNode node, BouncyConfiguration config) {
		DateTime expires = new DateTime().plusDays(CERT_FILE_TTL_DAYS);
		this.properties = new Properties();
		this.properties.setProperty(KlabCertificate.KEY_EXPIRATION, expires.toString());	
		this.properties.setProperty(KlabCertificate.KEY_NODENAME, node.getName());
		this.properties.setProperty(KlabCertificate.KEY_SIGNATURE, config.getKeyString());
		this.properties.setProperty(KlabCertificate.KEY_PARTNER_NAME, config.getName());
		this.properties.setProperty(KlabCertificate.KEY_PARTNER_EMAIL, config.getEmail());
		this.properties.setProperty(KlabCertificate.KEY_PARTNER_HUB, config.getHubUrl());
		this.properties.setProperty(KlabCertificate.KEY_EMAIL, node.getEmail());
		this.properties.setProperty("klab.wordlview", "im");
		this.properties.setProperty(KlabCertificate.KEY_URL, node.getUrl());
		this.properties.setProperty(KlabCertificate.KEY_CERTIFICATE_TYPE, KlabCertificate.Type.NODE.toString());
		this.properties.setProperty(KlabCertificate.KEY_CERTIFICATE_LEVEL, KlabCertificate.Level.INSTITUTIONAL.toString());
	}
	
	private void LegacyNodeProperties(MongoNode node, LegacyConfiguration config) {
		DateTime expires = new DateTime().plusDays(CERT_FILE_TTL_DAYS);
		this.properties = new Properties();
		this.properties.setProperty(KlabCertificate.KEY_EXPIRATION, expires.toString());	
		this.properties.setProperty(KlabCertificate.KEY_NODENAME, node.getName());
		this.properties.setProperty(KlabCertificate.KEY_SIGNATURE, config.getKeyString());
		this.properties.setProperty(KlabCertificate.KEY_PARTNER_NAME, "integratedmodelling.org");
		this.properties.setProperty(KlabCertificate.KEY_PARTNER_EMAIL, "info@integratedmodelling.org");
		this.properties.setProperty(KlabCertificate.KEY_PARTNER_HUB, config.getHubUrl());
		this.properties.setProperty(KlabCertificate.KEY_EMAIL, node.getEmail());
		this.properties.setProperty("klab.wordlview", "im");
		this.properties.setProperty(KlabCertificate.KEY_URL, node.getUrl());
		this.properties.setProperty(KlabCertificate.KEY_CERTIFICATE_TYPE, KlabCertificate.Type.NODE.toString());
		this.properties.setProperty(KlabCertificate.KEY_CERTIFICATE_LEVEL, KlabCertificate.Level.INSTITUTIONAL.toString());
	}

	private Properties properties;

	public Properties getProperties() {
		return properties;
	}
	

}
