package org.integratedmodelling.klab.hub.licenses.services;

import org.integratedmodelling.klab.hub.api.LicenseConfiguration;
import org.integratedmodelling.klab.hub.service.GenericHubService;
import org.springframework.stereotype.Service;

@Service
public interface LicenseConfigService extends GenericHubService<LicenseConfiguration>{
	LicenseConfiguration getDefaultConfig();
	LicenseConfiguration setAsDefaultConfig(String name);
	LicenseConfiguration getConfigByKey(String key);
}