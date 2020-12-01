package org.integratedmodelling.klab.engine.services;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.engine.configs.ConsulAgentService;
import org.integratedmodelling.klab.engine.configs.ConsulConfig;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Splitter;

@Component
public class ConsulDnsService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ConsulConfig consul;
	
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
	
	
	private ConsulAgentService getService () {
		ResponseEntity<ConsulAgentService> resp =
				restTemplate.exchange(consul.getServiceUrl(),
		                    HttpMethod.GET, null, ConsulAgentService.class);
		if (resp.getStatusCode().equals(HttpStatus.OK)) {
			return resp.getBody();
		} else {
			return null;
		}	
	}
	
	
	public void adjustServiceWeight(HubUserProfile profile) {
		ConsulAgentService service = getService();
		int userWeight = getProfileWeight(profile);
		Map<String, String> userWeights = getUserWeights(service);
		if(userWeights.containsKey(profile.getName())) {
			//user already on this engine
			return;
		} else {
			userWeights.put(profile.getName(), String.valueOf(userWeight));
			service.getMeta().put("Users", userWeights.toString());
			service.getWeights().setPassing(service.getWeights().getPassing()-userWeight);
		}
		service.setRegister();
		restTemplate.put(consul.registerServiceUrl(), service);
	}
	
	
	public void removeSessionWeight(Session session) {
		String name = session.getUsername();
		ConsulAgentService service = getService();
		Map<String, String> userWeights = getUserWeights(service);
		if(userWeights.containsKey(name)) {
			int userWeight = Integer.parseInt(userWeights.get(name));
			userWeights.remove(name);
			service.getWeights().setPassing(service.getWeights().getPassing()+userWeight);
			service.getMeta().put("Users", userWeights.toString());
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
	
	
	private Map<String, String> getUserWeights(ConsulAgentService service){
		Map<String, String> userWeights = new HashMap<>();
		if(service.getMeta().containsKey("Users")) {
			 userWeights = Splitter.on(",")
					.withKeyValueSeparator("=")
					.split(service.getMeta().get("Users")
							.replace("{", "").replace("}", ""));
		}
		return userWeights;
	}
	

}
