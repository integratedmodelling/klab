package org.integratedmodelling.klab;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.services.IAuthorityService;

public enum Authorities implements IAuthorityService {
	
    INSTANCE;
	
	Map<String, IAuthority> authorities = Collections.synchronizedMap(new HashMap<>());
	
	private Authorities() {
		Services.INSTANCE.registerService(this, IAuthorityService.class);
	}

	@Override
	public Collection<IAuthority> getAuthorities() {
		return authorities.values();
	}

	@Override
	public IAuthority getAuthority(String authorityId) {
		return authorities.get(authorityId);
	}
}
