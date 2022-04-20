package org.integratedmodelling.klab.engine.rest.controllers.engine;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.monitoring.TicketManager;
import org.integratedmodelling.klab.components.geospace.visualization.Renderer;
import org.integratedmodelling.klab.engine.runtime.APIObservationTask;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.Session.Estimate;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.provenance.Provenance;
import org.integratedmodelling.klab.rest.ContextRequest;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.ObservationRequest;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@Secured(Roles.SESSION)
public class EnginePublicController implements API.PUBLIC {

	@RequestMapping(value = CREATE_CONTEXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TicketResponse.Ticket contextRequest(@RequestBody ContextRequest request, @PathVariable String session) {

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

	@RequestMapping(value = OBSERVE_IN_CONTEXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
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
				request.isEstimate() ? ITicket.Type.ObservationEstimate : ITicket.Type.ObservationInContext, "url",
				OBSERVE_IN_CONTEXT, "user", user.getUsername(), "urn", request.getUrn(), "email",
				user.getEmailAddress());

		/*
		 * start the task and return the opened ticket
		 */
		APIObservationTask.submit(request, s, (IDirectObservation) ctx, ticket);

		return TicketManager.encode(ticket);
	}

	@RequestMapping(value = SUBMIT_ESTIMATE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TicketResponse.Ticket submitEstimate(@PathVariable String session, @PathVariable String estimate) {

		Session s = Authentication.INSTANCE.getIdentity(session, Session.class);
		if (s == null) {
			// FIXME not illegitimate in case of server failure or restart with persistent
			// tickets;
			// should send an error ticket instead
			throw new KlabIllegalStateException("submit estimate: invalid session ID");
		}

		Estimate est = ((Session) s).getEstimates().remove(estimate);
		if (est == null) {
			// FIXME not illegitimate in case of server failure or restart with persistent
			// tickets;
			// should send an error ticket instead
			throw new KlabIllegalStateException("submit estimate: estimate ID could not be found");
		}

		if (est.contextRequest != null) {
			return contextRequest(est.contextRequest, session);
		}

		return observationRequest(est.observationRequest, session, est.observationRequest.getContextId());
	}

	@RequestMapping(value = EXPORT_DATA, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_PDF_VALUE, MediaType.IMAGE_PNG_VALUE, "text/csv" })
	public void exportData(@PathVariable String export, @PathVariable String session, @PathVariable String observation,
			@RequestHeader(name = "Accept") String format, @RequestParam(required = false) String viewport,
			@RequestParam(required = false) String locator, HttpServletResponse response) throws IOException {

		Session s = Authentication.INSTANCE.getIdentity(session, Session.class);
		if (s == null) {
			throw new KlabIllegalStateException("observe in context: invalid session ID");
		}

		boolean done = false;
		IObservation obs = s.getObservation(observation);
		IUserIdentity user = s.getUser();
		Export target = Export.valueOf(export.toUpperCase());

		ILocator loc = obs.getScale().initialization();
		if (locator != null) {
			if (obs.getScale().getTime() != null && !locator.toLowerCase().startsWith("t")) {
				locator = "T1(1){time=INITIALIZATION}" + locator;
			}
			loc = Geometry.create(locator);
			loc = obs.getScale().at(loc);
		}

		switch (target) {
		case DATA:
			if (MediaType.IMAGE_PNG_VALUE.equals(format) && obs instanceof IState) {
				outputImage(obs, response, target, viewport, loc);
				done = true;
			}
			break;
		case LEGEND:
			if (MediaType.APPLICATION_JSON_VALUE.equals(format) && obs instanceof IState) {
				StateSummary summary = Observations.INSTANCE.getStateSummary((IState) obs, loc);
				if (summary.getColormap() == null) {
					/*
					 * force rendering before images are made. Adding the colormap to the state
					 * summary is a side effect.
					 */
					Renderer.INSTANCE.getRasterSymbolizer((IState) obs, loc);
				}
				if (summary.getColormap() != null) {
					response.getWriter().write(JsonUtils.printAsJson(summary.getColormap()));
					done = true;
				}
			}
			break;
		case DATAFLOW:
			if (MediaType.APPLICATION_JSON_VALUE.equals(format)) {
				response.getWriter().write(((IRuntimeScope)obs.getScope()).getElkGraph());
				done = true;
			} else if (MediaType.TEXT_PLAIN_VALUE.equals(format)) {
				response.getWriter().write(obs.getScope().getDataflow().getKdlCode());
				done = true;
			}
			break;
		case PROVENANCE_FULL:
		case PROVENANCE_SIMPLIFIED:
			if (MediaType.APPLICATION_JSON_VALUE.equals(format)) {
				response.getWriter().write(((Provenance)obs.getScope().getProvenance()).getElkGraph(target == Export.PROVENANCE_FULL));
				done = true;
			} else if (MediaType.TEXT_PLAIN_VALUE.equals(format)) {
				// this will currently throw an unimplemented exception
				response.getWriter().write(((Provenance)obs.getScope().getProvenance()).getKimCode(target == Export.PROVENANCE_FULL));
				done = true;
			}
			break;
		case REPORT:
			break;
		case STRUCTURE:
			ObservationReference ret = Observations.INSTANCE.createArtifactDescriptor((IObservation) obs);
			for (IObservation child : obs.getScope().getChildrenOf(obs)) {
				ret.getChildIds().put(child.getObservable().getName(), child.getId());
			}
			if (MediaType.APPLICATION_JSON_VALUE.equals(format)) {
				response.getWriter().write(JsonUtils.asString(ret));
				done = true;
			}
			break;
		}

		if (!done) {
			throw new KlabException("export request invalid or contents not available");
		}
	}

	private void outputImage(IObservation obs, HttpServletResponse response, Export target, String viewport,
			ILocator locator) throws IOException {

		if (obs instanceof IState) {

			if (target == Export.DATA) {

				BufferedImage image = Renderer.INSTANCE.render((IState) obs, locator,
						NumberUtils.intArrayFromString(viewport == null ? "800,800" : viewport));
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(image, "png", os);
				InputStream in = new ByteArrayInputStream(os.toByteArray());
				IOUtils.copy(in, response.getOutputStream());

			} else if (target == Export.LEGEND) {
				// TODO
			}
		}
	}

	@RequestMapping(value = TICKET_INFO, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TicketResponse.Ticket getTicketInfo(@PathVariable String session, @PathVariable String ticket) {

		Session s = Authentication.INSTANCE.getIdentity(session, Session.class);
		if (s == null) {
			// FIXME not illegitimate in case of server failure or restart with persistent
			// tickets;
			// should send an error ticket instead
			throw new KlabIllegalStateException("get ticket info: invalid session ID");
		}
		ITicket t = s.getTicket(ticket);

		/*
		 * FIXME same: ticket may be gone because the session has restarted - send an
		 * error ticket instead of nothing
		 */

		return t == null ? null : TicketManager.encode(t);
	}

}
