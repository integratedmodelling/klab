package org.integratedmodelling.klab.hub.payload;

public class UpdateEmailResponse {
	
	private String email;
	private String notVerifiedEmail;
	
	
	public UpdateEmailResponse() {
		super();
	}
	
	public UpdateEmailResponse(String email, String notVerifiedEmail) {
		super();
		this.email = email;
		this.notVerifiedEmail = notVerifiedEmail;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNotVerifiedEmail() {
		return notVerifiedEmail;
	}
	public void setNotVerifiedEmail(String notVerifiedEmail) {
		this.notVerifiedEmail = notVerifiedEmail;
	}
	
	

}
