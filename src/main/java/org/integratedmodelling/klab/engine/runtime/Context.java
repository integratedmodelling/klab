package org.integratedmodelling.klab.engine.runtime;

import org.integratedmodelling.klab.api.auth.IContextIdentity;
import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class Context implements IContextIdentity {

    public Context() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getToken() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean is(Type type) {
        return type == Type.CONTEXT;
    }

    @Override
    public <T extends IIdentity> T getParentIdentity(Class<? extends IIdentity> type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IEngineSessionIdentity getParentIdentity() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IMonitor getMonitor() {
        // TODO Auto-generated method stub
        return null;
    }

}
