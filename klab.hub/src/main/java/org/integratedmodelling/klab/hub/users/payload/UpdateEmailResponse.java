package org.integratedmodelling.klab.hub.users.payload;

public class UpdateEmailResponse {
	
	private String email;
	private String newEmail;
	
	
	public UpdateEmailResponse() {
		super();
	}
	
	public UpdateEmailResponse(String email, String newEmail) {
		super();
		this.email = email;
		this.newEmail = newEmail;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNewEmail() {
		return newEmail;
	}
	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}
	
	

}
