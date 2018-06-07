package org.integratedmodelling.klab.engine.rest.controllers.engine;

import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.StateSummary;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	public ObservationReference describeObservation(Principal session) {
		return null;
	}

	/**
	 * Get a summary of the observation state, either globally or at location
	 * (through a parameter)
	 */
	@RequestMapping(value = API.ENGINE.OBSERVATION.VIEW.SUMMARIZE_OBSERVATION, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public StateSummary summarizeObservation(Principal session) {
		return null;
	}

	/**
	 * Get one or more siblings of an artifact, potentially with offsets and number
	 */
	@RequestMapping(value = API.ENGINE.OBSERVATION.VIEW.GET_SIBLINGS_OBSERVATION, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ObservationReference getObservationSiblings(Principal session) {
		return null;
	}

	/**
	 * Get the data for a state in directly useable form, as values or images 
	 * TODO
	 * use filters or HttpMessageConverter/content negotiation for various media types - see
	 * https://stackoverflow.com/questions/3668185/how-to-define-message-converter-based-on-url-extension-in-spring-mvc
	 * http://www.baeldung.com/spring-mvc-content-negotiation-json-xml
	 */
	@RequestMapping(value = API.ENGINE.OBSERVATION.VIEW.GET_DATA_OBSERVATION, method = RequestMethod.GET)
	@ResponseBody
	public void getObservationData(Principal session, HttpServletResponse response) {
		// for an image:
		// InputStream in =
		// servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
		// response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		// IOUtils.copy(in, response.getOutputStream());
	}

}
