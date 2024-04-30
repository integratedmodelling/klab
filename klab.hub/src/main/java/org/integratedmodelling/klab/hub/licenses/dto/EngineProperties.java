package org.integratedmodelling.klab.hub.licenses.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.Properties;

import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.hub.agreements.dto.Agreement;
import org.integratedmodelling.klab.hub.users.dto.ProfileResource;

public class EngineProperties implements IProperties {

    public EngineProperties(ProfileResource profile, Agreement agreement, LicenseConfiguration config) {
        if (config.getClass().getName().equals(BouncyConfiguration.class.getName())) {
            BouncyEngineProperties(profile, agreement, (BouncyConfiguration) config);
        }
    }

    private Optional<LocalDateTime> getExpirationTime(Agreement agreement) {
        if (agreement == null || !agreement.isExpirable()) {
            return Optional.empty();
        }
        return Optional.of(agreement.getValidDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime());
    }

    private void BouncyEngineProperties(ProfileResource profile, Agreement agreement, LicenseConfiguration config) {
        this.properties = new Properties();

        Optional<LocalDateTime> expirationTime = getExpirationTime(agreement);
        if (!expirationTime.isEmpty()) {
            this.properties.setProperty(KlabCertificate.KEY_EXPIRATION, expirationTime.get().toString());
        } else {
            // solution to avoid null pointer in old implementations, specially control center, put 5 years of validity
            // TODO manage NPE in old implementations
            this.properties.setProperty(KlabCertificate.KEY_EXPIRATION, LocalDateTime.now().plusDays(1825).toString());
        }

		this.properties.setProperty(KlabCertificate.KEY_USERNAME, profile.getUsername());
		this.properties.setProperty(KlabCertificate.KEY_EMAIL, profile.getEmail());
		if (agreement != null) { this.properties.setProperty(KlabCertificate.KEY_AGREEMENT, agreement.getId()); }
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
