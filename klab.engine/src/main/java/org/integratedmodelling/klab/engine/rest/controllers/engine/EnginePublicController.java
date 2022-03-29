package org.integratedmodelling.klab.engine.rest.controllers.engine;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.rest.ContextRequest;
import org.integratedmodelling.klab.rest.ObservationRequest;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@Secured(Roles.SESSION)
public class EnginePublicController implements API.PUBLIC {

	@RequestMapping(value = CREATE_CONTEXT, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public TicketResponse.Ticket contextRequest(@RequestBody ContextRequest request, @PathVariable String session) {
		return null;
	}

	@RequestMapping(value = OBSERVE_IN_CONTEXT, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public TicketResponse.Ticket observationRequest(@RequestBody ObservationRequest request,
			@PathVariable String session, @PathVariable String context) {
		return null;
	}

}
