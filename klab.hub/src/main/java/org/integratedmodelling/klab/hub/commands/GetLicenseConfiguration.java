package org.integratedmodelling.klab.hub.commands;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.hub.api.LicenseConfiguration;
import org.integratedmodelling.klab.hub.repository.LicenseConfigRepository;


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
