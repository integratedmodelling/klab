package org.integratedmodelling.klab.engine.rest.controllers.engine;

import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.StateSummary;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller implementing the
 * {@link org.integratedmodelling.klab.api.API.ENGINE.OBSERVATION.VIEW view
 * API}.
 * 
 * @author ferdinando.villa
 *
 */
@RestController
@Secured(Roles.SESSION)
public class EngineViewController {

	/**
	 * Get the observation structure and description
	 */
	@RequestMapping(value = API.ENGINE.OBSERVATION.VIEW.DESCRIBE_OBSERVATION, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ObservationReference describeObservation(Principal principal, @PathVariable String observation,
			@RequestParam(required = false) String childCount) {

		ISession session = getSession(principal);
		IObservation obs = session.getObservation(observation);

		return Observations.INSTANCE.createArtifactDescriptor(obs, obs.getContext());
	}

	/**
	 * Get a summary of the observation state, either globally or at location
	 * (through a parameter)
	 */
	@RequestMapping(value = API.ENGINE.OBSERVATION.VIEW.SUMMARIZE_OBSERVATION, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public StateSummary summarizeObservation(Principal principal, @PathVariable String observation) {

		ISession session = getSession(principal);
		IObservation obs = session.getObservation(observation);

		if (!(obs instanceof IState)) {
			throw new IllegalArgumentException("cannot summarize an observation that is not a state");
		}

		return null;
	}

	/**
	 * Get one or more siblings of an artifact, potentially with offsets and number. The response will contain
	 * the first sibling requested containing all the others as siblings.
	 */
	@RequestMapping(value = API.ENGINE.OBSERVATION.VIEW.GET_SIBLINGS_OBSERVATION, params = { "offset",
			"count" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ObservationReference getObservationSiblings(Principal principal, @PathVariable String observation,
			@RequestParam(required = false) String offset, @RequestParam(required = false) String count) {

		ISession session = getSession(principal);
		IObservation obs = session.getObservation(observation);
		
		return null;
	}

	/**
	 * Get the data for a state in directly useable form, as values or images TODO
	 * use filters or HttpMessageConverter/content negotiation for various media
	 * types - see
	 * https://stackoverflow.com/questions/3668185/how-to-define-message-converter-based-on-url-extension-in-spring-mvc
	 * http://www.baeldung.com/spring-mvc-content-negotiation-json-xml
	 */
	@RequestMapping(value = API.ENGINE.OBSERVATION.VIEW.GET_DATA_OBSERVATION, method = RequestMethod.GET)
	@ResponseBody
	public void getObservationData(Principal principal, @PathVariable String observation,
			HttpServletResponse response) {

		ISession session = getSession(principal);
		IObservation obs = session.getObservation(observation);

		// for an image:
		// InputStream in =
		// servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
		// response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		// IOUtils.copy(in, response.getOutputStream());
	}

	private ISession getSession(Principal principal) {
		if (principal instanceof PreAuthenticatedAuthenticationToken
				|| !(((PreAuthenticatedAuthenticationToken) principal).getPrincipal() instanceof ISession)) {
			return (ISession) ((PreAuthenticatedAuthenticationToken) principal).getPrincipal();
		}
		throw new IllegalStateException(
				"request was not authenticated using a session token or did not use preauthentication");
	}

}
