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
import org.integratedmodelling.klab.engine.configs.ConsulConfig;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.UserAuthenticationRequest;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

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
public class HubUserService implements RemoteUserService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ConsulConfig consul;
	
	/*
	 * Generates a response entity a url to the session generated after succesful
	 * authentication.
	 */
	@Override
	public ResponseEntity<?> login(UserAuthenticationRequest login) throws JSONException {
		ResponseEntity<HubLoginResponse> result;
		try {
			result = hubLogin(login);
		} catch (HttpClientErrorException e) {
			if (e.getRawStatusCode() == 401) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to login user: " + login.getUsername());
			}
			throw new KlabAuthorizationException("Failed to login user: " + login.getUsername(), e); 
		}
		
		
        if (result != null && result.getStatusCode().is2xxSuccessful()) {
    		
        	if(activeSession()) {
        		return getUserActiveSession();
        	}
			
        	HubUserProfile profile = result.getBody().getProfile();
        	
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
			//consul.setWeight(user.getWeight(), restTemplate);
			adjustServiceWeight(profile);
			
			String redirectUrl = "/modeler/ui/viewer?session=" + sessionId;
			
			RemoteUserLoginResponse response = new RemoteUserLoginResponse();

			response.setRedirect(redirectUrl);
			response.setAuthorization(token);
			
		    return ResponseEntity.status(HttpStatus.ACCEPTED)
		            .location(URI.create(redirectUrl))
		            .body(response);
		} else {
			throw new KlabAuthorizationException("Failed to login user: " + login.getUsername());			
		}
		
	}
	

	/*
	 * This is a mock of different weights.
	 */
	private int getProfileWeight(HubUserProfile profile) {
		if(profile.getRoles().contains("ROLE_SYSTEM")){
			return 21845;
		} else if (profile.getRoles().contains("ROLE_ADMINSTRATOR")) {
			return 13107;
		} else {
			return 4369;
		}
		
	}



//	private HubUserProfile getProfile(Object object) throws IllegalArgumentException, JSONException {
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.registerModule(new JodaModule());
//		HubUserProfile profile = mapper.convertValue(object.get("Profile"), HubUserProfile.class);
//		return profile;
//	}


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
	
	
	private ResponseEntity<?> getUserActiveSession() {
		return null;
	}



	private ResponseEntity<HubLoginResponse> hubLogin(UserAuthenticationRequest login){
		HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<>(login, headers);
        return restTemplate.postForEntity(getHubUrl(), request, HubLoginResponse.class);
	}
	
	
	private String getHubUrl() {
		return KlabCertificate
				.createFromString(Configuration.INSTANCE.getProperty("klab.certificate", ""))
				.getProperty(KlabCertificate.KEY_PARTNER_HUB) + 
				API.HUB.AUTHENTICATE_USER;
	}
	
	
	public ConsulAgentService getService () {
		ResponseEntity<ConsulAgentService> resp =
				restTemplate.exchange(consul.getServiceUrl(),
		                    HttpMethod.GET, null, ConsulAgentService.class);
		
		
		if (resp.getStatusCode().equals(HttpStatus.OK)) {
			return resp.getBody();
		} else {
			return null;
		}
		
	}
	
	
	public void adjustServiceWeight(HubUserProfile profile) throws JSONException {
			ConsulAgentService service = getService();
			
			int userWeight = getProfileWeight(profile); 
			service.getWeights().setPassing(service.getWeights().getPassing()-userWeight);
			
			if(service.getMeta().containsKey("Users")) {
				JSONObject json = new JSONObject(service.getMeta().get("Users"));
				if(json.has(profile.getName())) {
					return;
				} else {
					json.append(profile.getName(), userWeight);
					service.getMeta().replace("Users", json.toString());	
				}
			} else {
				JSONObject entry = new JSONObject().put(profile.getName(), userWeight);
				service.getMeta().put("Users", entry.toString());
			}
			
			service.setRegister();
			restTemplate.put(consul.registerServiceUrl(), service);
	}
	
	
	public void setIntialServiceWeight() {
			ConsulAgentService service = getService();
			service.getWeights().setPassing(65535);
			service.setRegister();
			restTemplate.put(consul.registerServiceUrl(), service);
	}
	
}
