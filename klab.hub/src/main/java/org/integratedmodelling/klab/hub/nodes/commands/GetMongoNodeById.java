package org.integratedmodelling.klab.hub.nodes.commands;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.nodes.dtos.MongoNode;
import org.integratedmodelling.klab.hub.repository.MongoNodeRepository;

public class GetMongoNodeById {

	private String id;
	private MongoNodeRepository repository;

	public GetMongoNodeById(String id, MongoNodeRepository nodeRepository) {
		this.id = id;
		this.repository = nodeRepository;
	}

	public MongoNode execute() {
		return repository.findById(id)
				.orElseThrow(() -> new BadRequestException("Node by that name does not exist"));
	}

}
