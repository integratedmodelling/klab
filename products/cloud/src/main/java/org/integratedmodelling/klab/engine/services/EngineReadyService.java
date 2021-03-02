package org.integratedmodelling.klab.engine.services;


import org.integratedmodelling.klab.engine.RemoteEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(
        value="spring.cloud.consul.enabled", 
        havingValue = "", 
        matchIfMissing = true)
public class EngineReadyService {
	
	@Autowired
	AgentServiceCheck check;
	
	@Autowired
	RemoteEngineService engineService;
	
    @EventListener(ApplicationReadyEvent.class)
    public void ContextRefreshedEventExecute(){
        check.start();
        engineService.getEngine().setCheck(check);
    }
    
}
