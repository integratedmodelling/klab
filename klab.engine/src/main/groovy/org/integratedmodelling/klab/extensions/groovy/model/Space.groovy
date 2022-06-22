package org.integratedmodelling.klab.extensions.groovy.model
//
//import org.integratedmodelling.klab.api.observations.IObservation
//import org.integratedmodelling.klab.api.observations.scale.IExtent
//import org.integratedmodelling.klab.api.observations.scale.space.ISpace
//import org.integratedmodelling.klab.common.LogicalConnector
//import org.integratedmodelling.klab.engine.runtime.code.groovy.Wrapper
//
//class Space extends Extent<ISpace> {
//
//    Space(String id, Binding binding) {
//        super(id, binding);
//    }
//    
//    Space(ISpace space, Binding binding) {
//        super(space, binding);
//    }
//
//    //    def plus(other) {
//    //        return new Space(((org.integratedmodelling.klab.components.geospace.extents.Space)extent).joinBoundaries(coerceToExtent(other)), binding);
//    //    }
//    //
//    //    def multiply(other) {
//    //        // TODO mask
//    //        return this;
//    //    }
//
//    //    def distance(Object o) {
//    //        return ((org.integratedmodelling.klab.components.geospace.extents.Space)extent).distanceTo(coerceToExtent(o));
//    //    }
//
//
//    def or(Object e) {
//        return new Space(unwrap().merge(e instanceof Wrapper ? (IExtent)((Wrapper)e).unwrap() : (IExtent)e, LogicalConnector.UNION), binding);
//    }
//
//    def and(Object e) {
//        return new Space(unwrap().merge(e instanceof Wrapper ? (IExtent)((Wrapper)e).unwrap() : (IExtent)e, LogicalConnector.INTERSECTION), binding);
//    }
//
//    def getWidth() {
//        return unwrap().getStandardizedWidth();
//    }
//
//    def getHeight() {
//        return unwrap().getStandardizedHeight();
//    }
//
//    def getDepth() {
//        return unwrap().getStandardizedDepth();
//    }
//
//    def getArea() {
//        return unwrap().getStandardizedArea();
//    }
//
//    def getVolume() {
//        return unwrap().getStandardizedVolume();
//    }
//
//    def getLength() {
//        return unwrap().getStandardizedVolume();
//    }
//
//
//    def getShape() {
//        return unwrap().getShape();
//    }
//
//    ISpace coerceToExtent(Object o) {
//        
//        if (o instanceof Space) {
//            return ((Space)o).unwrap();
//        }
//        if (o instanceof Observation) {
//            return ((Observation)o).unwrap().getScale().getSpace();
//        }
//        if (o instanceof IObservation) {
//            return ((IObservation)o).getScale().getSpace();
//        }
//        if (o instanceof ISpace) {
//            return o;
//        }
//        return null;
//    }
//
//}
