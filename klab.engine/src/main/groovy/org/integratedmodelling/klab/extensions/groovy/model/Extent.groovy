package org.integratedmodelling.klab.extensions.groovy.model

import org.integratedmodelling.klab.api.observations.scale.IExtent
import org.integratedmodelling.klab.common.LogicalConnector
import org.integratedmodelling.klab.engine.runtime.code.groovy.Wrapper


class Extent<T extends IExtent> extends Wrapper<T> {

    Extent(String id, Binding binding) {
        super(id, binding)
    }

    Extent(IExtent extent, Binding binding) {
        super(extent, binding)
	}
    
    def getSize() {
        return unwrap().size();
    }
    
    def isEmpty() {
        return unwrap().isEmpty();
    }
    
    Iterator iterator() {
        return unwrap().iterator();
    }
    
	def getScale() {
		return unwrap().getScaleRank();
	}
    
}
