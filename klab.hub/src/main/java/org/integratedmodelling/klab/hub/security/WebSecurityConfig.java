package org.integratedmodelling.klab.hub.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.config.BeanIds;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.hub.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import org.integratedmodelling.klab.hub.security.oauth2.OAuth2AuthenticationFailureHandler;
import org.integratedmodelling.klab.hub.security.oauth2.OAuth2AuthenticationSuccessHandler;
import org.integratedmodelling.klab.hub.service.OAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.google.common.collect.ImmutableList;
import com.google.common.net.HttpHeaders;

@SuppressWarnings("deprecation")
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailSerice;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;
	
    @Autowired
    private OAuth2UserService oAuth2UserService;
    
    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    
    @Autowired
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Bean
	public TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter();
	}

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
	
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

	/**
	 * this is necessary so that HubAuthenticationManager injection points can be
	 * populated.
	 */
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailSerice).passwordEncoder(passwordEncoder());
	}

	
	/**
	 * The allows our password encoder to have multiple entries.  Allowing us to upgrade user
	 * passwords without needing to do some serious migration effort.  It is possible that
	 * we will need to have users set a new password, or force the system to rehash the password
	 * when the user logs in.
	 */	
	@Bean
	public PasswordEncoder passwordEncoder() {
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put("SHA512", new  LdapShaPasswordEncoder());
        DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder(encodingId, encoders);
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(new LdapShaPasswordEncoder());
        return delegatingPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http
		.cors()
			.and()
		.csrf()
			.disable()
			.exceptionHandling()
			.authenticationEntryPoint(unauthorizedHandler)
			.and()
		.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
		.authorizeRequests()
			.regexMatchers("/api/users/.*?(activate|lostPassword|setPassword|requestNewPassword|groups).*")
			.permitAll()
			.antMatchers(HttpMethod.POST, "/api/users")
			.permitAll()
			.antMatchers(HttpMethod.POST, "/api/v2/users")
			.permitAll()
			.antMatchers(HttpMethod.POST, "/api/v2/nodes/auth-cert")
			.permitAll()
			.antMatchers(HttpMethod.POST, "/api/v2/engines/auth-cert")
			.permitAll()
			.regexMatchers("/api/v2/users/.*?(activate|certificate|groups|lostPassword|requestNewPassword|setPassword|verify).*")
			.permitAll()
			.regexMatchers(HttpMethod.POST, API.HUB.AUTHENTICATE_USER)
			.permitAll()
			.antMatchers("/api/auth-cert/engine", "/api/auth-cert/node")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
		.oauth2Login()
			.authorizationEndpoint()
			.baseUri("/oauth2/authorize")
			.authorizationRequestRepository(cookieAuthorizationRequestRepository())
			.and()
		.redirectionEndpoint()
			.baseUri("/oauth2/callback/*")
			.and()
		.userInfoEndpoint()
			.userService(oAuth2UserService)
			.and()
		.successHandler(oAuth2AuthenticationSuccessHandler)
		.failureHandler(oAuth2AuthenticationFailureHandler);

		http.cors().and().csrf().disable().antMatcher("/api/**").addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	/*
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.setAllowedOrigins(
				ImmutableList.of(
						"https://integratedmodelling.org",
						"http://localhost:8080",
						"http://192.168.0.104:8080",
						"https://localhost:8284",
						"http://localhost:8284"));
		configuration.setAllowedHeaders(Collections.singletonList("*"));
		configuration.addExposedHeader("Content-disposition");
		configuration.addExposedHeader(HttpHeaders.LOCATION);
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","OPTIONS","DELETE","HEAD"));
		configuration.setMaxAge(3600l);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	*/
	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(ImmutableList.of(
				"https://integratedmodelling.org",
				"http://localhost:8080",
				"https://localhost:8080",
				"http://localhost:8284",
				"https://localhost:8284"));
		config.setAllowedHeaders(Collections.singletonList("*"));
		config.addExposedHeader("Content-disposition");
		config.addExposedHeader(HttpHeaders.LOCATION);
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH", "HEAD"));
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}