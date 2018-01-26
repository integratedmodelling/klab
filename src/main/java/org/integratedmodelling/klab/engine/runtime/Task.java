package org.integratedmodelling.klab.engine.runtime;

import org.integratedmodelling.klab.api.auth.IContextIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.ITaskIdentity;

public class Task implements ITaskIdentity {

    public Task() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getToken() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean is(Type type) {
        return type == Type.TASK;
    }

    @Override
    public <T extends IIdentity> T getParentIdentity(Class<? extends IIdentity> type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IContextIdentity getParentIdentity() {
        // TODO Auto-generated method stub
        return null;
    }

}
