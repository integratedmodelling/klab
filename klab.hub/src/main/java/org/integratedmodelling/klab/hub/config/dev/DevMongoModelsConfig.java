package org.integratedmodelling.klab.hub.config.dev;

import java.net.URL;
import org.integratedmodelling.klab.hub.commands.CreateInitialUsers;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;

@Profile("development")
@Configuration
public class DevMongoModelsConfig implements ApplicationListener<ContextRefreshedEvent>{
	
	private MongoGroupRepository groupRepo;
	private UserRepository UserRepository;
	private LdapUserDetailsManager LdapUserDetailsManager;

	@Autowired
	public DevMongoModelsConfig(MongoGroupRepository groupRepo,
			UserRepository userRepository,
			LdapUserDetailsManager ldapUserDetailsManager) {
		super();
		this.groupRepo = groupRepo;
		UserRepository = userRepository;
		LdapUserDetailsManager = ldapUserDetailsManager;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		URL url = DevMongoModelsConfig.class.getClassLoader().getResource("initial-groups.json");
		new CreateIntialGroups(url, groupRepo).execute();
		new CreateInitialUsers(groupRepo, UserRepository, LdapUserDetailsManager).execute();
	}

}
