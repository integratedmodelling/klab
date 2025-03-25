package org.integratedmodelling.klab.hub.security;

import java.util.Arrays;
import java.util.Collections;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.google.common.net.HttpHeaders;

@KeycloakConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {

    @Value("${cors.hosts.allow}")
    String[] corsHostsAllow;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Bean
    public KeycloakConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET, HubRequestMatchers.getAgreements()).permitAll()
                .antMatchers(HttpMethod.POST, HubRequestMatchers.getAuthentication()).permitAll()
                .antMatchers(HttpMethod.POST, HubRequestMatchers.getNode()).permitAll()
                .antMatchers(HttpMethod.GET, HubRequestMatchers.getUi()).permitAll().anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.setAllowedOrigins(Arrays.asList(corsHostsAllow));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.addExposedHeader("Content-disposition");
        config.addExposedHeader(HttpHeaders.LOCATION);
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH", "HEAD"));
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
    * The allows our password encoder to have multiple entries.  Allowing us to upgrade user
    * passwords without needing to do some serious migration effort.  It is possible that
    * we will need to have users set a new password, or force the system to rehash the password
    * when the user logs in.
    * 
    * In the end there are limited possibilities in the options here.  The better way would be
    * to use a proper encoder and not some deprecated methodm but crowd can only handle one type
    * of encrcyption at a time.
    * 
    * 
      * String encodingId = "bcrypt"; Map<String, PasswordEncoder> encoders = new
      * HashMap<>(); encoders.put(encodingId, new BCryptPasswordEncoder());
      * encoders.put("SHA512", new LdapShaPasswordEncoder());
      * DelegatingPasswordEncoder delegatingPasswordEncoder = new
      * DelegatingPasswordEncoder(encodingId, encoders);
      * delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(new
      * LdapShaPasswordEncoder()); return delegatingPasswordEncoder;
    */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new LdapShaPasswordEncoder();
    }
}