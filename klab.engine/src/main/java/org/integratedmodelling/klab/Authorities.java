package org.integratedmodelling.klab;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.services.IAuthorityService;
import org.integratedmodelling.klab.authorities.GBIFAuthority;

public enum Authorities implements IAuthorityService {
	
    INSTANCE;
	
	Map<String, IAuthority> authorities = Collections.synchronizedMap(new HashMap<>());
	
	private Authorities() {
		Services.INSTANCE.registerService(this, IAuthorityService.class);
		GBIFAuthority.register();
	}

	@Override
	public Collection<IAuthority> getAuthorities() {
		return authorities.values();
	}

	@Override
	public IAuthority getAuthority(String authorityId) {
		return authorities.get(authorityId);
	}

	public void registerAuthority(IAuthority authority) {
		authorities.put(authority.getName(), authority);
	}
}
