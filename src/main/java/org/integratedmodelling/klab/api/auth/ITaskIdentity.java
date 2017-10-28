package org.integratedmodelling.klab.api.auth;

public interface ITaskIdentity extends IIdentity {

    Type type = Type.TASK;
    
    @Override
    IContextIdentity getParentIdentity();
}
