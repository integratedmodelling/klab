package org.integratedmodelling.klab.hub.users.adapters;

import org.integratedmodelling.klab.hub.users.dto.DeletedUser;
import org.integratedmodelling.klab.hub.users.dto.User;

public class DeletedUserAdapter {
	
	private User user;

	public DeletedUserAdapter(User user) {
		super();
		this.user = user;
	}
	
	public DeletedUser convert() {
		DeletedUser deletedUser = new DeletedUser();
		deletedUser.setUsername(user.getUsername());
		deletedUser.setFirstName(user.getFirstName());
		deletedUser.setLastName(user.getLastName());
		deletedUser.setRegistrationDate(user.getRegistrationDate());
		return deletedUser;
	}
}
