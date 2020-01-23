package org.integratedmodelling.klab.hub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.ldap.DefaultLdapUsernameToDnMapper;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;

@Profile("production")
@Configuration
@ComponentScan(basePackages = {"org.integratedmodelling.klab.hub.*"})
//@EnableLdapRepositories(basePackages = "org.integratedmodelling.klab.hub.repository.UserRepository")
public class LdapConfig {
    @Value("${ldap.url}")
    private String LDAP_URL;

    @Value("${ldap.principal}")
    private String LDAP_PRINCIPAL;

    @Value("${ldap.password}")
    private String LDAP_PASSWORD;

    @Value("${ldap.partitionSuffix}")
    private String LDAP_PARTITION_SUFFIX;
	
    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(LDAP_URL);
        contextSource.setUserDn(LDAP_PRINCIPAL);
        contextSource.setPassword(LDAP_PASSWORD);
        contextSource.setBase(LDAP_PARTITION_SUFFIX);
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
