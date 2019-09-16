package org.integratedmodelling.klab.hub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.ldap.DefaultLdapUsernameToDnMapper;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;

@Profile("development")
@Configuration
@ComponentScan(basePackages = {"org.integratedmodelling.klab.hub.*"})
@EnableLdapRepositories(basePackages = "org.integratedmodelling.klab.hub.**")
public class LdapConfigDev {
	
    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("localhost");
        contextSource.setUserDn("admin");
        contextSource.setPassword("secret");
        contextSource.setBase("dc=integratedmodelling,dc=org");
        contextSource.afterPropertiesSet();
        return contextSource;
    }
    
    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }
    
    @Bean
    public LdapUserDetailsManager ldapUserDetailsManager() {
    	LdapUserDetailsManager ldapUserDetailsManager = new LdapUserDetailsManager(contextSource());
    	ldapUserDetailsManager.setGroupSearchBase("ou=groups");
    	ldapUserDetailsManager.setUsernameMapper(new DefaultLdapUsernameToDnMapper("ou=imusers", "uid"));
    	return ldapUserDetailsManager;
    }
    
    @Bean
    public LdapAuthoritiesPopulator authoritiesPopulator(){
    	return new DefaultLdapAuthoritiesPopulator(contextSource(),"ou=groups"){{
    		setGroupSearchFilter("(uniqueMember={0})");
    		}
    	};
    }
}
