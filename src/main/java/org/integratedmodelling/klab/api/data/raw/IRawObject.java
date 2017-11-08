package org.integratedmodelling.klab.api.data.raw;

import java.util.Map;

import org.integratedmodelling.kim.api.IKimMetadata;

public interface IRawObject {
    
    Map<String, IRawObservation> getObservations();

    IKimMetadata getMetadata();
}
