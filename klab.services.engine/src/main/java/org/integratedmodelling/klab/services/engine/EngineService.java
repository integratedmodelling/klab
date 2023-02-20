package org.integratedmodelling.klab.services.engine;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.xtext.testing.IInjectorProvider;
import org.integratedmodelling.kactors.model.KActors;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.api.auth.IEngineIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.engine.IEngineService;
import org.integratedmodelling.klab.api.engine.IScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.runtime.rest.IClient;
import org.integratedmodelling.klab.services.actors.KAgent.KAgentRef;
import org.integratedmodelling.klab.services.actors.UserAgent;
import org.integratedmodelling.klab.services.engine.reasoner.ReasonerDefaultService;
import org.integratedmodelling.klab.services.engine.resolver.ResolverDefaultService;
import org.integratedmodelling.klab.services.engine.resources.ResourceDefaultService;
import org.integratedmodelling.klab.services.engine.runtime.RuntimeDefaultService;
import org.integratedmodelling.klab.services.scope.Scope;
import org.integratedmodelling.klab.utils.xtext.KactorsInjectorProvider;
import org.integratedmodelling.klab.utils.xtext.KimInjectorProvider;

import com.google.inject.Injector;

import io.reacted.core.config.reactorsystem.ReActorSystemConfig;
import io.reacted.core.reactorsystem.ReActorSystem;

/**
 * Reference implementation for the new modular engine. Should eventually allow substituting
 * external RPC services for the default ones, based on configuration and a dedicated API.
 * 
 * @author Ferd
 *
 */
public enum EngineService implements IEngineService, IEngineIdentity {

    INSTANCE;

    private Reasoner reasonerService;
    private ResourceManager resourceService;
    private Resolver resolverService;
    private Runtime runtimeService;
    private Map<String, Scope> userScopes = Collections.synchronizedMap(new HashMap<>());
    private Monitor monitor = new Monitor(this);
    private String name = "modular-klab-engine";
    private ReActorSystem actorSystem;

    protected Reasoner createReasonerService() {
        return new ReasonerDefaultService();
    }

    protected ResourceManager createResourceService() {
        return new ResourceDefaultService();
    }

    protected Resolver createResolverService() {
        return new ResolverDefaultService();
    }

    protected Runtime createRuntimeService() {
        return new RuntimeDefaultService();
    }

    public void boot() {

        initializeLanguageServices();

        this.actorSystem = new ReActorSystem(ReActorSystemConfig.newBuilder().setReactorSystemName("klab").build())
                .initReActorSystem();

        this.reasonerService = createReasonerService();
        this.resourceService = createResourceService();
        this.resolverService = createResolverService();
        this.runtimeService = createRuntimeService();

    }

    @Override
    public IScope login(IUserIdentity user) {

        Scope ret = userScopes.get(user.getUsername());
        if (ret == null) {
            ret = new Scope(user, reasonerService, resourceService, resolverService, runtimeService);
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
        INSTANCE.boot();
        return INSTANCE;
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

    public ReActorSystem getActors() {
        return this.actorSystem;
    }

    @Override
    public IClient getClient() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean stop() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public IMonitor getMonitor() {
        return monitor;
    }

    @Override
    public IParameters<String> getState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Type getIdentityType() {
        return Type.ENGINE;
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IIdentity getParentIdentity() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean is(Type type) {
        return getIdentityType() == type;
    }

    @Override
    public <T extends IIdentity> T getParentIdentity(Class<T> type) {
        // TODO Auto-generated method stub
        return null;
    }

    public Object getSystemRef() {
        // TODO Auto-generated method stub
        return null;
    }

    private void initializeLanguageServices() {

        /*
         * set up access to the k.IM grammar
         */
        IInjectorProvider injectorProvider = new KimInjectorProvider();
        Injector injector = injectorProvider.getInjector();
        if (injector != null) {
            Kim.INSTANCE.setup(injector);
        }

        /*
         * ...and k.Actors
         */
        IInjectorProvider kActorsInjectorProvider = new KactorsInjectorProvider();
        Injector kActorsInjector = kActorsInjectorProvider.getInjector();
        if (kActorsInjector != null) {
            KActors.INSTANCE.setup(kActorsInjector);
        }

    }

}
