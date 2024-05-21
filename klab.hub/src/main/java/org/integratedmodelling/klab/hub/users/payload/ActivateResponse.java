package org.integratedmodelling.klab.hub.users.payload;

public class ActivateResponse {
	
	public String username;
	
	public ActivateResponse(String username) {
		String.format("Please check verify account %s!", username);
	}
	
	public ActivateResponse() {	
	}
	
    @Override
    public String toString() {
        return String.format("%s", username);
    }
}
