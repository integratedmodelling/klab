package org.integratedmodelling.klab.engine.runtime;

import java.io.IOException;

import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.IEngineIdentity;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.NameGenerator;

public class Session implements ISession {

    Monitor monitor;
    String   token = NameGenerator.newName("s");
    Engine  engine;

    public Session(Engine engine, IEngineUserIdentity user) {
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
    public Monitor getMonitor() {
        return monitor;
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public ITask observe(String urn) throws KlabException {
        // urn must specify observer
        IKimObject object = Resources.INSTANCE.getModelObject(urn);
        if (!(object instanceof IObserver)) {
            throw new KlabContextualizationException("URN " + urn + " does not specify an observation");
        }
        Context context = createContext();
        return context.createTask((IObserver)object);
    }

    private Context createContext() {
        Context ret = new Context(this);
        // TODO bookkeeping, storage
        return ret;
    }

}
