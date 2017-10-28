package org.integratedmodelling.klab.api.auth;

public interface IEngineSessionIdentity extends IIdentity {

    Type type = Type.MODEL_SESSION;
    
    @Override
    IEngineIdentity getParentIdentity();
}
