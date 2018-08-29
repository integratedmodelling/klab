package org.integratedmodelling.klab.extensions.groovy.model;

import org.codehaus.groovy.runtime.InvokerHelper;
import org.integratedmodelling.klab.api.observations.IDirectObservation
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.extensions.groovy.Extentual


/**
 * Proxy for a trajectory that wraps its results.
 * @author ferdinando.villa
 *
 */
public class Trajectory implements Extentual {

//    org.integratedmodelling.engine.geospace.layers.Trajectory trajectory;
    DirectObservation context;
    
    public Trajectory(IDirectObservation o1, IDirectObservation o2, DirectObservation context) {
        this.context = context;
//        this.trajectory = new org.integratedmodelling.engine.geospace.layers.Trajectory(o1, o2);
    }
    
//    public Trajectory(org.integratedmodelling.engine.geospace.layers.Trajectory trajectory, DirectObservation context) {
//        this.trajectory = trajectory;
//        this.context = context;    
//    }
    
    def getDirections() {
        List<Link> ret = new ArrayList<>();
//        for (SpatialLink o : trajectory.getDirections()) {
//            ret.add(new Link(link: o, observation: (o.getObservation() == null ? null : new DirectObservation(o.getObservation(), context.binding))));
//        }
        return ret;
    }
    
    def invokeMethod(String name, Object args) {
        try {
            return super.invokeMethod(name, args);
        } catch (MissingMethodException e) {
            return InvokerHelper.invokeMethod(trajectory, name, args);
        }
    }
    
    def getJoinPoint() {
        // TODO point where we enter the "endorsed" way (with subjects)
    }
    
    def getLeavePoint() {
        // TODO point where we leave the "endorsed" way (with subjects)
    }
    
    /*
     * link in a trajectory: proxies the subject if any, and reports length and
     * empty (no subject) status.
     */
    public static class Link {
        
//        SpatialLink link;
        Observation observation;
        
        def getLength() {
            return link.getLength();
        }
        
        def getSubject() {
            return observation;
        }
        
        def isEmpty() {
            return observation == null;
        }
        
        def invokeMethod(String name, Object args) {
            try {
                return super.invokeMethod(name, args);
            } catch (MissingMethodException e) {
                if (observation != null) {
                    return InvokerHelper.invokeMethod(observation, name, args);
                } else {
                    throw e;
                }
            }
        }
    }

    public Collection<IExtent> getExtents() {
        return Collections.singleton(this.trajectory.asExtent());
    }
    
}
