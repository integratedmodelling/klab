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
	
	enum FailureReason {
		USERNAME_OR_PASSWORD_NOT_FOUND,
		USER_STATUS_IS_SUSPENDED
	}
	
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
	
	public ResponseEntity<JSONObject> failure(FailureReason reason) {
		JSONObject resp = new JSONObject();
		switch(reason) {
		case USERNAME_OR_PASSWORD_NOT_FOUND:
			resp.appendField("Message", "Username or password not found");
			return new ResponseEntity<JSONObject>(resp, HttpStatus.FORBIDDEN);
		case USER_STATUS_IS_SUSPENDED:
			resp.appendField("Message", "Account is suspended");
			return new ResponseEntity<JSONObject>(resp, HttpStatus.UNAUTHORIZED);
		default:
			return new ResponseEntity<JSONObject>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	public ResponseEntity<JSONObject> getResponse() {
		if (this.profile.accountStatus == AccountStatus.suspended) {
			return failure(FailureReason.USER_STATUS_IS_SUSPENDED);
		}			
		if(this.profile != null && this.token != null) {
			return success();
		}		
		return failure(FailureReason.USERNAME_OR_PASSWORD_NOT_FOUND);

	}
}
