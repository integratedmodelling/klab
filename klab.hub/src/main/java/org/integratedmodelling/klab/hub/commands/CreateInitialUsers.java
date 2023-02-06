package org.integratedmodelling.klab.hub.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.api.GroupEntry;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.joda.time.DateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;

public class CreateInitialUsers {
	
	private MongoGroupRepository groupRepository;
	private UserRepository userRepository;
	private	LdapUserDetailsManager ldapUserDetailsManager;
	private PasswordEncoder passwordEncoder;
	
	public CreateInitialUsers(MongoGroupRepository groupRepository,
			UserRepository userRepository,
			LdapUserDetailsManager ldapUserDetailsManager,
			PasswordEncoder passwordEncoder) {
		this.groupRepository = groupRepository;
		this.userRepository = userRepository;
		this.ldapUserDetailsManager = ldapUserDetailsManager;
		this.passwordEncoder = passwordEncoder;
	}

	public void execute() {
		createInitialUsers();
	}
	
	private static final List<User> initialUsers = new ArrayList<User>(100);
	
	private static final User system = testUser("system", "password", "test.bc3@integratedmodelling.org", "Joe",
            "Robot", Role.ROLE_USER, Role.ROLE_ADMINISTRATOR, Role.ROLE_SYSTEM);
	
	private static final User hades = testUser("hades", "password", "hades@integratedmodelling.org", "Hades",
            "of Greece", Role.ROLE_USER, Role.ROLE_ADMINISTRATOR);
    
	private static final User developerS = testUser("srwohl", "password", "steven.wohl@bc3research.org",
            "Hercules", "of Rome", Role.ROLE_USER);
	
	private static final User developerE = testUser("enrico", "password", "enrico.girotto@bc3research.org",
            "Enrico", "of Venice", Role.ROLE_USER);
    
	private static final User achilles_activeMissingLdap = testUser("achilles", "password",
            "achilles@integratedmodelling.org", "Achilles", "of Greece", Role.ROLE_USER);
    
	private static final User triton_pendingMissingLdap = testUser("triton", "password",
            "triton@integratedmodelling.org", "Triton", "of Greece", Role.ROLE_USER);
	
    private static User testUser(String username, String password, String email, String firstName, String lastName,
            Role... roles) {
        User result = new User();
        result.setUsername(username);
        result.setEmail(email);
        result.setRoles(Arrays.asList(roles));
        result.setAccountStatus(AccountStatus.active);
        result.setFirstName(firstName);
        result.setLastName(lastName);
        result.setRegistrationDate(generateRandomDate(null));
        result.setLastLogin(generateRandomDate(result.getRegistrationDate()));
        result.setPasswordHash(password);
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
		GroupEntry im = new GroupEntry(new GetMongoGroupByName("IM", groupRepository).execute());
		GroupEntry aries = new GroupEntry(new GetMongoGroupByName("ARIES", groupRepository).execute(), DateTime.now().minusDays(20));
		GroupEntry alice = new GroupEntry(new GetMongoGroupByName("ALICE", groupRepository).execute());
		GroupEntry seea = new GroupEntry(new GetMongoGroupByName("SEEA", groupRepository).execute(), DateTime.now().plusDays(10));
		GroupEntry leticia = new GroupEntry(new GetMongoGroupByName("LETICIA", groupRepository).execute(), DateTime.now().plusDays(10));
		Set<GroupEntry> entries = new HashSet<GroupEntry>();
		entries.add(im);
		entries.add(aries);
		entries.add(alice);
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
    			u.addGroupEntries(im); // 80% has IM and ARIES. If no IM and ARIES, no groups for now
    			u.addGroupEntries(aries);
        		x = (int)(Math.random()*100+1);
        		if (x < 22) {
        			for (int j = 0; j<=x; j++) {
        				u.addGroupEntries(alice); // multiple groups
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
    		x = (int)(Math.random()*100+1);
    		if (x<=20) {
    			u.setSendUpdates(false);
    		}
    		initialUsers.add(u);
    	}
        system.addGroupEntries(aries);
        system.addGroupEntries(im);
        system.addGroupEntries(alice);
        system.addGroupEntries(seea);
        hades.addGroupEntries(aries);
        hades.addGroupEntries(im);
        developerS.addGroupEntries(aries);
        developerS.addGroupEntries(im);
        developerE.addGroupEntries(aries);
        developerE.addGroupEntries(im);
        developerE.addGroupEntries(seea);
        developerE.addGroupEntries(leticia);
        achilles_activeMissingLdap.addGroupEntries(im);
        triton_pendingMissingLdap.addGroupEntries(aries);
        triton_pendingMissingLdap.setAccountStatus(AccountStatus.pendingActivation);
        initialUsers.add(system);
        initialUsers.add(hades);
        initialUsers.add(developerS);
        initialUsers.add(developerE);
        initialUsers.add(achilles_activeMissingLdap);
        initialUsers.add(triton_pendingMissingLdap);
        return initialUsers;
    }
    
    private void createInitialUsers() {
    	List<User> users = getInitialUsers();
    	for(User user : users) {
    		try {
    			//This is our legacy password encoding
    			user = new SetUserPasswordHash(user, user.getPasswordHash(),this.passwordEncoder).execute();
    			User newUser = new CreateUserWithRolesAndStatus(user, userRepository, ldapUserDetailsManager).execute();
    			if (newUser.getLastLogin() != null) {
    				int x = (int)(Math.random()*100+1);
    				if (x>=5) // less than 5% not connected
    					newUser.setLastConnection(generateRandomDate(newUser.getLastLogin()));
    			}
    			new UpdateUser(user, userRepository).execute();
    		} catch (Exception e) {
    			Logging.INSTANCE.error(e);
    		}
    	}
    }   

}
