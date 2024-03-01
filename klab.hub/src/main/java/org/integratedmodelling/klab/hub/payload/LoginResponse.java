package org.integratedmodelling.klab.hub.payload;

import org.integratedmodelling.klab.hub.api.TokenAuthentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import net.minidev.json.JSONObject;

public class LoginResponse<T> {
	
	private TokenAuthentication token;
	private T profile;
	
	public LoginResponse(TokenAuthentication token, T profile) {
		this.token = token;
		this.profile = profile;
	}
	
    public LoginResponse() {
        // Jackson JSON mapping requires a no-arg constructor
    }
    
	public ResponseEntity<JSONObject> success() {
		JSONObject resp = new JSONObject();
		resp.appendField("Profile", profile);
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
		if(this.token != null & profile != null) {
			return success();
		} else {
			return failure();
		}
	}
}
