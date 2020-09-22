package org.integratedmodelling.klab.engine.services;

import org.integratedmodelling.klab.rest.UserAuthenticationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public abstract interface RemoteUserSerice {
	
	abstract ResponseEntity<Object> login(UserAuthenticationRequest request);

}
