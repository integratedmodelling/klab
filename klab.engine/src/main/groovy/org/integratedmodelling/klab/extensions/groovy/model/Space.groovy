package org.integratedmodelling.klab.extensions.groovy.model

import org.integratedmodelling.klab.api.observations.IObservation
import org.integratedmodelling.klab.api.observations.scale.space.ISpace

class Space extends Extent {

	Space(ISpace space, Binding binding) {
        super(space, binding);
	}
    
    def getGrid() {
        return new Grid(((Space)extent).getGrid(), binding);
    }
	
    def plus(other) {
        return new Space(((Space)extent).joinBoundaries(coerceToExtent(other)), binding);
    }
    
    def multiply(other) {
        // TODO mask
        return this;
    }
    
    def distance(Object o) {
        return ((Space)extent).distanceTo(coerceToExtent(o));
    }

	def getArea() {
		return ((Space)extent).getArea();
	}
	
	def getShape() {
		return ((Space)extent).getShape();
	}
	    
    ISpace coerceToExtent(Object o) {
        if (o instanceof Space) {
            return ((Space)o).extent;
        }
        if (o instanceof Observation) {
            return ((Observation)o).obs.getScale().getSpace();
        }
        if (o instanceof IObservation) {
            return ((IObservation)o).getScale().getSpace();
        }
        if (o instanceof ISpace) {
            return o;
        }
        return null;
    }

}
