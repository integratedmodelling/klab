package org.integratedmodelling.klab.engine.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.auth.KlabUser;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.UserAuthenticationRequest;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * This hub service is used to authenticate a user request to login to an engine that
 * they are not the owner.  The authentication is completed via the partner hub url
 * located in the engine certificate.  If successful it should create a session and return
 * a response entity with a relative url for the seesion.
 * 
 * @author steve
 *
 */
@Service
public class HubUserService implements RemoteUserSerice {
	
	@Autowired
	RestTemplate restTemplate;
	
	/*
	 * Generates a response entity a url to the session generated after succesful
	 * authentication.  Othe
	 */
	@Override
	public ResponseEntity<Object> login(UserAuthenticationRequest login) {
		
		ResponseEntity<JSONObject> result = hubLogin(login);
		
        if (result.getStatusCode().is2xxSuccessful()) {
    		
        	if(activeSession()) {
        		return getUserActiveSession();
        	}
			
        	HubUserProfile profile = getProfile(result);
        	
			List<String> roles = profile.getRoles();
			List<GrantedAuthority> authorities = getAuthorities(roles);
			List<Group> groups = new ArrayList<>();
			
			profile.getGroupEntries().forEach(grp -> {
				Group group = new Group();
				group.setId(grp.getGroup().getName());
				groups.add(group);
			});
	        
			String token = NameGenerator.newName();
			KlabUser user = new KlabUser(login.getUsername(), token, authorities);
			user.getGroups().addAll(groups);
			
			String sessionId = getSession(user);
			String redirectUrl = "/modeler/ui/viewer?session=" + sessionId;
			
			JSONObject data = new JSONObject();
			data.put("redirect", redirectUrl);
			data.put("Authorization", token);
			
		    return ResponseEntity.status(HttpStatus.ACCEPTED)
		            .location(URI.create(redirectUrl))
		            .body(data);
		} else {
			throw new KlabAuthorizationException("Failed to login user: " + login.getUsername());			
		}
		
	}
	
	
	private HubUserProfile getProfile(ResponseEntity<JSONObject> result) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JodaModule());
		HubUserProfile profile = mapper.convertValue(result.getBody().get("Profile"), HubUserProfile.class);
		return profile;
	}


	private String getSession(KlabUser user) {
		Authentication.INSTANCE.registerIdentity(user);
		Engine engine = Authentication.INSTANCE.getAuthenticatedIdentity(Engine.class);
		//engine.createSession(user);
		return engine.createSession().getId();
	}


	private List<GrantedAuthority> getAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>(); 
        for (String role : roles) {
        	
        	if(role.equals("ROLE_ADMINSTRATOR")) {
        		authorities.add(new SimpleGrantedAuthority(Roles.ADMIN));
        	}
        	
        	if(role.equals("ROLE_USER")) {
        		authorities.add(new SimpleGrantedAuthority(Roles.ENGINE_USER));
        	}
        	
        }
        
        if(authorities.isEmpty()) {
        	throw new KlabAuthorizationException("User does not have the authority to create session.");
        }
        
        return authorities;
	}



	private boolean activeSession() {
		Authentication.INSTANCE.getSessions();
		// TODO Auto-generated method stub
		// False for now so we can pretend
		return false;
	}
	
	private ResponseEntity<Object> getUserActiveSession() {
		return null;
	}



	private ResponseEntity<JSONObject> hubLogin(UserAuthenticationRequest login){
		HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<>(login, headers);
        return restTemplate.postForEntity(getHubUrl(), request, JSONObject.class);
	}
	
	private String getHubUrl() {
		return KlabCertificate
				.createFromString(Configuration.INSTANCE.getProperty("klab.certificate", ""))
				.getProperty(KlabCertificate.KEY_PARTNER_HUB) + 
				API.HUB.AUTHENTICATE_USER;
	}


}
