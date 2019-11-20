package org.integratedmodelling.klab.hub.controllers;

import javax.annotation.security.RolesAllowed;

import org.integratedmodelling.klab.hub.authentication.GroupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RolesAllowed({ "ROLE_ENGINE", "ROLE_SYSTEM", "ROLE_SYSTEM" })
public class IndexingController {

	@Autowired
	GroupManager groupManager;


}
