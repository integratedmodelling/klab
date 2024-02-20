package org.integratedmodelling.klab.hub.config.dev;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.hub.nodes.commands.CreateMongoNode;
import org.integratedmodelling.klab.hub.nodes.dtos.MongoNode;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.repository.MongoNodeRepository;
import org.integratedmodelling.klab.hub.users.dto.MongoGroup;

public class CreateIntialNodes {

	private MongoNodeRepository nodeRepo;
	private MongoGroupRepository groupRepo;
	
	public static String LOCAL_DEV_NODE_URL = "http://172.17.0.1:8287/node";

	public CreateIntialNodes(MongoNodeRepository nodeRepo, MongoGroupRepository groupRepo) {
		this.nodeRepo = nodeRepo;
		this.groupRepo = groupRepo;
	}

	public void execute() {
		MongoNode mongoNode = new MongoNode();
		mongoNode.setEmail("support@integratedmodelling.org");
		Set<MongoGroup> hSet = new HashSet<MongoGroup>(); 
		hSet.addAll(groupRepo.findAll());
		mongoNode.setGroups(hSet);
		mongoNode.setName("knot");
		mongoNode.setUrl(Configuration.INSTANCE.getProperty(IConfigurationService.KLAB_DEV_NODE_URL, LOCAL_DEV_NODE_URL));
		new CreateMongoNode(mongoNode, nodeRepo).execute();	
	}
	
	

}
