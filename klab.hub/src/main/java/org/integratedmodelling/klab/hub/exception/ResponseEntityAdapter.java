package org.integratedmodelling.klab.hub.exception;

import org.joda.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.minidev.json.JSONObject;

public class ResponseEntityAdapter<E extends Exception> {

	public ResponseEntityAdapter(HttpStatus status, E e) {
		JSONObject body = new JSONObject();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", e.getMessage());
        body.put("error", e.getClass().getSimpleName());

        this.response=  new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<Object> getResponse() {
		return response;
	}

	private ResponseEntity<Object> response;
	
	
}
