package org.integratedmodelling.klab.engine.runtime;

import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IObservationIdentity;
import org.integratedmodelling.klab.engine.Engine.Monitor;

public class Context implements IObservationIdentity {

    Monitor monitor;

    Context(Session session) {
        this.monitor = session.getMonitor().get(this);
    }

    @Override
    public String getToken() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean is(Type type) {
        return type == Type.OBSERVATION;
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
    public Monitor getMonitor() {
        // TODO Auto-generated method stub
        return monitor;
    }

}
