package org.integratedmodelling.klab.engine;

import org.integratedmodelling.klab.engine.services.ConsulDnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoteEngineService {
	
//	@Autowired
//	ConsulDnsService dnsService;
	
	RemoteEngine engine;

	public void setEngine(RemoteEngine engine) {
		this.engine = engine;
	}
	
	public RemoteEngine getEngine() {
		return this.engine;
	}

	
}
