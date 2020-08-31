package org.integratedmodelling.klab.hub.config.dev;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.api.MongoNode;
import org.integratedmodelling.klab.hub.commands.CreateMongoNode;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.repository.MongoNodeRepository;

public class CreateIntialNodes {

	private MongoNodeRepository nodeRepo;
	private MongoGroupRepository groupRepo;

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
		mongoNode.setUrl("http://172.17.0.1:8287/node");
		new CreateMongoNode(mongoNode, nodeRepo).execute();	
	}
	
	

}
