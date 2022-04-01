package org.integratedmodelling.klab.engine.rest.controllers.engine;

import java.security.Principal;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.monitoring.TicketManager;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.ContextRequest;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.ObservationRequest;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.integratedmodelling.klab.scale.Scale;
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
	public TicketResponse.Ticket contextRequest(Principal principal, @RequestBody ContextRequest request,
			@PathVariable String session) {

		Session s = Authentication.INSTANCE.getIdentity(session, Session.class);
		if (s == null) {
			throw new KlabIllegalStateException("create context: invalid session ID");
		}

		IUserIdentity user = s.getUser();
		Scale scale = Scale.create(Geometry.create(request.getGeometry()));
		s.getState().resetContext();

		/**
		 * TODO the tickets should be in the sessions and disappear with it. Should use
		 * MapDB to store, not the file-based ticket manager.
		 */
		ITicket ticket = Klab.INSTANCE.getTicketManager().open(
				request.isEstimate() ? ITicket.Type.ContextEstimate : ITicket.Type.ContextObservation, "url",
				CREATE_CONTEXT, "user", user.getUsername(), "geometry", request.getGeometry(), "urn",
				request.getContextType(), "email", user.getEmailAddress());

		if (request.isEstimate()) {

		} else {
			/*
			 * submit the task with a listener that closes the ticket. TODO do not use the session
			 * state - submit context, then all one by one in a specific method with specific listeners.
			 */
			s.getState().withScale(scale).submit(request.getContextType(), (task, artifact) -> {
				if (artifact != null) {
					/*
					 * TODO only resolve when the last of the observations requested comes in
					 */
					ticket.resolve("artifact", artifact.getId(), "session", s.getId(), "context",
							((IObservation) artifact).getScope().getRootSubject().getId());
				}
			}, (task, throwable) -> {
				ticket.error("failed contextualization: " + ExceptionUtils.getStackTrace(throwable));
			});
		}

		return TicketManager.encode(ticket);
	}

	@RequestMapping(value = OBSERVE_IN_CONTEXT, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public TicketResponse.Ticket observationRequest(@RequestBody ObservationRequest request,
			@PathVariable String session, @PathVariable String context) {
		return null;
	}

	@RequestMapping(value = RETRIEVE_OBSERVATION_DESCRIPTOR, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ObservationReference retrieveObservation(@PathVariable String session, @PathVariable String observation) {

		Session s = Authentication.INSTANCE.getIdentity(session, Session.class);
		if (s == null) {
			throw new KlabIllegalStateException("create context: invalid session ID");
		}

		IObservation obs = s.getObservation(observation);
		if (obs == null) {
			throw new KlabIllegalArgumentException("observation " + observation + " does not exist");
		}

		ObservationReference ret = Observations.INSTANCE.createArtifactDescriptor((IObservation) obs);
		
		/*
		 * TODO map the children to the URN of their requested observable for reference in the result. 
		 */
		
		return ret;
	}

	@RequestMapping(value = TICKET_INFO, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public TicketResponse.Ticket getTicketInfo(@PathVariable String session, @PathVariable String ticket) {
		ITicket t = Klab.INSTANCE.getTicketManager().getTicket(ticket);
		if (t != null) {
			if (t.getData().containsKey("session") && !t.getData().get("session").equals(session)) {
				throw new KlabValidationException("invalid ticket request: session IDs do not match");
			}
		}
		return t == null ? null : TicketManager.encode(t);
	}
	

}
