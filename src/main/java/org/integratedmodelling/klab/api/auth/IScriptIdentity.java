package org.integratedmodelling.klab.api.auth;

public interface IScriptIdentity extends IRuntimeIdentity {

    Type type = Type.SCRIPT;
    
    @Override
    IEngineSessionIdentity getParentIdentity();
}
