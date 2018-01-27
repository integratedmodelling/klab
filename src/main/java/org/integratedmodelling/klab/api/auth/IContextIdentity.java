package org.integratedmodelling.klab.api.auth;

public interface IContextIdentity extends IRuntimeIdentity {

    Type type = Type.CONTEXT;
    
    @Override
    IEngineSessionIdentity getParentIdentity();
}
