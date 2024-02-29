package org.integratedmodelling.klab.hub.config.dev;

import org.integratedmodelling.klab.hub.licenses.dto.MongoLever;
import org.integratedmodelling.klab.hub.repository.MongoLeverRepository;

public class CreateInitialLevers {
	
	MongoLeverRepository repo;

	public CreateInitialLevers(MongoLeverRepository repo) {
		super();
		this.repo = repo;
	}
	
	/*
	 * create a single lever for testing purposes.
	 */
	public void execute() {
		MongoLever lever  = new MongoLever();
		lever.setName("testLever");
		lever.setEmail("info@integratedmodelling.org");
		new CreateMongoLever(lever, repo).execute();		
	}

}
