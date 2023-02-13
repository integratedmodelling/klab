package org.integratedmodelling.klab.api.engine;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.engine.IEngineService.Reasoner;
import org.integratedmodelling.klab.api.engine.IEngineService.Resolver;
import org.integratedmodelling.klab.api.engine.IEngineService.ResourceManager;
import org.integratedmodelling.klab.api.engine.IEngineService.Runtime;

public interface IScope {

    enum Type {
        USER, // root-level scope
        SCRIPT, // session-level scope
        API, // session for the REST API through a client
        APPLICATION, // session for an application, including the Explorer
        SESSION, // raw session for direct use within Java code
        CONTEXT // context, on which observe() can be called
    }

    /**
     * Return the reasoner service assigned to this scope.
     * 
     * @return
     */
    Reasoner getReasoner();

    /**
     * Return the resolver service assigned to this scope.
     * 
     * @return
     */
    Resolver getResolver();

    /**
     * Return the runtime service assigned to this scope.
     * 
     * @return
     */
    Runtime getRuntime();

    /**
     * Return the resources service assigned to this scope.
     * 
     * @return
     */
    ResourceManager getResources();

    /**
     * Return the engine service that generated this scope.
     * 
     * @return
     */
    IEngineService getEngine();

    /**
     * The scope is created for an authenticated user by the engine.
     * 
     * @return
     */
    IUserIdentity getUser();

    /**
     * The token is needed to communicate with the scope. It can represent the user, an application
     * (or raw session), or a context; the hierarchial structure of these is reflected in the token,
     * which always starts with the user token possibly followed by slash-separated tokens for the
     * running app (or raw session) and context.
     * 
     * @return
     */
    String getToken();

    /**
     * Each scope can carry arbitrary data linked to it.
     * 
     * @return
     */
    IParameters<String> getData();

    /**
     * Run a raw session with a given identifier.
     * 
     * @param sessionName
     * @return
     */
    ISessionScope run(String sessionName);

    /**
     * Run an application or script and return the scope for it.
     * 
     * @param behavior
     * @return
     */
    ISessionScope run(IBehavior behavior);

}
