package org.integratedmodelling.klab.api.auth;

public interface IScriptIdentity extends IIdentity {

    Type type = Type.SCRIPT;
    
    @Override
    ITaskIdentity getParentIdentity();
}
