package org.integratedmodelling.klab.components.geospace.extents;

import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.scale.AbstractExtent;

public abstract class AbstractSpatialAbstractExtent extends AbstractExtent implements ISpace {

    @Override
    public Object getProperty(String propertyName) {
        switch(propertyName) {
        case "width":
            return getStandardizedWidth();
        case "height":
            return getStandardizedHeight();
        case "length":
            return getStandardizedLength();
        case "area":
            return getStandardizedArea();
        }
        return super.getProperty(propertyName);
    }
}
