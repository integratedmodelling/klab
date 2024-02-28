package org.integratedmodelling.klab.hub.users.services;

import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.integratedmodelling.klab.hub.api.ProfileResource;
import org.integratedmodelling.klab.hub.api.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public interface UserProfileService {
	
	abstract ProfileResource updateUserByProfile(ProfileResource profile);
	abstract ProfileResource getUserProfile(String username);
	abstract ProfileResource getUserProfileByEmail(String emai);
	abstract ProfileResource getUserSafeProfile(User user);
	abstract ProfileResource getRawUserProfile(String username);
	abstract ProfileResource getCurrentUserProfile(boolean remote);
	abstract Set<ProfileResource> getAllUserProfiles();
    abstract Page<User> getPage(Query query, Pageable pageable);
    abstract List<User> getQuery(Query query);
    /**
	 * Check if email is changed. 
	 * If email changes send and email to the user to verified this action
	 * @param user
	 * @throws MessagingException 
	 */
	abstract ProfileResource createNewEmailRequest(String id, String requestNewEmail) throws MessagingException;
	/**
	 * Update the email	 of user in mongo and ldap 
	 * @param id
	 * @param email
	 * @return
	 */
	abstract ProfileResource updateUserEmail(String id, String email);

}
