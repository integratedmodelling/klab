package org.integratedmodelling.klab.hub.config.dev;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.commands.CreateMongoGroup;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.utils.FileCatalog;

public class CreateIntialGroups {
	

	public CreateIntialGroups(URL fileUrl, MongoGroupRepository repo ) {
		super();
		this.fileUrl = fileUrl;
		this.repo = repo;
	}
	
	private URL fileUrl;
	private MongoGroupRepository repo;
	private Map<String, MongoGroup> groups = new HashMap<>();
	
	public void execute() {
		Map<String, MongoGroup> groups = new HashMap<>();
		groups = FileCatalog.create(fileUrl, MongoGroup.class);
		groups.forEach((k,v)->new CreateMongoGroup(v, repo).execute());
	}
	
}
