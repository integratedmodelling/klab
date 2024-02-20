package org.integratedmodelling.klab.hub.licenses.services;

import org.integratedmodelling.klab.hub.licenses.dto.LicenseConfiguration;
import org.integratedmodelling.klab.hub.services.GenericHubService;
import org.springframework.stereotype.Service;

@Service
public interface LicenseConfigService extends GenericHubService<LicenseConfiguration>{
	LicenseConfiguration getDefaultConfig();
	LicenseConfiguration setAsDefaultConfig(String name);
	LicenseConfiguration getConfigByKey(String key);
}