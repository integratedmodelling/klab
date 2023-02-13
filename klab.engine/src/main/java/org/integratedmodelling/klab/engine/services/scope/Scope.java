package org.integratedmodelling.klab.engine.services.scope;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.engine.IEngineService;
import org.integratedmodelling.klab.api.engine.IEngineService.Reasoner;
import org.integratedmodelling.klab.api.engine.IEngineService.Resolver;
import org.integratedmodelling.klab.api.engine.IEngineService.ResourceManager;
import org.integratedmodelling.klab.api.engine.IEngineService.Runtime;
import org.integratedmodelling.klab.api.engine.IScope;
import org.integratedmodelling.klab.api.engine.ISessionScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.services.engine.EngineService;
import org.integratedmodelling.klab.utils.Parameters;

import akka.actor.typed.ActorRef;

public class Scope implements IScope {

    private Parameters<String> data = Parameters.create();
    private IEngineUserIdentity user;
    private EngineService engineService;
    private Reasoner reasonerService;
    private ResourceManager resourceService;
    private Resolver resolverService;
    private Runtime runtimeService;
    private ActorRef<KlabMessage> userAgent;
    
    public Scope(IEngineUserIdentity user, ActorRef<KlabMessage> userAgent, EngineService engineService, Reasoner reasonerService, ResourceManager resourceService,
            Resolver resolverService, Runtime runtimeService) {
        this.user = user;
        this.userAgent = userAgent;
        this.engineService = engineService;
        this.reasonerService = reasonerService;
        this.resourceService = resourceService;
        this.resolverService = resolverService;
        this.runtimeService = runtimeService;
    }

    public Scope(Scope parent) {
        this.user = parent.user;
        this.engineService = parent.engineService;
        this.reasonerService = parent.reasonerService;
        this.resourceService = parent.resourceService;
        this.resolverService = parent.resolverService;
        this.runtimeService = parent.runtimeService;
        this.userAgent = parent.userAgent;
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

    @Override
    public IEngineService getEngine() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getToken() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ISessionScope run(String sessionName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ISessionScope run(IBehavior behavior) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IEngineUserIdentity getUser() {
        return this.user;
    }

    @Override
    public IParameters<String> getData() {
        return this.data;
    }

}
