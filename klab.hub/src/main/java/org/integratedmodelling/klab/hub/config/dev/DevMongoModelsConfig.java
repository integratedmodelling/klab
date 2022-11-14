package org.integratedmodelling.klab.hub.config.dev;

import java.net.URL;
import org.integratedmodelling.klab.hub.commands.CreateInitialUsers;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.repository.MongoLeverRepository;
import org.integratedmodelling.klab.hub.repository.MongoNodeRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;

@Profile("development")
@Configuration
public class DevMongoModelsConfig implements ApplicationListener<ContextRefreshedEvent>{
	
	private MongoGroupRepository groupRepo;
	private UserRepository userRepository;
//	private LdapUserDetailsManager ldapUserDetailsManager;
	private PasswordEncoder passwordEncoder;
	private MongoLeverRepository leverRepo;
	private MongoNodeRepository nodeRepo;

	@Autowired
	public DevMongoModelsConfig(MongoGroupRepository groupRepo,
			UserRepository userRepository,
//			LdapUserDetailsManager ldapUserDetailsManager,
			PasswordEncoder passwordEncoder,
			MongoLeverRepository leverRepo,
			MongoNodeRepository nodeRepo) {
		super();
		this.groupRepo = groupRepo;
		this.userRepository = userRepository;
//		this.ldapUserDetailsManager = ldapUserDetailsManager;
		this.passwordEncoder = passwordEncoder;
		this.leverRepo = leverRepo;
		this.nodeRepo = nodeRepo;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		URL url = DevMongoModelsConfig.class.getClassLoader().getResource("initial-groups.json");
		new CreateIntialGroups(url, groupRepo).execute();
		
		new CreateInitialUsers(
				groupRepo,
				userRepository,
//				ldapUserDetailsManager,
				passwordEncoder
				).execute();
		
		new CreateInitialLevers(leverRepo).execute();
		new CreateIntialNodes(nodeRepo, groupRepo).execute();
	}
    

}
