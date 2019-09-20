package org.integratedmodelling.klab.hub.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.hub.models.Role;
import org.integratedmodelling.klab.hub.models.User;
import org.integratedmodelling.klab.hub.models.User.AccountStatus;
import org.integratedmodelling.klab.hub.service.KlabUserDetailsService;
import org.integratedmodelling.klab.hub.service.LdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;

@Profile("development")
@Configuration
public class DevelopmentConfig implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private KlabUserDetailsService klabUserDetailsService;
	
	@Autowired
	LdapService ldapService;
	
	private static final User system = testUser("system", "password", "admin@integratedmodelling.org", "Joe",
            "Robot", Role.ROLE_USER, Role.ROLE_ADMINISTRATOR, Role.ROLE_SYSTEM);
	
	private static final User hades = testUser("hades", "password", "hades@integratedmodelling.org", "Hades",
            "of Greece", Role.ROLE_USER, Role.ROLE_ADMINISTRATOR);
    
	private static final User hercules = testUser("hercules", "password", "hercules@integratedmodelling.org",
            "Hercules", "of Rome", Role.ROLE_USER);
    
	private static final User achilles_activeMissingLdap = testUser("achilles", "password",
            "achilles@integratedmodelling.org", "Achilles", "of Greece", Role.ROLE_USER);
    
	private static final User triton_pendingMissingLdap = testUser("triton", "password",
            "triton@integratedmodelling.org", "Triton", "of Greece", Role.ROLE_USER);

    private static User testUser(String username, String password, String email, String firstName, String lastName,
            Role... roles) {
        User result = new User();
        result.setUsername(username);
        result.setPasswordHash(password);
        result.setEmail(email);
        result.setRoles(Arrays.asList(roles));
        result.setAccountStatus(AccountStatus.active);
        result.setFirstName(firstName);
        result.setLastName(lastName);
        result.setRegistrationDate();
        return result;
    }
    
    static {
        system.addGroups("aries");
        system.addGroups("im");
        hades.addGroups("aries");
        hades.addGroups("im");
        achilles_activeMissingLdap.addGroups("im");
        triton_pendingMissingLdap.addGroups("im");
        triton_pendingMissingLdap.setAccountStatus(AccountStatus.pendingActivation);
    }
    
    private List<User> getInitialUsers() {
        return new ArrayList<>(Arrays.asList(hercules, hades, system));
    }
    
    public void createInitialUsers() {
    	List<User> users = getInitialUsers();
    	for(User user : users) {
    		try {
    			klabUserDetailsService.createMongoUser(user, AccountStatus.active);
    			klabUserDetailsService.createLdapUser(user);
    		} catch (Exception e) {
    			Logging.INSTANCE.error(e);
    		}
    	}
    }

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		createInitialUsers();	
	}

}
