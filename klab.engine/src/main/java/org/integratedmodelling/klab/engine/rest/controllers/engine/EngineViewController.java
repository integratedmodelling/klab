package org.integratedmodelling.klab.engine.rest.controllers.engine;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.Principal;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.rest.IObservationReference;
import org.integratedmodelling.klab.components.geospace.visualization.Renderer;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.ObservationReference.GeometryType;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*")
@Secured(Roles.SESSION)
public class EngineViewController {

	/**
	 * Get the observation structure and description. This ignores siblings (which
	 * will always be an empty array) and allows retrieving children at arbitrary
	 * levels. Default child level is "all children".
	 */
	@RequestMapping(value = API.ENGINE.OBSERVATION.VIEW.DESCRIBE_OBSERVATION, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public IObservationReference describeObservation(Principal principal, @PathVariable String observation,
			@RequestParam(required = false) Integer childLevel, @RequestParam(required = false) String locator) {

		ISession session = EngineSessionController.getSession(principal);
		IObservation obs = session.getObservation(observation);
		ILocator loc = ITime.INITIALIZATION; // TODO parse the locator

		return Observations.INSTANCE.createArtifactDescriptor(obs, obs.getContext(), loc,
				childLevel == null ? -1 : childLevel, false);
	}

	/**
	 * Get a summary of the observation state, either globally or at location
	 * through an optional locator parameter.
	 */
	@RequestMapping(value = API.ENGINE.OBSERVATION.VIEW.SUMMARIZE_OBSERVATION, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public StateSummary summarizeObservation(Principal principal, @PathVariable String observation,
			@RequestParam(required = false) String locator) {

		ISession session = EngineSessionController.getSession(principal);
		IObservation obs = session.getObservation(observation);
		ILocator loc = ITime.INITIALIZATION; // TODO parse the locator

		if (!(obs instanceof IState)) {
			throw new IllegalArgumentException("cannot summarize an observation that is not a state");
		}

		return Observations.INSTANCE.getStateSummary((IState)obs, loc);
	}

	/**
	 * Get one or more siblings of an artifact, potentially with offsets and number.
	 * The response will contain the first sibling requested containing all the
	 * others as siblings. The optional childLevel parameter defines the level of
	 * the children representation in each sibling. If the sibling count is 1
	 * (default) the observation will return either the original observation or its
	 * sibling at the offset.
	 */
	@RequestMapping(value = API.ENGINE.OBSERVATION.VIEW.GET_SIBLINGS_OBSERVATION, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public IObservationReference getObservationSiblings(Principal principal, @PathVariable String observation,
			@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer count,
			@RequestParam(required = false) Integer childLevel, @RequestParam(required = false) String locator) {

		ISession session = EngineSessionController.getSession(principal);
		IObservation obs = session.getObservation(observation);
		ILocator loc = ITime.INITIALIZATION; // TODO parse locator

		IObservationReference ret = null;

		if (offset != null || count != null) {
			if (offset == null) {
				offset = 0;
			}
			if (count == null) {
				count = 1;
			}

			int nc = 0;
			Iterator<IArtifact> it = obs.iterator();
			for (int i = 0; i < obs.groupSize(); i++) {
				IArtifact artifact = it.next();
				if (i >= offset) {
					ObservationReference ref = Observations.INSTANCE.createArtifactDescriptor((IObservation) artifact,
							obs.getContext(), loc, childLevel == null ? 0 : childLevel, false);
					if (ret == null) {
						ret = ref;
					} else {
						ret.getSiblings().add(ref);
					}
					nc++;
				}
				if (count > 0 && nc > count) {
					break;
				}
			}
		}

		return ret;
	}

	/**
	 * Get the data for a state in directly usable form, as values or images TODO
	 * use filters or HttpMessageConverter/content negotiation for various media
	 * types - see
	 * https://stackoverflow.com/questions/3668185/how-to-define-message-converter-based-on-url-extension-in-spring-mvc
	 * http://www.baeldung.com/spring-mvc-content-negotiation-json-xml
	 */
	@RequestMapping(value = API.ENGINE.OBSERVATION.VIEW.GET_DATA_OBSERVATION, method = RequestMethod.GET)
	public void getObservationData(Principal principal, @PathVariable String observation,
			@RequestParam(required = false) String viewport, @RequestParam(required = false) String locator,
			@RequestParam ObservationReference.GeometryType format, HttpServletResponse response) throws Exception {

		ISession session = EngineSessionController.getSession(principal);
		IObservation obs = session.getObservation(observation);

		// TODO link to locator parameter
		ILocator timeLocator = ITime.INITIALIZATION;

		if (obs instanceof IState) {

			if (format == GeometryType.RASTER) {
				BufferedImage image = Renderer.INSTANCE.render((IState) obs, timeLocator,
						NumberUtils.intArrayFromString(viewport == null ? "800,800" : viewport));
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(image, "png", os);
				InputStream in = new ByteArrayInputStream(os.toByteArray());
				response.setContentType(MediaType.IMAGE_PNG_VALUE);
				IOUtils.copy(in, response.getOutputStream());
			}
		}

	}
}
