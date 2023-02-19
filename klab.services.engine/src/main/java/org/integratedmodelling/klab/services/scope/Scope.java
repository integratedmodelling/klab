package org.integratedmodelling.klab.services.scope;

import java.time.Duration;
import java.util.function.Consumer;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.engine.IEngineService.Reasoner;
import org.integratedmodelling.klab.api.engine.IEngineService.Resolver;
import org.integratedmodelling.klab.api.engine.IEngineService.ResourceManager;
import org.integratedmodelling.klab.api.engine.IEngineService.Runtime;
import org.integratedmodelling.klab.api.engine.IScope;
import org.integratedmodelling.klab.api.engine.ISessionScope;
import org.integratedmodelling.klab.api.engine.ISessionScope.Status;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.services.actors.messages.user.CreateApplication;
import org.integratedmodelling.klab.services.actors.messages.user.CreateSession;
import org.integratedmodelling.klab.services.engine.EngineService;
import org.integratedmodelling.klab.utils.Parameters;

import io.reacted.core.reactorsystem.ReActorRef;

public class Scope implements IScope {

    private static final long serialVersionUID = 605310381727313326L;

    private Parameters<String> data = Parameters.create();
    private IUserIdentity user;
    private Reasoner reasonerService;
    private ResourceManager resourceService;
    private Resolver resolverService;
    private Runtime runtimeService;
    private ReActorRef agent;

    public Scope(IUserIdentity user, Reasoner reasonerService, ResourceManager resourceService, Resolver resolverService,
            Runtime runtimeService) {
        this.user = user;
        this.reasonerService = reasonerService;
        this.resourceService = resourceService;
        this.resolverService = resolverService;
        this.runtimeService = runtimeService;
        EngineService.INSTANCE.registerScope(this);
    }

    protected Scope(Scope parent) {
        this.user = parent.user;
        this.reasonerService = parent.reasonerService;
        this.resourceService = parent.resourceService;
        this.resolverService = parent.resolverService;
        this.runtimeService = parent.runtimeService;
    }

    @Override
    public Reasoner getReasoner() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Resolver getResolver() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Runtime getRuntime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResourceManager getResources() {
        // TODO Auto-generated method stub
        return null;
    }

    // @Override
    // public String getToken() {
    // return this.token;
    // }

    @Override
    public ISessionScope runSession(String sessionName) {

        final SessionScope ret = new SessionScope(this);
        ret.setStatus(Status.WAITING);
        this.agent.ask(new CreateSession(this, sessionName), ReActorRef.class, Duration.ofSeconds(3), sessionName)
                .whenComplete((reply, failure) -> {
                    if (reply instanceof ReActorRef) {
                        ret.setAgent(reply);
                        ret.setStatus(Status.STARTED);
                    } else {
                        ret.setStatus(Status.ABORTED);
                    }
                }).toCompletableFuture().join();

        return ret;
    }

    @Override
    public ISessionScope runApplication(String behaviorName) {

        final SessionScope ret = new SessionScope(this);
        ret.setStatus(Status.WAITING);
        this.agent.ask(new CreateApplication(this, behaviorName), ReActorRef.class, Duration.ofSeconds(3), behaviorName)
                .whenComplete((reply, failure) -> {
                    if (reply instanceof ReActorRef) {
                        ret.setAgent(reply);
                        ret.setStatus(Status.STARTED);
                    } else {
                        ret.setStatus(Status.ABORTED);
                    }
                }).toCompletableFuture().join();

        return ret;
    }

    @Override
    public IUserIdentity getUser() {
        return this.user;
    }

    @Override
    public IParameters<String> getData() {
        return this.data;
    }

    public ReActorRef getAgent() {
        return this.agent;
    }

    public void setAgent(ReActorRef agent) {
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
    public void post(Consumer<IMessage> handler, Object... message) {
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

}
