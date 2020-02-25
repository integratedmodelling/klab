package org.integratedmodelling.klab.node.controllers;

import org.integratedmodelling.klab.node.auth.Role;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Secured({Role.ADMINISTRATOR, Role.SYSTEM})
public class AuthorityController {
	//TODO just test endpoint
	@GetMapping(value = "admin-stuff", produces = "application/json")
	@ResponseBody
	public String test() {
		// TODO
		return "You have made it to the admin interface";
	}

}
