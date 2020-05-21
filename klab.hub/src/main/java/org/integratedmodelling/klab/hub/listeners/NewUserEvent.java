package org.integratedmodelling.klab.hub.listeners;

import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.users.services.UserGroupEntryService;
import org.joda.time.DateTime;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NewUserEvent {
	
	private UserGroupEntryService service;

	public NewUserEvent(UserGroupEntryService service) {
		super();
		this.service = service;
	}
	
	@EventListener
	public void addGroupEntries(NewUserAdded event) {
		User user = event.getUser();
		DateTime expires = DateTime.now().plusDays(31);
		service.addPrelimenaryUserGroups(user, expires);
	}
}
