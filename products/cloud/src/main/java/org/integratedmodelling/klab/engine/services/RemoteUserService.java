package org.integratedmodelling.klab.engine.services;

import org.integratedmodelling.klab.rest.UserAuthenticationRequest;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public abstract interface RemoteUserService {
	
	abstract ResponseEntity<?> login(UserAuthenticationRequest request) throws JSONException;

}
