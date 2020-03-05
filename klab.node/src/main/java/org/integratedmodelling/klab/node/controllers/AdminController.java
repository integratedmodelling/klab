package org.integratedmodelling.klab.node.controllers;

import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.common.monitoring.Ticket;
import org.integratedmodelling.klab.common.monitoring.TicketManager;
import org.integratedmodelling.klab.engine.extensions.Component;
import org.integratedmodelling.klab.node.auth.Role;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Secured({Role.ADMINISTRATOR, Role.SYSTEM})
public class AdminController {

	// TODO remove
	@GetMapping(value = "admin-stuff", produces = "application/json")
	@ResponseBody
	public String test() {
		// TODO
		return "You have made it to the admin interface";
	}
	
	@GetMapping(value = API.NODE.ADMIN.COMPONENT_SETUP, produces = "application/json")
	public TicketResponse.Ticket setupComponent(@PathVariable String component) {
		Component comp = Extensions.INSTANCE.getComponent(component);
		if (comp != null) {
			return TicketManager.encode(comp.setup());
		}
		return null;
	}

	public String getProperty() {
		return null;
	}

	public void setProperty() {
	}

}
