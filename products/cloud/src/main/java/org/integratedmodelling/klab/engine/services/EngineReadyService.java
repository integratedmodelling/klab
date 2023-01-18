package org.integratedmodelling.klab.engine.services;


import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.engine.RemoteEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class EngineReadyService {
	
	@Autowired
	AgentServiceCheck check;
	
	@Autowired
	RemoteEngineService engineService;
	
	@Value("${engine.sessions.inactive:60}")
    Long inactive;
	
    @EventListener(ApplicationReadyEvent.class)
    public void ContextRefreshedEventExecute(){
        engineService.getEngine().setSessionDeadBand(inactive);
        Logging.INSTANCE.info("Started Agent Check Port");
        engineService.getEngine().setCheck(check);
    }
    
}
