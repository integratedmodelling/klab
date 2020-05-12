package org.integratedmodelling.klab.hub.tests;

public class PasswordRequest {
	
	private String password;
	private String confirm;

	public PasswordRequest(String password, String confirm) {
		this.password = password;
		this.confirm = confirm;
	}

	public String getPassword() {
		return password;
	}

	public String getConfirm() {
		return confirm;
	}
	
	

}
