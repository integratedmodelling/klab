package org.integratedmodelling.klab.engine.services;


import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.engine.RemoteEngineService;
import org.integratedmodelling.klab.engine.events.UserEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class EngineReadyService {
	
	@Autowired
	AgentServiceCheck check;
	
	@Autowired
	RemoteEngineService engineService;
	
	@Autowired
	UserEventPublisher publisher;
	
    @EventListener(ApplicationReadyEvent.class)
    public void ContextRefreshedEventExecute(){
        engineService.getEngine().setPublisher(publisher);
        Logging.INSTANCE.info("Started Agent Check Port");
        check.start();
        engineService.getEngine().setCheck(check);

    }
    
}
