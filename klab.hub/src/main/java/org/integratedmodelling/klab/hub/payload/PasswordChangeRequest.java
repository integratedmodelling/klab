package org.integratedmodelling.klab.hub.payload;

import javax.validation.constraints.NotEmpty;

public class PasswordChangeRequest {
	public String username;

	@NotEmpty
	public String newPassword;
	
	@NotEmpty
	public String confirm;

	public String getNewPassword() {
		return newPassword;
	}
	
	public String getConfirm() {
		return confirm;
	}
}
