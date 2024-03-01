package org.integratedmodelling.klab.hub.users.commands;

import org.integratedmodelling.klab.hub.repository.DeletedUserRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.adapters.DeletedUserAdapter;
import org.integratedmodelling.klab.hub.users.dto.DeletedUser;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.hub.users.dto.User.AccountStatus;

public class DeleteUser implements UserCommand{
	
	private User user;
	private UserRepository userRepository;
	private DeletedUserRepository deletedUserRepository;

	
	public DeleteUser(User user, UserRepository userRepository, DeletedUserRepository deletedUserRepository) {
		super();
		this.user = user;
		this.userRepository = userRepository;
		this.deletedUserRepository = deletedUserRepository;
	}


	@Override
	public User execute() {
		if (user.getAccountStatus() != AccountStatus.deleted) {
			userRepository.delete(user);
			user.setAccountStatus(AccountStatus.deleted);
		}
		DeletedUser deletedUser = new DeletedUserAdapter(user).convert();
		deletedUser.setDeletionDate();
		deletedUserRepository.insert(deletedUser);
		return user;
	}

}
