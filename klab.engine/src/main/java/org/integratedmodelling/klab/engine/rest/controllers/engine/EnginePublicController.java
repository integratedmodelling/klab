package org.integratedmodelling.klab.engine.rest.controllers.engine;

import java.security.Principal;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.monitoring.TicketManager;
import org.integratedmodelling.klab.engine.runtime.APIObservationTask;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
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

        /*
         * Tickets live in the sessions and disappear with it.
         */
        ITicket ticket = s.openTicket(
                request.isEstimate() ? ITicket.Type.ContextEstimate : ITicket.Type.ContextObservation, "url",
                CREATE_CONTEXT, "user", user.getUsername(), "geometry", request.getGeometry(), "urn",
                request.getContextType(), "email", user.getEmailAddress());

        /*
         * start the task and return the opened ticket
         */
        APIObservationTask.submit(request, s, scale, ticket);

        return TicketManager.encode(ticket);
    }

    @RequestMapping(value = OBSERVE_IN_CONTEXT, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public TicketResponse.Ticket observationRequest(@RequestBody ObservationRequest request,
            @PathVariable String session, @PathVariable String context) {
        Session s = Authentication.INSTANCE.getIdentity(session, Session.class);

        if (s == null) {
            throw new KlabIllegalStateException("observe in context: invalid session ID");
        }

        IObservation ctx = s.getObservation(context);
        IUserIdentity user = s.getUser();

        if (!(ctx instanceof IDirectObservation)) {
            throw new KlabIllegalStateException(
                    "observe in context: invalid context ID: not existing or not a direct observation");
        }

        /*
         * Tickets live in the sessions and disappear with it.
         */
        ITicket ticket = s.openTicket(
                request.isEstimate() ? ITicket.Type.ObservationEstimate : ITicket.Type.ObservationInContext,
                "url", OBSERVE_IN_CONTEXT, "user", user.getUsername(), "urn",
                request.getUrn(), "email", user.getEmailAddress());

        /*
         * start the task and return the opened ticket
         */
        APIObservationTask.submit(request, s, (IDirectObservation) ctx, ticket);

        return TicketManager.encode(ticket);
    }

    @RequestMapping(value = RETRIEVE_OBSERVATION_DESCRIPTOR, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ObservationReference retrieveObservation(@PathVariable String session,
            @PathVariable String observation) {

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
         * map the children IDs to the given name of their requested observable for reference in the
         * result.
         */
        for (IObservation child : obs.getScope().getChildrenOf(obs)) {
            ret.getChildIds().put(child.getObservable().getName(), child.getId());
        }

        return ret;
    }

    @RequestMapping(value = TICKET_INFO, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public TicketResponse.Ticket getTicketInfo(@PathVariable String session, @PathVariable String ticket) {

        Session s = Authentication.INSTANCE.getIdentity(session, Session.class);
        if (s == null) {
            // FIXME not illegitimate in case of server failure or restart with persistent tickets;
            // should send an error ticket instead
            throw new KlabIllegalStateException("get ticket info: invalid session ID");
        }
        ITicket t = s.getTicket(ticket);

        /*
         * FIXME same: ticket may be gone because the session has restarted - send an error ticket
         * instead of nothing
         */

        return t == null ? null : TicketManager.encode(t);
    }

}
