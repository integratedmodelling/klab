package org.integratedmodelling.klab.hub.nodes.commands;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.nodes.MongoNode;
import org.integratedmodelling.klab.hub.repository.MongoNodeRepository;

public class GetMongoNodeByName {
	
	private String nodeName;
	private MongoNodeRepository repository;
	
	public GetMongoNodeByName(String nodeName, MongoNodeRepository repository) {
		super();
		this.nodeName = nodeName;
		this.repository = repository;
	}
	
	public MongoNode execute() {
		return repository.findByNodeIgnoreCase(nodeName)
				.orElseThrow(() -> new BadRequestException("Node by that name does not exist"));
	}
	
}
