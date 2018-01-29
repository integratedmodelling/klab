package org.integratedmodelling.klab.api.auth;

public interface ITaskIdentity extends IRuntimeIdentity {

    Type type = Type.TASK;
    
    @Override
    IObservationIdentity getParentIdentity();
}
