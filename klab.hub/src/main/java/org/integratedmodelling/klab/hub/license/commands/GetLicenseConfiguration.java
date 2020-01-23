package org.integratedmodelling.klab.hub.license.commands;

import java.util.List;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.hub.license.LicenseConfiguration;
import org.integratedmodelling.klab.hub.repository.LicenseConfigRepository;

import com.google.common.base.Optional;

public class GetLicenseConfiguration {
	
	private LicenseConfigRepository repository;
	
	public GetLicenseConfiguration(LicenseConfigRepository repository) {
		super();
		this.repository = repository;
	}
	
	public LicenseConfiguration execute() {
		List<LicenseConfiguration> configurations = repository.findAll();
		if(configurations.size() != 0) {
			Optional<LicenseConfiguration> config = repository.findById(configurations.get(0).getId());
			if(config.isPresent()) {
				return config.get();
			} else {
				Logging.INSTANCE.warn("No LicenseConfiguration found");
				return null;
			}
		} else {
			return null;
		}
	}
	
	

}
