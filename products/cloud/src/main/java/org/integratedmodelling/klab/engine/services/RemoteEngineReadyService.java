package org.integratedmodelling.klab.engine.services;


import org.integratedmodelling.klab.engine.RemoteEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class RemoteEngineReadyService {
	
	@Autowired
	ConsulDnsService dnsService;
	
	@Autowired
	RemoteEngineService engineService;
	
    @EventListener(ApplicationReadyEvent.class)
    public void ContextRefreshedEventExecute(){
        engineService.getEngine().setDnsService(dnsService);
    }
    
}
