package org.integratedmodelling.klab.hub.security;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String AUTHENTICATION_TOKEN_HEADER_NAME = "Authentication";

	/**
	 * TODO incorporate this where? this allows us to assign our own HTTP response
	 * status code, by using an exception class which is annotated with it directly.
	 * (otherwise we would rely on Spring's container logic to assign a 'redirect'
	 * or other default status.)
	 */
	AuthenticationFailureHandler authenticationFailureHandler = new AuthenticationFailureHandler() {

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException e) throws IOException, ServletException {
			throw new KlabAuthorizationException("Authentication failed.");
		}
	};
	
	/**
	 * this is necessary so that HubAuthenticationManager injection points can be
	 * populated.
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
		http.csrf().disable();
	}

}