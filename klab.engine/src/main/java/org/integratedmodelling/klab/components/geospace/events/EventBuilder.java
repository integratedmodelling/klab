package org.integratedmodelling.klab.components.geospace.events;

import java.util.Map;

import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;

/**
 * Takes a data resource with a T1S2 geometry and builds an event resource using
 * an expression to select areas that "have" the event and configurable criteria
 * to split and merge events.
 * 
 * In addition to threshold may include split criteria - e.g. standard deviation of values that breaks contiguity. Or we 
 * can use clustering.
 * 
 * TODO
 * 
 * 1. establish the source layers (can use storage w/grid, so that input can be more flexible) and selection criteria.
 *    if there is a previous observation/database, find out all the events that end "active" at the time we start from, and
 *    find the earliest start among them. Then reconstruct based on that time instead of the time at the call.
 *    
 * 2. For each layer:
 * 2.1   use JAI vectorizer and tag each shape in a layer with a progressive ID and rasterize the ID over a new layer.
 * 2.2   if there is a previous layer, connect the shapes that overlap. Shapes that don't (or end of observations) causes
 *       event storage.
 *       
 * To solve: event split/merge
 * Additional data to record: statistics of source variable, duration, total footprint and raster intensity per event.
 * New and ongoing event count at specified temporal resolution
 * 
 * @author ferdinando.villa
 *
 */
public class EventBuilder {

	IResource sourceData;
	IKlabData.Builder builder;

	public EventBuilder(String dataUrn, IKlabData.Builder builder, Map<String, Object> configuration) {
		this.sourceData = Resources.INSTANCE.resolveResource(dataUrn);
		if (this.sourceData == null || this.sourceData.hasErrors() || !this.sourceData.getGeometry().is("T1S2")) {
			throw new IllegalArgumentException(
					"EventBuilder: cannot use the requested resource: must exist with T1S2 geometry and without errors");
		}
	}

	/**
	 * Discards any previously built state.
	 */
	void reset() {

	}

	/**
	 * Potentially VERY long-running. Keeps tabs on the execution so that it can be
	 * restarted from the last commit point.
	 */
	void build() {

	}

}
