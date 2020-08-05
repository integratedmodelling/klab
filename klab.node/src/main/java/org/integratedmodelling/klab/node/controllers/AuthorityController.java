package org.integratedmodelling.klab.node.controllers;

import org.integratedmodelling.klab.Authorities;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.knowledge.IAuthority.Identity;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.rest.AuthorityIdentity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * These are public endpoints by virtue of the /public/ prefix.
 * 
 * @author Ferd
 *
 */
@RestController
public class AuthorityController {

	@GetMapping(value = API.AUTHORITY.RESOLVE, produces = "application/json")
	@ResponseBody
	public AuthorityIdentity resolve(@PathVariable String authority, @PathVariable String identifier) {
		Identity ret = Authorities.INSTANCE.getIdentity(authority, identifier);
		if (ret instanceof AuthorityIdentity) {
			return (AuthorityIdentity) ret;
		}
		throw new KlabResourceNotFoundException(
				"Authority " + authority + " does not exist or cannot resolve identity " + identifier);
	}

//	@PostMapping(value = API.AUTHORITY.QUERY, produces = "application/json")
//	@ResponseBody
//	public AuthorityIdentity query(@PathVariable String authority, @PathVariable String identifier) {
//		AuthorityIdentity ret = null;
//		return ret;
//	}

}
