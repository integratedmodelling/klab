package org.integratedmodelling.klab.engine.services.scope;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.engine.IContextScope;
import org.integratedmodelling.klab.api.engine.ISessionScope;

import akka.actor.ActorRef;

public class SessionScope extends Scope implements ISessionScope {

    private Status status = Status.STARTED;
    private String name;
    
    SessionScope(Scope parent) {
        super(parent);
    }
    
    @Override
    public IGeometry getGeometry() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IContextScope createContext() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
