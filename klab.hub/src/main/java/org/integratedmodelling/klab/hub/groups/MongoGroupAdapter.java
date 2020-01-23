package org.integratedmodelling.klab.hub.groups;

import org.integratedmodelling.klab.rest.Group;

public class MongoGroupAdapter {
	
	public MongoGroupAdapter(MongoGroup mongoGroup) {
		super();
		this.mongoGroup = mongoGroup;
	}

	private MongoGroup mongoGroup;
	
	
	public Group convertGroup() {
		Group group = new Group();
		group.setId(mongoGroup.getId());
		group.setProjectUrls(mongoGroup.getProjectUrls());
		group.setSshKey(mongoGroup.getSshKey());
		group.setObservables(mongoGroup.getObservableReferences());
		group.setWorldview(mongoGroup.getWorldview());
		return group;
	}

}
