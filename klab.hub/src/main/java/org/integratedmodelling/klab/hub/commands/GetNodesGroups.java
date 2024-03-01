package org.integratedmodelling.klab.hub.commands;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.api.MongoNode;
import org.integratedmodelling.klab.rest.Group;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetNodesGroups {
	
	private MongoNode node;
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public GetNodesGroups(MongoNode node) {
		super();
		this.node = node;
		this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public Set<Group> execute() {
		Set<MongoGroup> mongoGroups = node.getGroups();
		Set<Group> groups = new HashSet<>();
		
		mongoGroups.forEach(
				mongoGroup -> groups.add(objectMapper.convertValue(mongoGroup, Group.class)));
		
		return groups;
	}

}
