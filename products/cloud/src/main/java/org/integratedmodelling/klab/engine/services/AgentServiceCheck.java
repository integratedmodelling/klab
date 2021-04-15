package org.integratedmodelling.klab.engine.services;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.engine.api.ComputeWeightFactor;
import org.integratedmodelling.klab.engine.configs.ConsulConfig;
import org.integratedmodelling.klab.engine.events.GenericUserEvent;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AgentServiceCheck {
    
    @Value("${engine.agent.port:8999}")
    private int port;

	//to be set from config
	int weight = 100;
	int overload = 0;

//	@Autowired
//	ConsulConfig consul;
	
	@Autowired
	RestTemplate template;
	
	@Autowired
	ObjectMapper mapper;

	
	public void start() {
       try (ServerSocket serverSocket = new ServerSocket(port)) {
 
            while (true) {
                Socket socket = serverSocket.accept();
                new AgentCheckThread(socket, weight).start();
            }
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }	
	}
	
	public void setweight(int weight) {
		this.weight = weight;
	}

	public void removeServiceWeight(HubUserProfile profile) {
		int usersWeight = ComputeWeightFactor.compute(profile);
		removeWeight(usersWeight);
		
	}
	
	public void addServiceWeight(HubUserProfile profile) {
		int usersWeight = ComputeWeightFactor.compute(profile);
		addWeight(usersWeight);
		
	}
	
	private void removeWeight(int usersWeight) {
		if (this.weight - usersWeight > 0 ) {
			this.weight = this.weight - usersWeight;
		} else {
			//we should not go below 1 and it should not be chosen for new connections
			this.overload = this.weight - usersWeight; 
			this.weight = 1;	 
		}
	}
	
	private void addWeight(int usersWeight) {
		if (this.overload != 0 ) {
			usersWeight = usersWeight + overload;
			overload = 0;
			this.weight = this.weight + usersWeight;
		} else {
			this.weight = this.weight + usersWeight;	 
		}
	}
	
	@EventListener(condition = "#event.type == T(org.integratedmodelling.klab.engine.events.UserEventType).LOGIN")
	void handleLogin(GenericUserEvent<HubUserProfile, Session> event) {
		HubUserProfile profile = event.getProfile();
		Session session = event.getSession();
		removeServiceWeight(profile);
		//addUserToStore(profile, session);		
	}
	
	@EventListener(condition = "#event.type == T(org.integratedmodelling.klab.engine.events.UserEventType).LOGOUT")
	void handleLogout(GenericUserEvent<HubUserProfile, Session> event) {
	    //removeUserFromStore(event.getProfile().getName());
    	addServiceWeight(event.getProfile());
    }
	
	
//	private boolean addUserToStore(HubUserProfile profile, Session session) {
//		Map<String,List<Object>> payload = new HashMap<>();
//		List<Object> details = new ArrayList<Object>();
//		details.add(profile);
//		details.add(session.getSessionReference());
//		payload.put(profile.getName(), details);
//		payload.put("engine", Arrays.asList(consul.getId()));
//
//		HttpEntity<?> requestUpdate = new HttpEntity<>(payload, getHeaders());
//		ResponseEntity<Boolean> success = template.exchange(consul.getStoreUrl() + "/" + profile.getName(),
//				HttpMethod.PUT, requestUpdate, Boolean.class);
//		return success.getBody();
//	}
//	
//	private boolean removeUserFromStore(String username) {
//		HttpEntity<?> delete = new HttpEntity<>(getHeaders());
//		ResponseEntity<Boolean> success = template.exchange(consul.getStoreUrl() + "/" + username,
//				HttpMethod.DELETE, delete, Boolean.class);
//		return success.getBody();
//	}
//	
//	private HttpHeaders getHeaders() {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		return headers;
//	}
	
}
