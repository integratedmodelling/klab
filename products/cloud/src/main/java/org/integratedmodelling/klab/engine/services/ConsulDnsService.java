package org.integratedmodelling.klab.engine.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.integratedmodelling.klab.engine.configs.ConsulAgentService;
import org.integratedmodelling.klab.engine.configs.ConsulConfig;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Splitter;

@Component
@ConditionalOnProperty(
        value="spring.cloud.consul.enabled", 
        havingValue = "true", 
        matchIfMissing = false)
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
		String name = session.getParentIdentity().getUsername();
		ConsulAgentService service = getService();
		Map<String, String> userWeights = getUserWeights(service);
		Iterator<String> iterator = userWeights.keySet().iterator();

	    while(iterator.hasNext()){
	    	String key = iterator.next();
	        if(key.equals(name)){
	        	int userWeight = Integer.parseInt(userWeights.get(name));
	        	service.getWeights().setPassing(service.getWeights().getPassing()+userWeight);
	        	iterator.remove();
	        }
	    }
		service.getMeta().put("Users", userWeights.toString());
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
			if(!service.getMeta().get("Users").equals("{}")) {
				 userWeights =  new HashMap<>(Splitter.on(",")
							.trimResults()
							.withKeyValueSeparator("=")
							.split(service.getMeta().get("Users")
									.replace("{", "").replace("}", "")));	
			}
		}
		return userWeights;
	}
	

}
