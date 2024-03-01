package org.integratedmodelling.klab.hub.commands;

import java.time.LocalDateTime;
import java.util.Set;

import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.IdentityReference;

public class GetNodeAuthenticatedIdentity {
	
	public GetNodeAuthenticatedIdentity(INodeIdentity nodeIdentity, Set<Group> groups) {
		super();
		this.nodeIdentity = nodeIdentity;
		this.groups = groups;
	}

	private INodeIdentity nodeIdentity;
	private Set<Group> groups;
	
	public AuthenticatedIdentity execute() {
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expires = now.plusDays(90);
		
		IdentityReference nodeReference = new IdentityReference(nodeIdentity.getName(),
				nodeIdentity.getParentIdentity().getEmailAddress(), now.toString());
	
		AuthenticatedIdentity authenticatedIdentity = new AuthenticatedIdentity(nodeReference,
				groups, expires.toString(), nodeIdentity.getId());
		
		return authenticatedIdentity;
	}

}
