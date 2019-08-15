package org.integratedmodelling.klab.engine.rest.controllers.engine;

import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.documentation.IReport.Encoding;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.rest.DataflowReference;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller implementing the
 * {@link org.integratedmodelling.klab.api.API.ENGINE.OBSERVATION engine context
 * API}.
 * 
 * @author ferdinando.villa
 *
 */
@RestController
@CrossOrigin(origins = "*")
@Secured(Roles.SESSION)
public class EngineContextController {

	/**
	 * Get the HTML report for the observation.
	 */
	@RequestMapping(value = API.ENGINE.OBSERVATION.REPORT_CONTEXT, method = RequestMethod.GET)
	public String getObservationData(Principal principal, @PathVariable String context, HttpServletResponse response)
			throws Exception {

		ISession session = EngineSessionController.getSession(principal);
		IObservation ctx = session.getObservation(context);
		if (ctx == null) {
			throw new IllegalArgumentException("context " + context + " does not exist");
		}
		return ((Observation) ctx).getRuntimeScope().getReport().render(Encoding.HTML);
	}

	/**
	 * Get the current dataflow for the observation.
	 */
	@RequestMapping(value = API.ENGINE.OBSERVATION.RETRIEVE_DATAFLOW, method = RequestMethod.GET)
	public DataflowReference getDataflow(Principal principal, @PathVariable String context,
			HttpServletResponse response) throws Exception {

		ISession session = EngineSessionController.getSession(principal);
		IObservation ctx = session.getObservation(context);
		if (ctx == null) {
			throw new IllegalArgumentException("context " + context + " does not exist");
		}
		return new DataflowReference(session.getId(), null,
				((Observation) ctx).getRuntimeScope().getContextualizationStrategy().getElkGraph());
	}
}
