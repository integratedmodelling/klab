package org.integratedmodelling.klab.api.auth;

public interface IEngineIdentity extends IServerIdentity {

    Type type = Type.ENGINE;
    
    @Override
    INetworkSessionIdentity getParentIdentity();
}
