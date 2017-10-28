package org.integratedmodelling.klab.api.auth;

public interface IEngineIdentity extends IIdentity {

    Type type = Type.ENGINE;
    
    @Override
    INetworkSessionIdentity getParentIdentity();
}
