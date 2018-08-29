package org.integratedmodelling.klab.extensions.groovy.model

import java.util.Iterator

import org.integratedmodelling.klab.api.observations.scale.IExtent


class Extent {
	
	IExtent extent;
    Binding binding;

	Extent(IExtent extent, Binding binding) {
		this.extent = extent;
        this.binding = binding;
	}

    def or(Object e) {
        return extent.union(e);
    }
    
    def and(Object e) {
        return extent.intersection(e);
    }
    
    def getMultiplicity() {
        return extent.getMultiplicity();
    }
    
    def isEmpty() {
        return extent.isEmpty();
    }
    
    Iterator iterator() {
        return extent.iterator();
    }
    
    
}
