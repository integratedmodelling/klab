package org.integratedmodelling.klab.hub.payload;

import org.integratedmodelling.klab.hub.api.TokenAuthentication;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;
import org.integratedmodelling.klab.hub.api.ProfileResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import net.minidev.json.JSONObject;

public class LoginResponse {
	
	private TokenAuthentication token;
	private ProfileResource profile;
	private boolean remote;
	
	public LoginResponse(TokenAuthentication token, ProfileResource profile, boolean remote) {
		this.token = token;
		this.profile = profile;
		this.remote = remote;
	}
	
    public LoginResponse() {
        // Jackson JSON mapping requires a no-arg constructor
    }
    
	public ResponseEntity<JSONObject> success() {
		JSONObject resp = new JSONObject();
		if (remote) {
		    resp.appendField("Profile", profile);
		} else {
		    resp.appendField("Profile", profile.getSafeProfile());
		}
		resp.appendField("Authentication", token);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authentication", token.getTokenString());
		return new ResponseEntity<JSONObject>(resp, headers, HttpStatus.OK);
    }
	
	public ResponseEntity<JSONObject> failure() {
		JSONObject resp = new JSONObject();
		resp.appendField("Message", "Username or password not found");
		return new ResponseEntity<JSONObject>(resp, HttpStatus.FORBIDDEN);
    }
	
	public ResponseEntity<JSONObject> getResponse() {
		if(this.token != null & this.profile != null) {
			return success();
		} else {
			return failure();
		}
	}
}
