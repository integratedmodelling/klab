package org.integratedmodelling.klab.hub.api;

import java.time.LocalDateTime;
import java.util.Properties;

import org.integratedmodelling.klab.auth.KlabCertificate;

public class EngineProperties implements IProperties {
	
	public static final int CERT_FILE_TTL_DAYS = 365/2;
	
	public EngineProperties(ProfileResource profile, LicenseConfiguration config) {
		if(config.getClass().getName() == BouncyConfiguration.class.getName()) {
			BouncyEngineProperties(profile, (BouncyConfiguration) config);
		}
		if(config.getClass().getName() == LegacyConfiguration.class.getName()) {
			LegacyEngineProperties(profile, (LegacyConfiguration) config);
		}
	}
	
	private void LegacyEngineProperties(ProfileResource profile, LegacyConfiguration config) {
		LocalDateTime expires = LocalDateTime.now().plusDays(CERT_FILE_TTL_DAYS);
		this.properties = new Properties();
		this.properties.setProperty(KlabCertificate.KEY_EXPIRATION, expires.toString());	
		this.properties.setProperty(KlabCertificate.KEY_USERNAME, profile.getUsername());
		this.properties.setProperty(KlabCertificate.KEY_EMAIL, profile.getEmail());
		this.properties.setProperty(KlabCertificate.KEY_SIGNATURE, config.getKeyString());
		this.properties.setProperty(KlabCertificate.KEY_NODENAME, "im");
		this.properties.setProperty(KlabCertificate.KEY_PARTNER_NAME, "integratedmodelling.org");
		this.properties.setProperty(KlabCertificate.KEY_PARTNER_EMAIL, "info@integratedmodelling.org");
		this.properties.setProperty(KlabCertificate.KEY_PARTNER_HUB, config.getHubUrl());
		this.properties.setProperty(KlabCertificate.KEY_CERTIFICATE_TYPE, KlabCertificate.Type.ENGINE.toString());
		this.properties.setProperty(KlabCertificate.KEY_CERTIFICATE_LEVEL, KlabCertificate.Level.USER.toString());
	}

	private void BouncyEngineProperties(ProfileResource profile, BouncyConfiguration config) {
		LocalDateTime expires =LocalDateTime.now().plusDays(CERT_FILE_TTL_DAYS);
		this.properties = new Properties();
		this.properties.setProperty(KlabCertificate.KEY_EXPIRATION, expires.toString());	
		this.properties.setProperty(KlabCertificate.KEY_USERNAME, profile.getUsername());
		this.properties.setProperty(KlabCertificate.KEY_EMAIL, profile.getEmail());
		this.properties.setProperty(KlabCertificate.KEY_SIGNATURE, config.getKeyString());
		this.properties.setProperty(KlabCertificate.KEY_PARTNER_NAME, config.getName());
		this.properties.setProperty(KlabCertificate.KEY_PARTNER_EMAIL, config.getEmail());
		this.properties.setProperty(KlabCertificate.KEY_PARTNER_HUB, config.getHubUrl());
		this.properties.setProperty(KlabCertificate.KEY_CERTIFICATE_TYPE, KlabCertificate.Type.ENGINE.toString());
		this.properties.setProperty(KlabCertificate.KEY_CERTIFICATE_LEVEL, KlabCertificate.Level.USER.toString());
	}

	private Properties properties;

	public Properties getProperties() {
		return properties;
	}
}
