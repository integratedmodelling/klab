package org.integratedmodelling.klab.api.auth;

public interface INetworkSessionIdentity extends IIdentity {

    Type type = Type.NETWORK_SESSION;
    
    @Override
    INodeUserIdentity getParentIdentity();
}
