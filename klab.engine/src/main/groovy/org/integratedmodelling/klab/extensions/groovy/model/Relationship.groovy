package org.integratedmodelling.klab.extensions.groovy.model;
//
//
//import org.integratedmodelling.klab.api.observations.IDirectObservation
//import org.integratedmodelling.klab.api.observations.IRelationship
//
//import groovy.lang.Binding;
//
//public class Relationship extends DirectObservation<IRelationship> {
//
//    public Relationship(String id, Binding binding) {
//        super(id, binding)
//    }
//    
//    public Relationship(IRelationship obs, Binding binding) {
//        super(obs, binding);
//    }
//
//    public DirectObservation getSource() {
//        return new DirectObservation(unwrap().getSource(), binding);
//    }
//    
//    public DirectObservation getTarget() {
//        return new DirectObservation(unwrap().getTarget(), binding);
//    }
//}
