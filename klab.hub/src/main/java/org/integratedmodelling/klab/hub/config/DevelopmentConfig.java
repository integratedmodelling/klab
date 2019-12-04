package org.integratedmodelling.klab.hub.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.hub.manager.KlabUserManager;
import org.integratedmodelling.klab.hub.models.GroupEntry;
import org.integratedmodelling.klab.hub.models.KlabGroup;
import org.integratedmodelling.klab.hub.models.Role;
import org.integratedmodelling.klab.hub.models.User;
import org.integratedmodelling.klab.hub.models.User.AccountStatus;
import org.integratedmodelling.klab.hub.service.KlabGroupService;
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
	private KlabUserManager KlabUserManager;
	
	@Autowired
	private KlabGroupService klabGroupService;
	
	@Autowired
	KlabUserManager klabUserManager;
	
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
        result.setRegistrationDate(generateRandomDate(null));
        result.setLastLogin(generateRandomDate(result.getRegistrationDate()));
        return result;
    }
    
    private static final DateTime generateRandomDate(DateTime from) {
    	GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(from != null ? from.getYear() : 2015, 2019);
        gc.set(Calendar.YEAR, year);
        int dayOfYear = randBetween(from != null ? from.getDayOfYear() : 1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
        int hour = randBetween(from != null ? from.getHourOfDay() : 0, 23);
        gc.set(Calendar.HOUR_OF_DAY, hour);
        int minute = randBetween(from != null ? from.getMinuteOfHour() : 0, 60);
        gc.set(Calendar.MINUTE, minute);
        return new DateTime(gc);
    }
    private static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
    
    
    private List<User> getInitialUsers() {
		GroupEntry im = new GroupEntry(klabGroupService.getGroup("IM").get());
		GroupEntry aries = new GroupEntry(klabGroupService.getGroup("ARIES").get());
		GroupEntry alice = new GroupEntry(klabGroupService.getGroup("ALICE").get());
    	for (int i = 0; i<100; i++) {
    		User u = testUser("User-"+i, "password", "user-"+i+"@integratedmodelling.org", "Name"+i, "Last"+i, Role.ROLE_USER);
    		int x = (int)(Math.random()*100+1);
    		if (x < 2)
    			u.addRoles(Role.ROLE_SYSTEM); // less than 2% are system
    		if (x < 5)
    			u.addRoles(Role.ROLE_ADMINISTRATOR); // less than 5% are administrator 
    		if (x < 7)
    			u.addRoles(Role.ROLE_DATA_MANAGER); // less than 7% are data manager
    		x = (int)(Math.random()*100+1);
    		if (x <= 80) {
    			u.addGroups(im, aries, alice); // 80% has IM and ARIES. If no IM and ARIES, no groups for now
    			GroupEntry[] groups = {im, alice, aries}; // and other random group assignment 
        		for (int j = 0; j<groups.length; j++) {
        			x = (int)(Math.random()*100+1);
            		if (x <= 33) {
            			u.addGroups(groups[j]);
            		}
        		}
        		x = (int)(Math.random()*100+1);
        		if (x < 22) {
        			for (int j = 0; j<=x; j++) {
        				//u.addGroups("GROUP "+j); // multiple groups
        				//add back something in the future
        			}
        		}
    		}
    		x = (int)(Math.random()*100+1);
    		if (x <= 5) { // 5% pending activation
    			u.setAccountStatus(AccountStatus.pendingActivation);
    			u.setLastLogin(null);
    			u.setRegistrationDate(null);
    		}
    		if (x >= 6 && x <= 8) { // not pending but no registration date (legacy problem)
    			u.setRegistrationDate(null);
    		}
    		if (u.getAccountStatus() != AccountStatus.pendingActivation) {
	    		x = (int)(Math.random()*100+1);
	    		if (x < 5) { // less than 5% with no last login 
	    			u.setLastLogin(null);
	    		}
    		}
    		initialUsers.add(u);
    	}
        system.addGroups(aries);
        system.addGroups(im);
        system.addGroups(alice);
        hades.addGroups(aries);
        hades.addGroups(im);
        achilles_activeMissingLdap.addGroups(im);
        triton_pendingMissingLdap.addGroups(aries);
        triton_pendingMissingLdap.setAccountStatus(AccountStatus.pendingActivation);
        initialUsers.add(system);
        initialUsers.add(hades);
        initialUsers.add(achilles_activeMissingLdap);
        initialUsers.add(triton_pendingMissingLdap);
        return initialUsers;
    }
    
    public void createInitialUsers() {
    	List<User> users = getInitialUsers();
    	for(User user : users) {
    		try {
    			KlabUserManager.createKlabUser(user, AccountStatus.active);
    			if (user.getLastLogin() != null) {
    				int x = (int)(Math.random()*100+1);
    				if (x>=5) // less than 5% not connected
    					user.setLastEngineConnection(generateRandomDate(user.getLastLogin()));
    				}
    			KlabUserManager.updateKlabUser(user);
    		} catch (Exception e) {
    			Logging.INSTANCE.error(e);
    		}
    	}
    }
    
    public void createIntialGroups() {
		Map<String, KlabGroup> groups = new HashMap<>();
		groups = FileCatalog.create(DevelopmentConfig.class.getClassLoader().getResource("auth/groups.json"), KlabGroup.class);
		groups.forEach((k,v)->klabGroupService.createGroup(v.getGroupName(),v));
    }

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		createIntialGroups();
		createInitialUsers();
	}

}
