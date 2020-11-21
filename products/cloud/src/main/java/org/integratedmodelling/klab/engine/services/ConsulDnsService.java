package org.integratedmodelling.klab.engine.services;

import org.integratedmodelling.klab.engine.configs.ConsulAgentService;
import org.integratedmodelling.klab.engine.configs.ConsulConfig;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
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
	
	
	public void adjustServiceWeight(HubUserProfile profile) {
			ConsulAgentService service = getService();
			
			int userWeight = getProfileWeight(profile); 
			service.getWeights().setPassing(service.getWeights().getPassing()-userWeight);
			try {
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
			} catch(JSONException ex) {
				throw new KlabException("Error in seeting DNS service weight");
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
