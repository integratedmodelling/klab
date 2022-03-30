package org.integratedmodelling.klab.engine.rest.controllers.engine;

import java.security.Principal;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.monitoring.TicketManager;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.ContextRequest;
import org.integratedmodelling.klab.rest.ObservationRequest;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.integratedmodelling.klab.rest.TicketResponse.Ticket;
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

		/**
		 * TODO the tickets should be in the sessions and disappear with it. Should use
		 * MapDB to store, not the file-based ticket manager.
		 */
		ITicket ticket = Klab.INSTANCE.getTicketManager().open(
				request.isEstimate() ? ITicket.Type.ContextEstimate : ITicket.Type.ContextObservation, "url",
				CREATE_CONTEXT, "user", user.getUsername(), "geometry", request.getGeometry(), "urn", request.getUrn(),
				"email", user.getEmailAddress());

		if (request.isEstimate()) {

		} else {
			/*
			 * submit the task with a listener that closes the ticket.
			 */
			s.getState().withScale(scale).submit(request.getUrn(), (task, artifact) -> {
				ticket.resolve("artifact", artifact.getId(), "session", s.getId(), "context",
						((IObservation) artifact).getScope().getRootSubject().getId());
			}, (task, throwable) -> {
				ticket.error("failed contextualization: " + ExceptionUtils.getStackTrace(throwable));
			});
		}

		return (Ticket) ticket;
	}

	@RequestMapping(value = OBSERVE_IN_CONTEXT, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public TicketResponse.Ticket observationRequest(@RequestBody ObservationRequest request,
			@PathVariable String session, @PathVariable String context) {
		return null;
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
