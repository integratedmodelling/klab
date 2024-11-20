package org.integratedmodelling.klab.engine.rest.security; 
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.integratedmodelling.klab.api.auth.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter; 
 
@Configuration
class WebSecurityConfig {
	
	interface AuthoritiesConverter extends Converter<Map<String, Object>, Collection<GrantedAuthority>> {} 
	
	@Profile("engine.remote")
	@EnableWebSecurity 
	@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) 
	class WebSecurityConfigRemote extends WebSecurityConfigurerAdapter{ 
		
	  @Autowired
	  private PreauthenticatedUserDetailsService customUserDetailsService;


//	  @Bean 
//	  AuthoritiesConverter realmRolesAuthoritiesConverter() { 
//	      return claims -> { 
//	          final var realmAccess = Optional.ofNullable((Map<String, Object>) claims.get("realm_access")); 
//	          final var roles = 
//	                  realmAccess.flatMap(map -> Optional.ofNullable((List<String>) map.get("roles"))); 
//	          
//	          roles.ifPresent(role -> role.add(Roles.PUBLIC));
//	          
//	          List<GrantedAuthority> rolesList = roles.map(List::stream).orElse(Stream.empty()).map(SimpleGrantedAuthority::new) 
//	                  .map(GrantedAuthority.class::cast).toList();
//	          
//	          return rolesList;
//	      }; 
//	  } 
//	  
//	  @Bean
//	  JwtAuthenticationConverter authenticationConverter(
//	          Converter<Map<String, Object>, Collection<GrantedAuthority>> authoritiesConverter) {
//	      JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//	      jwtAuthenticationConverter
//	              .setJwtGrantedAuthoritiesConverter(jwt -> authoritiesConverter.convert(jwt.getClaims()));
//	      return jwtAuthenticationConverter;
//	  }
	   
	  @Override 
	  protected void configure(HttpSecurity http) throws Exception { 
		  
		  http.cors().and().csrf().disable().addFilterAfter(certFilter(), RequestHeaderAuthenticationFilter.class)
		  .authorizeHttpRequests( 
					authorize -> authorize.mvcMatchers("/api/**").authenticated().mvcMatchers("/**").permitAll()) 
					.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt); 
		  
	  }
	  
	  @Bean
		@Override
		protected AuthenticationManager authenticationManager()  {
			final List<AuthenticationProvider> providers = new ArrayList<>(2);
			providers.add(preauthAuthProvider());
			return new ProviderManager(providers);
		}
	  
	    @Bean(name="certFilter")
	  	PreauthenticationFilter certFilter() {
	  		PreauthenticationFilter ret = new PreauthenticationFilter();
			ret.setAuthenticationManager(authenticationManager());
	  		return ret;
	  	}

	  
		@Bean(name = "preAuthProvider")
		PreAuthenticatedAuthenticationProvider preauthAuthProvider()  {
			PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
			provider.setPreAuthenticatedUserDetailsService(userDetailsServiceWrapper());
			return provider;
		}

		@Bean
		UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> userDetailsServiceWrapper()  {
			UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = new UserDetailsByNameServiceWrapper<>();
			wrapper.setUserDetailsService(customUserDetailsService);
			return wrapper;
		}
	     
	}
	
	
	@Profile("engine.local")
	@EnableWebSecurity
	@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
	class WebSecurityConfigLocal extends WebSecurityConfigurerAdapter {
		
	  @Autowired
	  private PreauthenticatedUserDetailsService customUserDetailsService;

	  @Autowired
	  private EngineDirectoryAuthenticationProvider authProvider;
	  
	  @Override
	  protected void configure(HttpSecurity http) throws Exception {
		  http
		  // disable automatic session creation to avoid use of cookie session
		  // and the consequent authentication failures in web ui
		  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		  .and()
		  .addFilterBefore(certFilter(), RequestHeaderAuthenticationFilter.class)
//		  .authorizeRequests().anyRequest().hasAnyRole("ADMIN")
//	      .and()
	      .authorizeRequests().antMatchers("/login**").permitAll()
	      .and()
	      .formLogin().permitAll()
	      .and()
	      .logout().permitAll() 
	      .and()
	      .csrf().disable()
	      .exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {

			@Override
			public void commence(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException authException) throws IOException, ServletException {
				// Pre-authenticated entry point called. Rejecting access
		        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
	    	  
	      })
	      .and()
	      .headers().frameOptions().disable();
	  }

		@Bean
		@Override
		protected AuthenticationManager authenticationManager()  {
			final List<AuthenticationProvider> providers = new ArrayList<>(2);
			providers.add(preauthAuthProvider());
			providers.add(authProvider);
			return new ProviderManager(providers);
		}

	  	@Bean(name="certFilter")
	  	PreauthenticationFilter certFilter() {
	  		PreauthenticationFilter ret = new PreauthenticationFilter();
			ret.setAuthenticationManager(authenticationManager());
	  		return ret;
	  	}
	  
		@Bean(name = "preAuthProvider")
		PreAuthenticatedAuthenticationProvider preauthAuthProvider()  {
			PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
			provider.setPreAuthenticatedUserDetailsService(userDetailsServiceWrapper());
			return provider;
		}

		@Bean
		UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> userDetailsServiceWrapper()  {
			UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = new UserDetailsByNameServiceWrapper<>();
			wrapper.setUserDetailsService(customUserDetailsService);
			return wrapper;
		}

	}
	
}

