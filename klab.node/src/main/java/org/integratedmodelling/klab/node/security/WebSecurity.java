package org.integratedmodelling.klab.node.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	JWTAuthorizationFilter jwtAuthorizationFilter;
	
	   @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
	        http.csrf().disable();
	        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
	    }
}
