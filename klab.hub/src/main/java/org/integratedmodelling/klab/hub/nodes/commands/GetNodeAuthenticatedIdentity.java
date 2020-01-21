package org.integratedmodelling.klab.hub.nodes.commands;

import java.util.Set;

import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.hub.authentication.HubAuthenticationManager;
import org.integratedmodelling.klab.hub.nodes.MongoNode;
import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.joda.time.DateTime;

public class GetNodeAuthenticatedIdentity {
	
	public GetNodeAuthenticatedIdentity(MongoNode mongoNode, Set<Group> groups) {
		super();
		this.mongoNode = mongoNode;
		this.groups = groups;
	}

	private MongoNode mongoNode;
	private Set<Group> groups;
	
	public AuthenticatedIdentity execute() {
		
		INodeIdentity node = new GetINodeIdentity(mongoNode).execute();
		
		DateTime now = DateTime.now();
		DateTime expires = now.plusDays(90);
		
		IdentityReference nodeReference = new IdentityReference(node.getName(),
				node.getParentIdentity().getEmailAddress(), now.toString());
	
		AuthenticatedIdentity authenticatedIdentity = new AuthenticatedIdentity(nodeReference,
				groups, expires.toString(), node.getId());
		
		return authenticatedIdentity;
	}

}
