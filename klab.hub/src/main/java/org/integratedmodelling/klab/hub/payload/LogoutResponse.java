package org.integratedmodelling.klab.hub.payload;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.minidev.json.JSONObject;

public class LogoutResponse {
	private String username;
	
	public LogoutResponse(String username) {
		this.username = username;
	}
	
    public LogoutResponse() {
        // Jackson JSON mapping requires a no-arg constructor
    }
    
	public ResponseEntity<JSONObject> success() {
		JSONObject resp = new JSONObject();
		resp.appendField("Message", String.format("%s has succesfully logged out", username));
		return new ResponseEntity<JSONObject>(resp, HttpStatus.OK);
    }
	
	public ResponseEntity<JSONObject> failure() {
		JSONObject resp = new JSONObject();
		resp.appendField("Message", "Token our username not found");
		return new ResponseEntity<JSONObject>(resp, HttpStatus.BAD_REQUEST);
    }
	
	public ResponseEntity<JSONObject> getResponse() {
		if(this.username != null) {
			return success();
		} else {
			return failure();
		}
	}
}
