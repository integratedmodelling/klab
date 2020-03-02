package org.integratedmodelling.klab.hub.authentication.commands;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.auth.Hub;
import org.integratedmodelling.klab.rest.HubReference;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.joda.time.DateTime;

public class GenerateHubReference {
	
	public HubReference execute() {
		Hub hub = Authentication.INSTANCE.getAuthenticatedIdentity(Hub.class);
		
		
		IdentityReference partnerIdentity = new IdentityReference();
		partnerIdentity.setId(hub.getParentIdentity().getId());
		partnerIdentity.setEmail(hub.getParentIdentity().getEmailAddress());
		partnerIdentity.setLastLogin(DateTime.now().toString());
		
		HubReference hubReference = new HubReference();
		hubReference.setId(hub.getName());
		hubReference.setOnline(true);
		hubReference.getUrls().addAll(hub.getUrls());
		hubReference.setPartner(partnerIdentity);
		return hubReference;
	}
}
