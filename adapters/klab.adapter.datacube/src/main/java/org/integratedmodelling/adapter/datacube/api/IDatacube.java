package org.integratedmodelling.adapter.datacube.api;

import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.rest.ResourceReference.AvailabilityReference;

/**
 * Experimental; can go in the API package if it gets generic enough.
 * 
 * @author Ferd
 *
 */
public interface IDatacube {

	interface ObservationStrategy {

		/**
		 * Execute the strategy, setting the requested data into the builder. Called
		 * only when availability for this strategy returned immediate.
		 * 
		 * @param geometry
		 * @param builder
		 * @param scope
		 * @return true if successful
		 */
		boolean execute(IGeometry geometry, IKlabData.Builder builder, IContextualizationScope scope);

		/**
		 * Execute the synchronization strategy, with an expected wait time of
		 * {@link #getTimeToAvailabilitySeconds()}. If the wait time is 0, no need to
		 * call execute(). The returned descriptor may contain an updated wait time or
		 * an error notification.
		 * 
		 * @return
		 */
		AvailabilityReference buildCache();

		/**
		 * Check before any operation and call execute() as needed. If < 0, no strategy
		 * is going to succeed.
		 * 
		 * @return
		 */
		int getTimeToAvailabilitySeconds();
	}

	/**
	 * Return the sync strategy needed to cover the passed extents, with the
	 * estimated time considering both download, if needed, and aggregation.
	 * 
	 * @param time
	 * @return
	 */
	ObservationStrategy getStrategy(String variable, IGeometry geometry);

	/**
	 * Repository should be online for anything to work.
	 * 
	 * @return
	 */
	boolean isOnline();

	/**
	 * Return the proper aggregation mode for the variable identified by the passed
	 * string, which is the resource ID or part thereof.
	 * 
	 * @param variable
	 * @return
	 */
	Aggregation getAggregation(String variable);

}
