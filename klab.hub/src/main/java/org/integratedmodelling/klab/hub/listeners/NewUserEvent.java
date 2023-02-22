package org.integratedmodelling.klab.hub.listeners;

import java.time.LocalDate;

import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.users.services.UserGroupEntryService;
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
		LocalDate expires = LocalDate.now().plusDays(31);
		service.addPrelimenaryUserGroups(user, expires);
	}
}
