package org.integratedmodelling.klab.node.controllers;

import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.engine.extensions.Component;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.stats.StatsComponent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Anonymous, lightweight observation statistics endpoints for web sites or
 * quick monitoring. Endpoints are public by virtue of the /public prefix.
 * 
 * @author Ferd
 *
 */
@RestController
@CrossOrigin(origins = "*")
public class PublicStatController {

	@GetMapping(API.STATS.GEOJSON_EVENTS)
	public String getGeoJsonObservations(@RequestParam(required = false) String span,
			@RequestParam(required = false) boolean errors, @RequestParam(required = false) boolean polygons) {
		Component stc = Extensions.INSTANCE.getComponent(StatsComponent.ID);
		if (stc == null || !stc.isActive()) {
			throw new KlabIllegalStateException("statistics component is not configured or not installed");
		}
		StatsComponent stats = stc.getImplementation(StatsComponent.class);
		return stats.getDatabase().getGeoJSONObservations(span, polygons, errors);
	}

}
