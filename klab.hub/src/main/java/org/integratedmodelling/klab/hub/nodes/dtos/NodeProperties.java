package org.integratedmodelling.klab.hub.nodes.dtos;

import java.time.LocalDateTime;
import java.util.Properties;

import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.hub.licenses.dto.BouncyConfiguration;
import org.integratedmodelling.klab.hub.licenses.dto.IProperties;
import org.integratedmodelling.klab.hub.licenses.dto.LicenseConfiguration;

public class NodeProperties implements IProperties {
	public static final int CERT_FILE_TTL_DAYS = 365/2;
	
	public NodeProperties(MongoNode node, LicenseConfiguration config) {
		if(config.getClass().getName() == BouncyConfiguration.class.getName()) {
			BouncyNodeProperties(node, (BouncyConfiguration) config);
		}
	}
	
	private void BouncyNodeProperties(MongoNode node, BouncyConfiguration config) {
		LocalDateTime expires = LocalDateTime.now().plusDays(CERT_FILE_TTL_DAYS);
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
	
	private Properties properties;

	public Properties getProperties() {
		return properties;
	}
	

}
