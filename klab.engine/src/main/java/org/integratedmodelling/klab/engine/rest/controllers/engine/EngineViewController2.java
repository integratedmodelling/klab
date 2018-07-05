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
import org.integratedmodelling.klab.Auth;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.components.geospace.visualization.Renderer;
import org.integratedmodelling.klab.rest.ObservationReference;
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
@CrossOrigin(origins = "http://localhost:8080")
@Secured(Roles.PUBLIC)
public class EngineViewController2 {

	@RequestMapping(value = "/provastocazzo/{session}/{observation}", method = RequestMethod.GET)
	public void getObservationData(@PathVariable String session, @PathVariable String observation,
			@RequestParam(required = false) String viewport, @RequestParam(required = false) String locator,
			@RequestParam ObservationReference.GeometryType format, HttpServletResponse response) throws Exception {

		ISession sess = Auth.INSTANCE.getIdentity(session, ISession.class);
		IObservation obs = sess.getObservation(observation);

		// TODO link to locator parameter
		ILocator timeLocator = ITime.INITIALIZATION;

		if (obs instanceof IState) {

			if (format == ObservationReference.GeometryType.RASTER) {
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
