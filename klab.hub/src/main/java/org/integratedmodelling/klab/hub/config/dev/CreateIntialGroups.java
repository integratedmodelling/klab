package org.integratedmodelling.klab.hub.config.dev;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.users.commands.CreateMongoGroup;
import org.integratedmodelling.klab.hub.users.dto.MongoGroup;
import org.integratedmodelling.klab.utils.FileCatalog;

public class CreateIntialGroups {
	

	public CreateIntialGroups(URL fileUrl, MongoGroupRepository repo ) {
		super();
		this.fileUrl = fileUrl;
		this.repo = repo;
	}
	
	private URL fileUrl;
	private MongoGroupRepository repo;
	
	public void execute() {
		Map<String, MongoGroup> groups = new HashMap<>();
		groups = FileCatalog.create(fileUrl, MongoGroup.class);
		groups.forEach((k,v)->new CreateMongoGroup(v, repo).execute());
	}
	
}
