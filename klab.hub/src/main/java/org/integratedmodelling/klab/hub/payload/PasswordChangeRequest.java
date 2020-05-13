package org.integratedmodelling.klab.hub.payload;

public class PasswordChangeRequest {
	
	public String newPassword;
	
	public String confirm;

	public String getNewPassword() {
		return newPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public String getConfirm() {
		return confirm;
	}
}
