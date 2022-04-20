package org.integratedmodelling.klab.engine.rest.controllers.engine;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.documentation.IReport.View;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.documentation.Report;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.rest.ContextualizationNotification;
import org.integratedmodelling.klab.rest.DocumentationNode;
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
	@RequestMapping(value = API.ENGINE.OBSERVATION.DOCUMENTATION_VIEW_CONTEXT, method = RequestMethod.GET)
	public @ResponseBody List<DocumentationNode> getDocumentationView(Principal principal, @PathVariable String view,
			@PathVariable String context, @RequestParam(required = false) String format) throws Exception {

		ISession session = EngineSessionController.getSession(principal);
		IObservation ctx = session.getObservation(context);
		if (format == null) {
			format = "html";
		}
		if (ctx == null) {
			throw new IllegalArgumentException("context " + context + " does not exist");
		}

		return ((Report) ctx.getScope().getReport()).getView(View.valueOf(view.toUpperCase()), format);
	}

//	/**
//	 * Get the current dataflow graph for the observation.
//	 * @deprecated use the public API export function
//	 */
//	@RequestMapping(value = API.ENGINE.OBSERVATION.RETRIEVE_DATAFLOW, method = RequestMethod.GET)
//	public DataflowReference getDataflow(Principal principal, @PathVariable String context,
//			@RequestParam(defaultValue = "elk") String format, HttpServletResponse response) throws Exception {
//
//		ISession session = EngineSessionController.getSession(principal);
//		IObservation ctx = session.getObservation(context);
//		if (ctx == null) {
//			throw new IllegalArgumentException("context " + context + " does not exist");
//		}
//		if ("elk".equals(format)) {
//			return new DataflowReference(session.getId(), null,
//				((Observation) ctx).getScope().getElkGraph());
//		} else if ("kdl".equals(format)) {
//			return new DataflowReference(session.getId(),
//					((Observation) ctx).getScope().getKdl(), null);
//		}
//		
//		throw new KlabIllegalArgumentException("dataflow: invalid format " + format);
//			
//	}
}
