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
