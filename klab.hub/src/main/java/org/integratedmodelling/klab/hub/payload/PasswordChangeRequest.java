package org.integratedmodelling.klab.hub.payload;

import javax.validation.constraints.NotEmpty;

public class PasswordChangeRequest {
	public String username;

	public String oldPassword;

	@NotEmpty
	public String newPassword;

	public String getNewPassword() {
		return newPassword;
	}
}
