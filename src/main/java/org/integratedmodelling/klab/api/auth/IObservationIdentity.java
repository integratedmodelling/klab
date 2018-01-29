package org.integratedmodelling.klab.api.auth;

public interface IObservationIdentity extends IRuntimeIdentity {

    Type type = Type.OBSERVATION;
    
    @Override
    IEngineSessionIdentity getParentIdentity();
}
