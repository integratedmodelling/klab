package org.integratedmodelling.klab.hub.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.models.KlabGroup;
import org.integratedmodelling.klab.hub.models.KlabNode;
import org.integratedmodelling.klab.hub.service.KlabGroupService;
import org.integratedmodelling.klab.hub.service.KlabNodeService;
import org.integratedmodelling.klab.rest.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KlabNodeManager {
	
	@Autowired
	KlabNodeService klabNodeService;
	
	@Autowired
	KlabGroupService klabGroupService;
	
	public Collection<KlabNode> getNodes() {
		return klabNodeService.getNodes();
	}
	
	public void updateLastEngineConnection(String nodename) {
		KlabNode node = klabNodeService.getNode(nodename)
				.orElseThrow(IllegalArgumentException::new);
		node.setLastNodeConnection();
		klabNodeService.updateNodeLastConnection(nodename);
	}

	public KlabNode getNode(String nodename) {
		KlabNode node = klabNodeService.getNode(nodename)
				.orElseThrow(IllegalArgumentException::new);
		return node;
	}

	public List<Group> getNodeGroups(String nodename) {
		KlabNode node = klabNodeService.getNode(nodename)
				.orElseThrow(IllegalArgumentException::new);
		List<Group> listOfGroups = new ArrayList<>();
		for (String groupName : node.getGroups()) {
			if(groupName != null) {
				Group group = new Group();
				KlabGroup klabGroup = klabGroupService.getGroup(groupName)
						.orElseThrow(IllegalArgumentException::new);
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
	
	public List<Group> getGroups() {
		List<Group> listOfGroups = new ArrayList<>();
		for (KlabGroup klabGroup : klabGroupService.getGroups()) {
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
		Collection<KlabNode> nodes = getNodes();
		List<String> nodeNames = new ArrayList<String>();
		for (KlabNode node : nodes) {
			nodeNames.add(node.getNode());
		}
		return nodeNames;
	}

	public KlabNode updateNodeGroups(String id, KlabNode node) {
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

	public KlabNode createNode(String id, KlabNode node) {
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
