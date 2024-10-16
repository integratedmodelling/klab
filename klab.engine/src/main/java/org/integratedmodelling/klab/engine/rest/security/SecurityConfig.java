package org.integratedmodelling.klab.engine.rest.security;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfig {
//	
//  @Autowired
//  private PreauthenticatedUserDetailsService customUserDetailsService;

//  @Autowired
//  private EngineDirectoryAuthenticationProvider authProvider;
  
  interface AuthoritiesConverter extends Converter<Map<String, Object>, Collection<GrantedAuthority>> {}

  @Bean
  AuthoritiesConverter realmRolesAuthoritiesConverter() {
      return claims -> {
          final var realmAccess = Optional.ofNullable((Map<String, Object>) claims.get("realm_access"));
          final var roles =
                  realmAccess.flatMap(map -> Optional.ofNullable((List<String>) map.get("roles")));
          return roles.map(List::stream).orElse(Stream.empty()).map(SimpleGrantedAuthority::new)
                  .map(GrantedAuthority.class::cast).toList();
      };
  }
  
  @Bean
  JwtAuthenticationConverter authenticationConverter(
          Converter<Map<String, Object>, Collection<GrantedAuthority>> authoritiesConverter) {
      JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
      jwtAuthenticationConverter
              .setJwtGrantedAuthoritiesConverter(jwt -> authoritiesConverter.convert(jwt.getClaims()));
      return jwtAuthenticationConverter;
  }

  @Bean
  SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http,
          Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter) throws Exception {
      http.oauth2ResourceServer(resourceServer -> {
          resourceServer.jwt(jwtDecoder -> {
              jwtDecoder.jwtAuthenticationConverter(jwtAuthenticationConverter);
          });
      });

      http.sessionManagement(sessions -> {
          sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      }).csrf(csrf -> {
          csrf.disable();
      });


      http.cors().and().csrf().disable().authorizeRequests()
//      
      .antMatchers(HttpMethod.GET, "/").permitAll()
      .antMatchers(HttpMethod.GET, "/css/**").permitAll()
      .antMatchers(HttpMethod.GET, "/fonts/**").permitAll()
      .antMatchers(HttpMethod.GET, "/images/**").permitAll()
      .antMatchers(HttpMethod.GET, "/js/**").permitAll()
      .antMatchers(HttpMethod.GET, "/icons/**").permitAll()      
      .antMatchers(HttpMethod.GET, EngineRequestMatchers.getEngine()).permitAll()
      .antMatchers(HttpMethod.GET, EngineRequestMatchers.getCapabilities()).permitAll()
      .antMatchers(HttpMethod.POST, EngineRequestMatchers.getTemplate()).permitAll()
      .antMatchers(HttpMethod.GET, EngineRequestMatchers.getSchema()).permitAll()
      .antMatchers(HttpMethod.GET, EngineRequestMatchers.getPing()).permitAll()
      .antMatchers(HttpMethod.HEAD, EngineRequestMatchers.getPing()).permitAll()
      .antMatchers(HttpMethod.GET, EngineRequestMatchers.getUi()).permitAll()
      .anyRequest().authenticated();
      
//      http.cors().disable().authorizeHttpRequests(requests -> {    	  	
//    	  	requests.antMatchers(HttpMethod.GET, EngineRequestMatchers.getEngine()).permitAll();
//    	  	requests.antMatchers(HttpMethod.GET, EngineRequestMatchers.getCapabilities()).permitAll();
//    	  	requests.antMatchers(HttpMethod.POST, EngineRequestMatchers.getTemplate()).permitAll();
//    	  	requests.antMatchers(HttpMethod.GET, EngineRequestMatchers.getSchema()).permitAll();
//    	  	requests.antMatchers(HttpMethod.GET, EngineRequestMatchers.getPing()).permitAll();
//    	  	requests.antMatchers(HttpMethod.HEAD, EngineRequestMatchers.getPing()).permitAll();
//    	    requests.anyRequest().authenticated();
//    	  });
//      
      

      return http.build();
  }
  
//  @Bean
//  CorsConfigurationSource corsConfigurationSource() {
//      final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//      final CorsConfiguration config = new CorsConfiguration();
//      config.setAllowCredentials(false);
//      config.setAllowedOrigins(Arrays.asList("*"));
//      config.setAllowedHeaders(Arrays.asList("*"));
//      config.addExposedHeader("Content-disposition");
//      config.addExposedHeader(HttpHeaders.LOCATION);
//      config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH", "HEAD"));
//      source.registerCorsConfiguration("/**", config);
//      return source;
//  }
  
  
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//	  http
//	  // disable automatic session creation to avoid use of cookie session
//	  // and the consequent authentication failures in web ui
//	  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//	  .and()
//	  .addFilterBefore(certFilter(), RequestHeaderAuthenticationFilter.class)
////	  .authorizeRequests().anyRequest().hasAnyRole("ADMIN")
////      .and()
//      .authorizeRequests().antMatchers("/login**").permitAll()
//      .and()
//      .formLogin().permitAll()
//      .and()
//      .logout().permitAll() 
//      .and()
//      .csrf().disable()
//      .exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
//
//		@Override
//		public void commence(HttpServletRequest request, HttpServletResponse response,
//				AuthenticationException authException) throws IOException, ServletException {
//			// Pre-authenticated entry point called. Rejecting access
//	        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//		}
//    	  
//      })
//      .and()
//      .headers().frameOptions().disable();
//  }

//	@Bean
//	@Override
//	protected AuthenticationManager authenticationManager()  {
//		final List<AuthenticationProvider> providers = new ArrayList<>(2);
//		providers.add(preauthAuthProvider());
//		providers.add(authProvider);
//		return new ProviderManager(providers);
//	}
//
//  	@Bean(name="certFilter")
//  	PreauthenticationFilter certFilter() {
//  		PreauthenticationFilter ret = new PreauthenticationFilter();
//		ret.setAuthenticationManager(authenticationManager());
//  		return ret;
//  	}
//  
//	@Bean(name = "preAuthProvider")
//	PreAuthenticatedAuthenticationProvider preauthAuthProvider()  {
//		PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
//		provider.setPreAuthenticatedUserDetailsService(userDetailsServiceWrapper());
//		return provider;
//	}
//
//	@Bean
//	UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> userDetailsServiceWrapper()  {
//		UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = new UserDetailsByNameServiceWrapper<>();
//		wrapper.setUserDetailsService(customUserDetailsService);
//		return wrapper;
//	}

    
}
