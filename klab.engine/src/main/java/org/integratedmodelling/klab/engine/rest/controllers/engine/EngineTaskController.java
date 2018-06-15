package org.integratedmodelling.klab.engine.rest.controllers.engine;

import java.security.Principal;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller implementing the {@link org.integratedmodelling.klab.api.API.ENGINE.OBSERVATION.TASK task API}.
 * 
 * @author ferdinando.villa
 *
 */
@RestController
@Secured(Roles.SESSION)
public class EngineTaskController {
	
	/**
	 * Interrupt passed task, notifying any computation through the monitor.
	 * 
	 * @param principal
	 * @param task
	 * @param force
	 * @return true if interruption was achieved
	 */
	@RequestMapping(value = API.ENGINE.OBSERVATION.TASK.INTERRUPT, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public boolean describeObservation(Principal principal, @PathVariable String task,
			@RequestParam(required = false) Boolean force) {
		ISession session = EngineSessionController.getSession(principal);
		return ((Session)session).interruptTask(task, force);
	}
}
