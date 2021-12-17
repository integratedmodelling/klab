package org.integratedmodelling.klab.engine.services;

import java.io.File;

import org.integratedmodelling.klab.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RemoteEngineDiskWatcher {
	
	@Autowired
	AgentServiceCheck agent;
	
	@Scheduled(fixedDelay = 200000)
	public void detectDiskFull() {
		File tmpdir = new File(System.getProperty("java.io.tmpdir"));
		double freeSpace = tmpdir.getFreeSpace() * 0.000001;
		if (freeSpace < 10) {
			System.exit(0);
		}
		if (freeSpace < 100) {
			Logging.INSTANCE.info("Temporary Disk free space: " + freeSpace);
			int halfWeight = agent.weight / 2 + 1;
			agent.setweight(halfWeight);
		}
	}
}
