package org.integratedmodelling.klab.extensions.groovy.model

import org.integratedmodelling.klab.api.data.IGeometry.Dimension
import org.integratedmodelling.klab.api.observations.scale.IExtent
import org.integratedmodelling.klab.api.observations.scale.IScale
import org.integratedmodelling.klab.api.observations.scale.space.ISpace
import org.integratedmodelling.klab.api.observations.scale.time.ITime
import org.integratedmodelling.klab.common.LogicalConnector
import org.integratedmodelling.klab.engine.runtime.code.groovy.Wrapper
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
class Scale extends Wrapper<IScale> {

    Scale(String id, Binding binding) {
        super(id, binding)
    }
    
    Scale(IScale scale, Binding binding) {
        super(scale, binding)
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
        return unwrap().getSpace() == null ? null : new Space(unwrap().getSpace(), binding);
    }

    def getTime() {
        return unwrap().getTime() == null ? null : new Time(unwrap().getTime(), binding);
    }

    def getExtent(Dimension.Type type) {
        IExtent ext = unwrap().getDimension(type);
        if (ext == null) {
            return null;
        }
        if (ext instanceof ISpace) {
            return new Space(ext, binding);
        }
        if (ext instanceof ITime) {
            return new Time(ext, binding);
        }
        return null;
    }
    
    def or(Object e) {
        return new Scale(unwrap().merge(e instanceof Wrapper ? (IScale)((Wrapper)e).unwrap() : (IScale)e, LogicalConnector.UNION), binding);
    }

    def and(Object e) {
        return new Scale(unwrap().merge(e instanceof Wrapper ? (IScale)((Wrapper)e).unwrap() : (IScale)e, LogicalConnector.INTERSECTION), binding);
    }
    
    def getSize() {
        return unwrap().size();
    }

}
