package org.integratedmodelling.klab.api.data.raw;

import java.util.Map;

import org.integratedmodelling.kim.api.IMetadata;

public interface IRawObject extends Map<String, IRawObservation> {
    
    IMetadata getMetadata();
}
