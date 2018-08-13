package org.integratedmodelling.klab.components.geospace.api;

import java.util.List;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.data.utils.IPair;
import org.integratedmodelling.klab.api.observations.IDirectObservation;

/**
 * Spatial index with k.LAB semantics (as well as more basic functions for now).
 * Much to be defined.
 * 
 * @author ferdinando.villa
 *
 */
public interface ISpatialIndex {

	void add(IDirectObservation observation);

	/**
	 * Nearest object to other object and distance. Passed object does not need to
	 * be in the index.
	 * 
	 * @param obs
	 * @return the nearest object and the distance
	 */
	IPair<IDirectObservation, Double> getNearestObject(IDirectObservation obs);

	/**
	 * Return all objects in the index, sorted according to increasing distance from
	 * the passed one. Find nearest N bounding boxes, then sort according to length
	 * of projection to nearest point in perimeter.
	 * 
	 * @param obs
	 * @return maxResults objects in order of increasing distance
	 */
	List<IPair<IDirectObservation, Double>> getNear(IDirectObservation obs, int maxResults);

	/**
	 * 
	 * @param locator
	 * @param unit
	 * @return
	 */
	Object distanceToNearestObjectFrom(ILocator locator, IUnit unit);

	/**
	 * 
	 * @param observation
	 * @param unit
	 * @return
	 */
	double distanceToNearestObjectFrom(IDirectObservation observation, IUnit unit);

	/**
	 * 
	 * @return
	 */
	int size();

}
