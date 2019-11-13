package org.integratedmodelling.klab.hub.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.hub.models.KlabGroup;
import org.integratedmodelling.klab.hub.models.Role;
import org.integratedmodelling.klab.hub.models.User;
import org.integratedmodelling.klab.hub.models.User.AccountStatus;
import org.integratedmodelling.klab.hub.service.KlabGroupService;
import org.integratedmodelling.klab.hub.service.KlabUserDetailsService;
import org.integratedmodelling.klab.hub.service.LdapService;
import org.integratedmodelling.klab.utils.FileCatalog;
import org.joda.time.DateTime;
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
	private KlabGroupService klabGroupService;
	
	@Autowired
	LdapService ldapService;
	
	private static final List<User> initialUsers = new ArrayList<User>(100);
	
	private static final User system = testUser("system", "password", "test.bc3@integratedmodelling.org", "Joe",
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
        result.setRegistrationDate(generateRandomDate());
        return result;
    }
    
    private static final DateTime generateRandomDate() {
    	GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(2015, 2019);
        gc.set(Calendar.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
        int hour = randBetween(0, 23);
        gc.set(Calendar.HOUR_OF_DAY, hour);
        int minute = randBetween(0,60);
        gc.set(Calendar.MINUTE, minute);
        return new DateTime(gc);
    }
    private static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
    
    static {
    	for (int i = 0; i<100; i++) {
    		User u = testUser("user-"+i, "password", "user-"+i+"@integratedmodelling.org", "Name"+i, "Last"+i, Role.ROLE_USER);
    		int x = (int)(Math.random()*100+1);
    		if (x < 10) {
    			u.addRoles(Role.ROLE_SYSTEM);
    		} else if (x < 40) {
    			u.addRoles(Role.ROLE_ADMINISTRATOR);
    		}
    		String[] groups = {"ARIES", "IM", "ALICE", "OTHER", "ÑAÑA", "KLAB"};
    		for (int j = 0; j<groups.length; j++) {
    			x = (int)(Math.random()*100+1);
        		if (x <= 15) {
        			u.addGroups(groups[j]);
        		}	
    		}
    		x = (int)(Math.random()*100+1);
    		if (x <= 5) {
    			u.setAccountStatus(AccountStatus.pendingActivation);
    		}
    		initialUsers.add(u);
    	}
        system.addGroups("ARIES");
        system.addGroups("IM");
        system.addGroups("ALICE");
        hades.addGroups("ARIES");
        hades.addGroups("IM");
        achilles_activeMissingLdap.addGroups("IM");
        triton_pendingMissingLdap.addGroups("IM");
        triton_pendingMissingLdap.setAccountStatus(AccountStatus.pendingActivation);
        initialUsers.add(system);
        initialUsers.add(hades);
        initialUsers.add(achilles_activeMissingLdap);
        initialUsers.add(triton_pendingMissingLdap);
    }
    
    private List<User> getInitialUsers() {
        return new ArrayList<>(initialUsers);
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
    
    public void createIntialGroups() {
		Map<String, KlabGroup> groups = new HashMap<>();
		groups = FileCatalog.create(DevelopmentConfig.class.getClassLoader().getResource("auth/groups.json"), KlabGroup.class);
		groups.forEach((k,v)->klabGroupService.createGroup(v.getId(),v));
    }

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		createInitialUsers();	
		createIntialGroups();
	}

}
