package org.integratedmodelling.klab.engine;

import org.springframework.stereotype.Service;

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
