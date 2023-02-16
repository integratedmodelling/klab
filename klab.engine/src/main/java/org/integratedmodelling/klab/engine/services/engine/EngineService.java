package org.integratedmodelling.klab.engine.services.engine;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.auth.IEngineIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.engine.IEngineService;
import org.integratedmodelling.klab.api.engine.IScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.runtime.rest.IClient;
import org.integratedmodelling.klab.engine.services.engine.reasoner.ReasonerDefaultService;
import org.integratedmodelling.klab.engine.services.engine.resolver.ResolverDefaultService;
import org.integratedmodelling.klab.engine.services.engine.resources.ResourceDefaultService;
import org.integratedmodelling.klab.engine.services.engine.runtime.RuntimeDefaultService;
import org.integratedmodelling.klab.engine.services.scope.Scope;
import org.integratedmodelling.klab.engine.services.scope.actors.Supervisor;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.AskPattern;

/**
 * Reference implementation for the new modular engine. Should eventually allow
 * substituting external RPC services for the default ones, based on
 * configuration and a dedicated API.
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
	private ActorSystem<KlabMessage> supervisorAgent;
	private Map<String, Scope> userScopes = Collections.synchronizedMap(new HashMap<>());
	private Monitor monitor = new Monitor(this);
	private String name = "modular-klab-engine";

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
		this.supervisorAgent = ActorSystem.create(Supervisor.create(), "klab");
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
			String agentName = user.getUsername().replace('.', '_');
	        CompletionStage<Supervisor.UserCreated> sessionFuture = AskPattern.ask(this.supervisorAgent,
	                replyTo -> new Supervisor.CreateUser(agentName, scope, replyTo), Duration.ofSeconds(5),
	                this.supervisorAgent.scheduler());

	        sessionFuture.whenComplete((reply, failure) -> {
	            if (reply != null) {
	                scope.setAgent(reply.userAgent);
	                scope.setToken(agentName);
	            }
	        });

	        sessionFuture.toCompletableFuture().join();
			userScopes.put(user.getUsername(), ret);
		}
		return ret;
	}

	public void registerScope(Scope scope) {
		userScopes.put(scope.getToken(), scope);
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

	public ActorSystem<KlabMessage> getActorSystem() {
		return this.supervisorAgent;
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

}
