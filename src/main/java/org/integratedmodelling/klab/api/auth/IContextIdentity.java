package org.integratedmodelling.klab.api.auth;

public interface IContextIdentity extends IIdentity {

    Type type = Type.CONTEXT;
    
    @Override
    IEngineSessionIdentity getParentIdentity();
}
