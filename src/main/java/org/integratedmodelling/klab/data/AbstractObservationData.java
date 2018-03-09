package org.integratedmodelling.klab.data;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ILocator;
import org.integratedmodelling.klab.observation.Scale;

/**
 * Simple abstract observation data class for storage components. Just implements
 * the basic iterator functions and access to simple final fields.
 * 
 * @author Ferd
 *
 */
public abstract class AbstractObservationData implements IObservationData {

    IObservable semantics;
    IGeometry   geometry;
    IMetadata   metadata = new Metadata();

    protected AbstractObservationData(IObservable semantics) {
        this.semantics = semantics;
    }
    
    @Override
    public boolean hasNext() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public IObservationData next() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IObservable getSemantics() {
        return semantics;
    }

    @Override
    public IGeometry getGeometry() {
        return geometry;
    }

    @Override
    public IMetadata getMetadata() {
        return metadata;
    }
    
    public long locatorToOffset(ILocator locator, Scale scale) {
      return 0;
    }

}
