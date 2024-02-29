package org.integratedmodelling.klab.hub.users.payload;

public class SignupResponse {
	
	public String email;
	
	public String username;
	
	
	public SignupResponse(String email) {
		email = 
		String.format("Please check verify account %s!", email);
	}

	public SignupResponse() {
		
	}
	
    @Override
    public String toString() {
        return String.format("%s", email);
    }
}
