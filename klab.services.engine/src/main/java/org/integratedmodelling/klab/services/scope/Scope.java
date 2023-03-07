package org.integratedmodelling.klab.services.scope;

import java.util.function.Consumer;

import org.integratedmodelling.kactors.api.IKActorsBehavior.Ref;
import org.integratedmodelling.klab.api.collections.KParameters;
import org.integratedmodelling.klab.api.collections.impl.Parameters;
import org.integratedmodelling.klab.api.identities.KIdentity;
import org.integratedmodelling.klab.api.identities.KUserIdentity;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KScope;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KSessionScope;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KSessionScope.Status;
import org.integratedmodelling.klab.api.services.runtime.KMessage;
import org.integratedmodelling.klab.configuration.Services;
import org.integratedmodelling.klab.services.actors.messages.user.CreateApplication;
import org.integratedmodelling.klab.services.actors.messages.user.CreateSession;
import org.integratedmodelling.klab.services.engine.EngineService;

public class Scope implements KScope {

    private static final long serialVersionUID = 605310381727313326L;

    private KParameters<String> data = Parameters.create();
    private KUserIdentity user;
    private Ref agent;

    public Scope(KUserIdentity user) {
        this.user = user;
        ((EngineService)Services.INSTANCE.getEngine()).registerScope(this);
    }

    protected Scope(Scope parent) {
        this.user = parent.user;
    }
    
    @Override
    public KSessionScope runSession(String sessionName) {

        final SessionScope ret = new SessionScope(this);
        ret.setStatus(Status.WAITING);
        Ref sessionAgent = this.agent.ask(new CreateSession(this, sessionName), Ref.class);
        if (!sessionAgent.isEmpty()) {
            ret.setStatus(Status.STARTED);
            ret.setAgent(sessionAgent);
        } else {
            ret.setStatus(Status.ABORTED);
        }
        return ret;
    }

    @Override
    public KSessionScope runApplication(String behaviorName) {

        final SessionScope ret = new SessionScope(this);
        ret.setStatus(Status.WAITING);
        Ref sessionAgent = this.agent.ask(new CreateApplication(this, behaviorName), Ref.class);
        if (!sessionAgent.isEmpty()) {
            ret.setStatus(Status.STARTED);
            ret.setAgent(sessionAgent);
        } else {
            ret.setStatus(Status.ABORTED);
        }
        return ret;
    }

    @Override
    public KUserIdentity getUser() {
        return this.user;
    }

    @Override
    public KParameters<String> getData() {
        return this.data;
    }

    public Ref getAgent() {
        return this.agent;
    }

    public void setAgent(Ref agent) {
        this.agent = agent;
    }

    // public void setToken(String token) {
    // this.token = token;
    // }

    @Override
    public void info(Object... info) {
        // TODO Auto-generated method stub

    }

    @Override
    public void warn(Object... o) {
        // TODO Auto-generated method stub

    }

    @Override
    public void error(Object... o) {
        // TODO Auto-generated method stub

    }

    @Override
    public void debug(Object... o) {
        // TODO Auto-generated method stub

    }

    @Override
    public void send(Object... message) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addWait(int seconds) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getWaitTime() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isInterrupted() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasErrors() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public KIdentity getIdentity() {
        return getUser();
    }

    @Override
    public void post(Consumer<KMessage> handler, Object... message) {
        // TODO Auto-generated method stub
        
    }


}
