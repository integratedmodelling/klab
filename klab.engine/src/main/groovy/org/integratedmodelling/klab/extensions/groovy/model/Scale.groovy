package org.integratedmodelling.klab.extensions.groovy.model

import org.integratedmodelling.klab.api.observations.scale.IExtent
import org.integratedmodelling.klab.api.observations.scale.IScale
import org.integratedmodelling.klab.api.observations.scale.space.ISpace
import org.integratedmodelling.klab.api.observations.scale.time.ITime
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException

/**
 * Works as a looper for the index over the current transition.
 * Simply do 
 * 
 * <pre>
 * for (n in scale) { 
 *      value = elevation[n\]; 
 * }
 * </pre>
 * or use scale.each { } semantics.
 * 
 * @author Ferd
 *
 */
class Scale {

    IScale scale;
    Binding binding;

    Scale(IScale scale, Binding binding) {
        this.scale = scale;
        this.binding = binding;
    }
    
    Iterator iterator() {

		throw new KlabUnimplementedException("Implement me please! groovy:Scale:Iterator")		
//        ITransition t = null;
//        if (binding.hasVariable('_transition')) {
//            t = (ITransition)binding.getVariable('_transition');
//        }
//        return this.scale.getIndex(t).iterator();
    }
    
    def getSpace() {
        return scale.getSpace() == null ? null : new Space(scale.getSpace(), binding);
    }

    def getTime() {
        return scale.getTime() == null ? null : new Time(scale.getTime(), binding);
    }

    def getExtent(Concept o) {
        IExtent ext = scale.getExtent(o.concept);
        if (ext == null) {
            return null;
        }
        if (ext instanceof ISpace) {
            return new Space(ext, binding);
        }
        if (ext instanceof ITime) {
            return new Time(ext, binding);
        }
        return new Extent(ext, binding);
    }
    
    def or(Object e) {
        return scale.union(e);
    }
    
    def and(Object e) {
        return scale.intersection(e);
    }
    
    def getMultiplicity() {
        return scale.getMultiplicity();
    }

}
