package org.integratedmodelling.klab.components.geospace.api;

import java.util.Collection;
import java.util.List;
import org.integratedmodelling.klab.api.data.utils.IPair;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.observation.Scale;

/**
 * Spatial index with k.LAB semantics (as well as more basic functions for now). Much to
 * be defined.
 * 
 * @author ferdinando.villa
 *
 */
public interface ISpatialIndex {

    void add(ISpace extent, String name);

    void add(IDirectObservation observation);

//    double distanceBetween(Scale.Locator position, String objectId);

    double distanceBetween(int offset, String objectId);

//    Collection<IPair<String, ISpace>> getNearest(Scale.Locator position, int maxResults);

    ISpace getExtent();

    /**
     * Nearest object to other object and distance. Passed object does not need to be in
     * the index.
     * 
     * @param obs
     * @return
     */
    IPair<IDirectObservation, Double> getNearestObject(IDirectObservation obs);

//    /**
//     * 
//     * @param sfs
//     * @return
//     */
//    IPair<IDirectObservation, Double> getNearestObject(Scale.Locator sfs);

    /**
     * Return all objects in the index, sorted according to increasing distance from
     * the passed one. 
     * 
     * @param obs
     * @return
     */
    List<IPair<IDirectObservation, Double>> getNear(IDirectObservation obs);

//    /**
//     * Return all objects in the index, sorted according to increasing distance from
//     * the passed locator. 
//     * 
//     * @param sfs
//     * @return
//     */
//    List<IPair<IDirectObservation, Double>> getNear(Scale.Locator sfs);

    boolean contains(String objectId);

    double distanceToNearestObjectFrom(int sfs);

//    double distanceToNearestObjectFrom(Scale.Locator sfs);

    int size();
}
