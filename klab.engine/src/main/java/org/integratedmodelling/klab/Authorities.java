package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.services.IAuthorityService;

public enum Authorities implements IAuthorityService {
	
    INSTANCE;
	
	private Authorities() {
		Services.INSTANCE.registerService(this, IAuthorityService.class);
	}
}
