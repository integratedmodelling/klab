package org.integratedmodelling.klab.services.engine;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.collections.KParameters;
import org.integratedmodelling.klab.api.identities.KEngineIdentity;
import org.integratedmodelling.klab.api.identities.KIdentity;
import org.integratedmodelling.klab.api.identities.KUserIdentity;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KScope;
import org.integratedmodelling.klab.api.services.KEngine;
import org.integratedmodelling.klab.api.services.runtime.KChannel;
import org.integratedmodelling.klab.configuration.Services;
import org.integratedmodelling.klab.runtime.Monitor;
import org.integratedmodelling.klab.services.actors.KAgent.KAgentRef;
import org.integratedmodelling.klab.services.actors.UserAgent;
import org.integratedmodelling.klab.services.scope.Scope;
import org.springframework.stereotype.Service;

import io.reacted.core.config.reactorsystem.ReActorSystemConfig;
import io.reacted.core.reactorsystem.ReActorSystem;

/**
 * Reference implementation for the new modular engine. Should eventually allow substituting
 * external RPC services for the default ones, based on configuration and a dedicated API.
 * 
 * @author Ferd
 *
 */
@Service
public class EngineService implements KEngine, KEngineIdentity {

    private Map<String, Scope> userScopes = Collections.synchronizedMap(new HashMap<>());
    private Monitor monitor = new Monitor(this);
    private String name = "modular-klab-engine";
    private ReActorSystem actorSystem;

    public void boot() {

        Services.INSTANCE.setEngine(this);

        /*
         * boot the actor system
         */
        // Actors.INSTANCE.setup();

        this.actorSystem = new ReActorSystem(ReActorSystemConfig.newBuilder().setReactorSystemName("klab").build())
                .initReActorSystem();

        // this.reasonerService = createReasonerService();
        // this.resourceService = createResourceService();
        // this.resolverService = createResolverService();
        // this.runtimeService = createRuntimeService();

    }

    @Override
    public KScope login(KUserIdentity user) {

        Scope ret = userScopes.get(user.getUsername());
        if (ret == null) {
            ret = new Scope(user);
            final Scope scope = ret;
            String agentName = user.getUsername();
            actorSystem.spawn(new UserAgent(agentName)).ifSuccess((t) -> scope.setAgent(KAgentRef.get(t))).orElseSneakyThrow();
            userScopes.put(user.getUsername(), ret);
        }
        return ret;
    }

    public void registerScope(Scope scope) {
        userScopes.put(scope.getUser().getUsername(), scope);
    }

    public void deregisterScope(String token) {
        userScopes.remove(token);
    }

    public static EngineService start() {
        EngineService ret = new EngineService();
        ret.boot();
        return ret;
    }

    //
    // @Override
    // public Date getBootTime() {
    // // TODO Auto-generated method stub
    // return null;
    // }
    //
    // @Override
    // public Collection<String> getUrls() {
    // // TODO Auto-generated method stub
    // return null;
    // }
    //
    // @Override
    // public boolean isOnline() {
    // // TODO Auto-generated method stub
    // return false;
    // }

    public ReActorSystem getActors() {
        return this.actorSystem;
    }

    // @Override
    // public IClient getClient() {
    // // TODO Auto-generated method stub
    // return null;
    // }
    //
    // @Override
    // public boolean stop() {
    // // TODO Auto-generated method stub
    // return false;
    // }
    //
    // @Override
    // public IMonitor getMonitor() {
    // return monitor;
    // }
    //
    // @Override
    // public IParameters<String> getState() {
    // // TODO Auto-generated method stub
    // return null;
    // }
    //
    // @Override
    // public Type getIdentityType() {
    // return Type.ENGINE;
    // }
    //
    // @Override
    // public String getId() {
    // // TODO Auto-generated method stub
    // return null;
    // }
    //
    // @Override
    // public IIdentity getParentIdentity() {
    // // TODO Auto-generated method stub
    // return null;
    // }
    //
    // @Override
    // public boolean is(Type type) {
    // return getIdentityType() == type;
    // }
    //
    // @Override
    // public <T extends IIdentity> T getParentIdentity(Class<T> type) {
    // // TODO Auto-generated method stub
    // return null;
    // }

    public Object getSystemRef() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getBootTime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<String> getUrls() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isOnline() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean stop() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public KChannel getMonitor() {
        return monitor;
    }

    @Override
    public KParameters<String> getState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Type getIdentityType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KIdentity getParentIdentity() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean is(Type type) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <T extends KIdentity> T getParentIdentity(Class<T> type) {
        // TODO Auto-generated method stub
        return null;
    }

}
