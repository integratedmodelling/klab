package org.integratedmodelling.klab.node.controllers;

import org.integratedmodelling.klab.node.auth.Role;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	public TicketResponse.Ticket setupComponent() {
		return null;
	}

	public String getProperty() {
		return null;
	}

	public void setProperty() {
	}

}
