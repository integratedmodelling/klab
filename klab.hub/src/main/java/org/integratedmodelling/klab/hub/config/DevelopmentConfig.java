package org.integratedmodelling.klab.hub.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.integratedmodelling.klab.hub.models.KlabGroup;
import org.integratedmodelling.klab.hub.models.Role;
import org.integratedmodelling.klab.hub.models.User;
import org.integratedmodelling.klab.hub.models.User.AccountStatus;
import org.integratedmodelling.klab.rest.ObservableReference;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("development")
@Configuration
public class DevelopmentConfig {
	
	public static final User system = testUser("system", "password", "admin@integratedmodelling.org", "Joe",
            "Robot", Role.ROLE_USER, Role.ROLE_ADMINISTRATOR, Role.ROLE_SYSTEM);
	
    public static final User hades = testUser("hades", "password", "hades@integratedmodelling.org", "Hades",
            "of Greece", Role.ROLE_USER, Role.ROLE_ADMINISTRATOR);
    
    public static final User hercules = testUser("hercules", "password", "hercules@integratedmodelling.org",
            "Hercules", "of Rome", Role.ROLE_USER);
    
    public static final User achilles_activeMissingLdap = testUser("achilles", "password",
            "achilles@integratedmodelling.org", "Achilles", "of Greece", Role.ROLE_USER);
    
    public static final User triton_pendingMissingLdap = testUser("triton", "password",
            "triton@integratedmodelling.org", "Triton", "of Greece", Role.ROLE_USER);
    
    public static final KlabGroup im = testGroup("im", "worldview", "No_Key", true);
    
    public static final KlabGroup aries = testGroup("aries", "ARIESTEAM", "No_Key", false);
    
    
	
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
    
    private static KlabGroup testGroup(String groupname, String description, String sshKey, Boolean worldview) {
    	KlabGroup group = new KlabGroup();
    	group.setId(groupname);
    	group.setDescription(description);
    	group.setSshKey(sshKey);
    	List<String> list = Arrays.asList("foo", "bar");
    	group.setProjectUrls(list);
    	group.setWorldview(worldview);
    	return group;
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
    
    public List<User> getInitialUsers() {
        return new ArrayList<>(Arrays.asList(hercules, hades, system));
    }

}
