package org.integratedmodelling.klab.engine.services;


import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.engine.RemoteEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class EngineReadyService {
	
	@Autowired
	AgentServiceCheck check;
	
	@Autowired
	RemoteEngineService engineService;
	
    @EventListener(ApplicationReadyEvent.class)
    public void ContextRefreshedEventExecute(){
        Logging.INSTANCE.info("Started Agent Check Port");
        check.start();
        engineService.getEngine().setCheck(check);
    }
    
}
