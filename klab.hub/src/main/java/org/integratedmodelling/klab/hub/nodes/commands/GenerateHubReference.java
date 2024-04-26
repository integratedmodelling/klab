package org.integratedmodelling.klab.hub.nodes.commands;

import java.time.LocalDateTime;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.auth.IPartnerIdentity;
import org.integratedmodelling.klab.auth.Hub;
import org.integratedmodelling.klab.auth.Partner;
import org.integratedmodelling.klab.rest.HubReference;
import org.integratedmodelling.klab.rest.IdentityReference;

public class GenerateHubReference {
	
	public HubReference execute() {
		Hub hub = Authentication.INSTANCE.getAuthenticatedIdentity(Hub.class);
		IPartnerIdentity rootIdentity = Authentication.INSTANCE.getAuthenticatedIdentity(Partner.class);
		
		IdentityReference partnerIdentity = new IdentityReference();
		partnerIdentity.setId(hub.getParentIdentity().getName());
		partnerIdentity.setEmail(hub.getParentIdentity().getEmailAddress());
		partnerIdentity.setLastLogin(LocalDateTime.now().toString());
		
		HubReference hubReference = new HubReference();
		hubReference.setId(hub.getName());
		hubReference.setOnline(true);
		hubReference.getUrls().addAll(hub.getUrls());
		hubReference.setPartner(partnerIdentity);
		return hubReference;
	}
}
