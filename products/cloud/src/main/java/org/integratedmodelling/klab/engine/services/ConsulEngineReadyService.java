package org.integratedmodelling.klab.engine.services;


import org.integratedmodelling.klab.engine.RemoteEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Profile("consul")
public class ConsulEngineReadyService {
	
	@Autowired
	ConsulDnsService dnsService;
	
	@Autowired
	AgentServiceCheck check;
	
	@Autowired
	RemoteEngineService engineService;
	
    @EventListener(ApplicationReadyEvent.class)
    public void ContextRefreshedEventExecute(){
        engineService.getEngine().setDnsService(dnsService);
        check.start();
        engineService.getEngine().setCheck(check);
    }
    
}
