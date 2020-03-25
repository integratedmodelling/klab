package org.integratedmodelling.klab.hub.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.api.MongoNode;
import org.integratedmodelling.klab.hub.api.adapters.MongoGroupAdapter;
import org.integratedmodelling.klab.hub.groups.services.MongoGroupService;
import org.integratedmodelling.klab.hub.nodes.services.KlabNodeService;
import org.integratedmodelling.klab.rest.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KlabNodeManager {
	
	@Autowired
	KlabNodeService klabNodeService;
	
	@Autowired
	MongoGroupService klabGroupService;
	
	public Collection<MongoNode> getNodes() {
		return klabNodeService.getNodes();
	}
	
	public void updateLastEngineConnection(String nodename) {
		MongoNode node = klabNodeService.getNode(nodename)
				.orElseThrow(IllegalArgumentException::new);
		node.setLastConnection();
		klabNodeService.updateNodeLastConnection(nodename);
	}

	public MongoNode getNode(String nodename) {
		MongoNode node = klabNodeService.getNode(nodename)
				.orElseThrow(IllegalArgumentException::new);
		return node;
	}

	public List<Group> getNodeGroups(String nodename) {
		MongoNode node = klabNodeService.getNode(nodename)
				.orElseThrow(IllegalArgumentException::new);
		List<Group> listOfGroups = new ArrayList<>();
		for (MongoGroup mGroup : node.getGroups()) {
			Group group = new MongoGroupAdapter(mGroup).convertGroup();
			listOfGroups.add(group);
		}
		return listOfGroups;
	}
	
	public List<Group> getGroups() {
		List<Group> listOfGroups = new ArrayList<>();
		for (MongoGroup klabGroup : klabGroupService.getGroups()) {
			if(klabGroup != null) {
				Group group = new Group();
				group.setId(klabGroup.getId());
				group.setProjectUrls(klabGroup.getProjectUrls());
				group.setSshKey(klabGroup.getSshKey());
				group.setObservables(klabGroup.getObservableReferences());
				group.setWorldview(klabGroup.getWorldview());
				listOfGroups.add(group);
			}
		}
		return listOfGroups;
	}

	public Collection<String> getNodeNames() {
		Collection<MongoNode> nodes = getNodes();
		List<String> nodeNames = new ArrayList<String>();
		for (MongoNode node : nodes) {
			nodeNames.add(node.getName());
		}
		return nodeNames;
	}

	public MongoNode updateNodeGroups(String id, MongoNode node) {
		Collection<String> groupNames = klabGroupService.getGroupNames();
		if (groupNames.containsAll(node.getGroups())) {
			klabNodeService.getNode(id)
				.map(kldabNode -> {
					klabNodeService.updateNodeGroups(id, node);
					return true;
				})
				.orElseThrow(IllegalArgumentException::new);
		}
		return node;
	}

	public void deleteNode(String id) {
		klabNodeService.deleteNode(id);
	}

	public MongoNode createNode(String id, MongoNode node) {
		klabNodeService.getNode(id)
			.ifPresent(s -> {
				throw new KlabException("Node already exists");	
			});
		Collection<String> groupNames = klabGroupService.getGroupNames();
		if (groupNames.containsAll(node.getGroups())) {
			klabNodeService.createNode(id, node);
		} else {
			throw new KlabException("Node contains nonexistent groups");
		}
		return node;
	}
}
