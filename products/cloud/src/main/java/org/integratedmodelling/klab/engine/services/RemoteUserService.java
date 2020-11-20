package org.integratedmodelling.klab.engine.services;

import org.integratedmodelling.klab.rest.RemoteUserAuthenticationRequest;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public abstract interface RemoteUserService {
	
	abstract ResponseEntity<?> login(RemoteUserAuthenticationRequest request) throws JSONException;

}
