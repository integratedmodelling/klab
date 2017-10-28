package org.integratedmodelling.klab.api.auth;

public interface INodeIdentity extends IIdentity {

    Type type = Type.NODE;
    
    @Override
    IPartnerIdentity getParentIdentity();
}
