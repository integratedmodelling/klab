package org.integratedmodelling.klab.engine.runtime;

import java.io.IOException;

import org.integratedmodelling.klab.api.auth.IEngineIdentity;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.engine.IEngine;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.utils.NameGenerator;

public class Session implements ISession {

    IMonitor monitor;
    String   token = NameGenerator.newName("s");
    IEngine  engine;

    public Session(IEngine engine, IEngineUserIdentity user) {
        this.monitor = ((Monitor) engine.getMonitor()).get(this);
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public boolean is(Type type) {
        return type == Type.MODEL_SESSION;
    }

    @Override
    public <T extends IIdentity> T getParentIdentity(Class<? extends IIdentity> type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IEngineIdentity getParentIdentity() {
        return engine;
    }

    @Override
    public IMonitor getMonitor() {
        return monitor;
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public ITask observe(String urn) {
        // TODO Auto-generated method stub
        return null;
    }

}
