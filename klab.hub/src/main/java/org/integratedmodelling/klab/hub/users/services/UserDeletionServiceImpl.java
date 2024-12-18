package org.integratedmodelling.klab.hub.users.services;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.agreements.services.AgreementMongoTemplate;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.tasks.services.TaskMongoTemplate;
import org.integratedmodelling.klab.hub.users.commands.CreateUser;
import org.integratedmodelling.klab.hub.users.commands.DeleteUser;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.hub.users.dto.User.AccountStatus;
import org.springframework.stereotype.Service;

@Service
public class UserDeletionServiceImpl implements UserDeletionService{
	
	private UserRepository userRepository;
	private TaskMongoTemplate taskMongoTemplate;
	private AgreementMongoTemplate agreementMongoTemplate;
//	private LdapUserDetailsManager ldapUserDetailsManager;
	private TokenRepository tokenRepository;
	
    public UserDeletionServiceImpl(UserRepository userRepository, /*LdapUserDetailsManager ldapUserDetailsManager,*/
            TaskMongoTemplate taskMongoTemplate, AgreementMongoTemplate agreementMongoTemplate, TokenRepository tokenRepository) {
		super();
		this.userRepository = userRepository;
//		this.ldapUserDetailsManager = ldapUserDetailsManager;
		this.taskMongoTemplate = taskMongoTemplate;
		this.agreementMongoTemplate = agreementMongoTemplate;
		this.tokenRepository = tokenRepository;
	}
	
		@Override
	public void deleteUser(String username) {
		User user = userRepository.findByNameIgnoreCase(username)
				.orElseThrow(() -> new BadRequestException("User is not present or already deleted"));
			
		try {
			/* Delete mongo user */
			new DeleteUser(user, userRepository).execute();
			
			/* Update to null agreements */
			taskMongoTemplate.updateTaskUserToNull(user.getUsername());
			
			/* Delete agreements and tokens*/
            agreementMongoTemplate.removeAgreementsById(
                    user.getAgreements().stream().map(agreement -> agreement.getAgreement().getId()).toList());
			
			tokenRepository.deleteAllByUsername(user.getUsername());
			
		} catch (Exception e) {
			new CreateUser(userRepository, user, AccountStatus.deleted);
			throw new KlabException("Error deleting mongo user", e);
		}
		
//        /* Delete ldap user */
//        try {
//            deleteUserLdap(user);
//        } catch (Exception e) {
//            new CreateUser(userRepository, user, AccountStatus.deleted);
//            throw new KlabException("Error deleting ldap user", e);
//        }
	}

//    @Override
//    public void deleteUserLdap(User user) {
//        new DeleteLdapUser(user, ldapUserDetailsManager).execute();
//    }

//    @Override
//    public void deleteUserLdap(String username) {
//        User user = userRepository.findByNameIgnoreCase(username)
//                .orElseThrow(() -> new BadRequestException("User is not present or already deleted"));
//
//        try {
//            deleteUserLdap(user);
//        } catch (Exception e) {
//            new CreateUser(userRepository, user, AccountStatus.deleted);
//            throw new KlabException("Error deleting ldap user", e);
//        }
//
//    }
}
