package org.integratedmodelling.klab.engine.rest.controllers.engine;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Principal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.INetwork;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.rest.IObservationReference;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.geospace.visualization.Renderer;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.ObservationReference.GeometryType;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.utils.JsonUtils;
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
	 * Get the observation structure and description. This allows retrieving
	 * children at arbitrary levels. Default child level is "all children". The
	 * parent will be an observation group if the observation is part of one.
	 */
	@RequestMapping(value = API.ENGINE.OBSERVATION.VIEW.DESCRIBE_OBSERVATION, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public IObservationReference describeObservation(Principal principal, @PathVariable String observation,
			@RequestParam(required = false) Integer childLevel,
			/*@RequestParam(required = false) boolean collapseSiblings,*/ @RequestParam(required = false) String locator) {

		ISession session = EngineSessionController.getSession(principal);
		IObservation obs = session.getObservation(observation);
		ILocator loc = ITime.INITIALIZATION; // TODO parse the locator
		if (obs == null) {
			throw new IllegalArgumentException("observation " + observation + " does not exist");
		}

		IObservation parent = obs instanceof DirectObservation && ((DirectObservation) obs).getGroup() != null
				? ((DirectObservation) obs).getGroup()
				: obs.getContext();

		return Observations.INSTANCE.createArtifactDescriptor(obs, parent, loc, childLevel == null ? -1 : childLevel,
				/*collapseSiblings,*/ false);
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
		if (obs == null) {
			throw new IllegalArgumentException("observation " + observation + " does not exist");
		}

		if (!(obs instanceof IState)) {
			throw new IllegalArgumentException("cannot summarize an observation that is not a state");
		}

		return Observations.INSTANCE.getStateSummary((IState) obs, loc);
	}

	/**
	 * Get one or more siblings of an artifact, potentially with offsets and number.
	 * The response will contain the first sibling requested containing all the
	 * others as siblings. The optional childLevel parameter defines the level of
	 * the children representation in each sibling. If the sibling count is 1
	 * (default) the observation will return either the original observation or its
	 * sibling at the offset.
	 */
	@RequestMapping(value = API.ENGINE.OBSERVATION.VIEW.GET_CHILDREN_OBSERVATION, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<IObservationReference> getObservationChildren(Principal principal, @PathVariable String observation,
			@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer count,
			@RequestParam(required = false) String locator) {

		ISession session = EngineSessionController.getSession(principal);
		IObservation obs = session.getObservation(observation);
		ILocator loc = ITime.INITIALIZATION; // TODO parse locator
		if (obs == null) {
			throw new IllegalArgumentException("observation " + observation + " does not exist");
		}

		List<IObservationReference> ret = new ArrayList<>();
		IRuntimeContext context = ((Observation) obs).getRuntimeContext();

		int i = -1;
		int n = 0;
		for (IObservation child : context.getChildrenOf(obs)) {

			i++;
			if (offset != null && i < offset) {
				continue;
			}
			if (count != null && count >= 0 && n >= count) {
				break;
			}

			ret.add(Observations.INSTANCE.createArtifactDescriptor(child, obs.getContext(), loc, 0, /*false,*/ false));
			n++;
		}

		return ret;
	}

	/**
	 * Get the data for an observation in directly usable form, as values or images
	 * 
	 * TODO if format == null (currently mandatory) it should return the Protobuf
	 * data for the corresponding artifact and geometry. For completeness there
	 * should also be a GeometryType for this.
	 * 
	 * TODO should honor an 'adapter' parameter to find the specific adapter that
	 * proposed the format. At the moment it will scan the adapters and propose the
	 * observation, calling the first one that responds with the same format and
	 * assuming that only one adapter handles it.
	 * 
	 * TODO use filters or HttpMessageConverter/content negotiation for various
	 * media types - see
	 * https://stackoverflow.com/questions/3668185/how-to-define-message-converter-based-on-url-extension-in-spring-mvc
	 * http://www.baeldung.com/spring-mvc-content-negotiation-json-xml
	 */
	@RequestMapping(value = API.ENGINE.OBSERVATION.VIEW.GET_DATA_OBSERVATION, method = RequestMethod.GET)
	public void getObservationData(Principal principal, @PathVariable String observation,
			@RequestParam(required = false) String viewport, @RequestParam(required = false) String locator,
			@RequestParam ObservationReference.GeometryType format, @RequestParam(required = false) String outputFormat,
			@RequestParam(required = false) String adapter, HttpServletResponse response) throws Exception {

		ISession session = EngineSessionController.getSession(principal);
		IObservation obs = session.getObservation(observation);
		if (obs == null) {
			throw new IllegalArgumentException("observation " + observation + " does not exist");
		}

		ILocator loc = ITime.INITIALIZATION;
		if (locator != null) {
			loc = Geometry.create(locator);
		}

		boolean done = false;

		// special handling for some types: with time, these may be integrated in the
		// adapters
		if (obs instanceof IState) {

			if (format == GeometryType.RASTER
					|| (format == GeometryType.RAW && outputFormat != null && outputFormat.equals("png"))) {

				BufferedImage image = Renderer.INSTANCE.render((IState) obs, loc,
						NumberUtils.intArrayFromString(viewport == null ? "800,800" : viewport));
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(image, "png", os);
				InputStream in = new ByteArrayInputStream(os.toByteArray());
				response.setContentType(MediaType.IMAGE_PNG_VALUE);
				IOUtils.copy(in, response.getOutputStream());
				done = true;

			} else if (format == GeometryType.COLORMAP) {

				StateSummary summary = Observations.INSTANCE.getStateSummary((IState) obs, loc);
				if (summary.getColormap() != null) {
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					response.getWriter().write(JsonUtils.printAsJson(summary.getColormap()));
					response.setStatus(HttpServletResponse.SC_OK);
				}
				done = true;

			} else if (format == GeometryType.SCALAR) {

				Object value = ((IState) obs).get(loc);
				String descr = value instanceof Number
						? NumberFormat.getInstance().format(((Number) value).doubleValue())
						: (value instanceof IConcept ? Concepts.INSTANCE.getDisplayLabel(((IConcept) value))
								: (value instanceof Boolean ? ((Boolean) value ? "Present" : "Not present")
										: "No data"));

				if (obs.getObservable().getUnit() != null) {
					descr += " " + ((Unit) obs.getObservable().getUnit()).toUTFString();
				} else if (obs.getObservable().getCurrency() != null) {
					descr += " " + obs.getObservable().getCurrency();
				} else if (obs.getObservable().getRange() != null) {
					descr += " [" + obs.getObservable().getRange().getLowerBound() + " to "
							+ obs.getObservable().getRange().getUpperBound() + "]";
				}

				response.setContentType(MediaType.TEXT_PLAIN_VALUE);
				response.getWriter().write(descr);
				response.setStatus(HttpServletResponse.SC_OK);
				done = true;

			}
		}

		if (obs instanceof IConfiguration) {

			if (obs.is(INetwork.class)) {
				INetwork network = obs.as(INetwork.class);
				network.export(outputFormat, response.getOutputStream());
			}

			done = true;
		}

		if (!done && format == GeometryType.RAW) {

			// should have a format field
			File out = File.createTempFile("klab", "." + outputFormat);
			out.deleteOnExit();
			// TODO support explicit adapter
			out = Observations.INSTANCE.export(obs, loc, out, outputFormat, null, session.getMonitor());
			if (out != null) {
				response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
				try (InputStream in = new FileInputStream(out)) {
					IOUtils.copy(in, response.getOutputStream());
				}
			}

		}

	}
}
