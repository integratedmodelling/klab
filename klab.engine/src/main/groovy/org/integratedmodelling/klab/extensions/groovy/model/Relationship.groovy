package org.integratedmodelling.klab.extensions.groovy.model;


import org.integratedmodelling.klab.api.observations.IDirectObservation
import org.integratedmodelling.klab.api.observations.IRelationship

import groovy.lang.Binding;

public class Relationship extends DirectObservation {

    public Relationship(IDirectObservation obs, Binding binding) {
        super(obs, binding);
    }

    public DirectObservation getSource() {
        return new DirectObservation(((IRelationship)obs).getSource(), binding);
    }
    
    public DirectObservation getTarget() {
        return new DirectObservation(((IRelationship)obs).getSource(), binding);
    }
}
