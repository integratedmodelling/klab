package org.integratedmodelling.klab.hub.api;

import java.util.Properties;

import javax.ws.rs.BadRequestException;

public class LicenseGenerator {
	
	public LicenseGenerator(LicenseConfiguration config, Properties props) {
		super();
		this.config = config;
		this.props = props;
	}
	LicenseConfiguration config;
	Properties props;

	public byte[] generate() {
		if(config.getClass().getName().equals(BouncyConfiguration.class.getName())) {
			return new BouncyLicense().generate(props, config);
		}
		if(config.getClass().getName().equals(LegacyConfiguration.class.getName())) {
			return new LegacyLicense().generate(props, config);
		} else {
			throw new BadRequestException();
		}
	}
}
