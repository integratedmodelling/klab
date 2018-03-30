package org.integratedmodelling.klab.api.auth;

public interface INodeIdentity extends IServerIdentity {

    Type type = Type.NODE;
    
    @Override
    IPartnerIdentity getParentIdentity();
}
