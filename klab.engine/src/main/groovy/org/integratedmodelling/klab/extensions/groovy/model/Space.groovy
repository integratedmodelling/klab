package org.integratedmodelling.klab.extensions.groovy.model

import org.integratedmodelling.klab.api.observations.IObservation
import org.integratedmodelling.klab.api.observations.scale.space.ISpace

class Space extends Extent {

	Space(ISpace space, Binding binding) {
        super(space, binding);
	}
    
//    def plus(other) {
//        return new Space(((org.integratedmodelling.klab.components.geospace.extents.Space)extent).joinBoundaries(coerceToExtent(other)), binding);
//    }
//    
//    def multiply(other) {
//        // TODO mask
//        return this;
//    }
    
//    def distance(Object o) {
//        return ((org.integratedmodelling.klab.components.geospace.extents.Space)extent).distanceTo(coerceToExtent(o));
//    }

	def getWidth() {
		return ((ISpace)extent).getStandardizedWidth();
	}
	
	def getHeight() {
		return ((ISpace)extent).getStandardizedHeight();	
	}

	def getDepth() {
		return ((ISpace)extent).getStandardizedDepth();
	}

	def getArea() {
		return ((ISpace)extent).getStandardizedArea();
	}

	def getVolume() {
		return ((ISpace)extent).getStandardizedVolume();
	}
	
	def getLength() {
		return ((ISpace)extent).getStandardizedVolume();
	}
	
		
	def getShape() {
		return ((org.integratedmodelling.klab.components.geospace.extents.Space)extent).getShape();
	}
	    
    ISpace coerceToExtent(Object o) {
        if (o instanceof org.integratedmodelling.klab.components.geospace.extents.Space) {
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
