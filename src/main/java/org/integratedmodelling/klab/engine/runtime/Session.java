package org.integratedmodelling.klab.engine.runtime;

import org.integratedmodelling.klab.api.auth.IEngineIdentity;
import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;

public class Session implements IEngineSessionIdentity {

    public Session() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getToken() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean is(Type type) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <T extends IIdentity> T getParentIdentity(Class<? extends IIdentity> type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IEngineIdentity getParentIdentity() {
        // TODO Auto-generated method stub
        return null;
    }

}
