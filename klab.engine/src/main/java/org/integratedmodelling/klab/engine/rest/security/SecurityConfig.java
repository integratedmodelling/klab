package org.integratedmodelling.klab.engine.rest.security;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    
    PasswordEncoder encoder =
        PasswordEncoderFactories.createDelegatingPasswordEncoder();

    UserDetails user = User
      .withUsername("user")
      .password(encoder.encode("password"))
      .roles("USER")
      .build();
    UserDetails admin = User
      .withUsername("admin")
      .password(encoder.encode("password"))
      .roles("ADMIN")
      .build();
      
    auth.inMemoryAuthentication()
      .withUser(user)
      .withUser(admin);
  }
  
// see https://www.jeejava.com/spring-security-pre-authentication-example/
// uncomment to enable preauthentication after the filter is set up correctly
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//      http.addFilterAfter(siteminderFilter(), RequestHeaderAuthenticationFilter.class).authorizeRequests()
//              .antMatchers("/").permitAll().anyRequest().authenticated().antMatchers("/blogs")
//              .access("hasRole('ADMIN')");
//  }
//
//  @Bean(name = "siteminderFilter")
//  public RequestHeaderAuthenticationFilter siteminderFilter() throws Exception {
//    // TODO use a custom filter!
//      RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter = new RequestHeaderAuthenticationFilter();
//      requestHeaderAuthenticationFilter.setPrincipalRequestHeader("SM_USER");
//      requestHeaderAuthenticationFilter.setAuthenticationManager(authenticationManager());
//      return requestHeaderAuthenticationFilter;
//  }
//
//  @Bean
//  @Override
//  protected AuthenticationManager authenticationManager() throws Exception {
//      final List<AuthenticationProvider> providers = new ArrayList<>(1);
//      providers.add(preauthAuthProvider());
//      return new ProviderManager(providers);
//  }
//
//  @Bean(name = "preAuthProvider")
//  PreAuthenticatedAuthenticationProvider preauthAuthProvider() throws Exception {
//      PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
//      provider.setPreAuthenticatedUserDetailsService(userDetailsServiceWrapper());
//      return provider;
//  }
//
//  @Bean
//  UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> userDetailsServiceWrapper() throws Exception {
//      UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = new UserDetailsByNameServiceWrapper<>();
//      wrapper.setUserDetailsService(customUserDetailsService);
//      return wrapper;
//  }
//  
//  @Autowired
//  private CustomUserDetailsService customUserDetailsService;
  
  

    
}
