package org.integratedmodelling.klab.api.engine;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.engine.IEngineService.Reasoner;
import org.integratedmodelling.klab.api.engine.IEngineService.Resolver;
import org.integratedmodelling.klab.api.engine.IEngineService.ResourceManager;
import org.integratedmodelling.klab.api.engine.IEngineService.Runtime;
import org.integratedmodelling.klab.api.runtime.monitoring.IChannel;

public interface IScope extends IChannel {

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
     * The scope is created for an authenticated user by the engine.
     * 
     * @return
     */
    IUserIdentity getUser();

    /**
     * Each scope can carry arbitrary data linked to it.
     * 
     * @return
     */
    IParameters<String> getData();

    /**
     * Start a raw session with a given identifier and return the scope that controls it.
     * 
     * @param sessionName
     * @return
     */
    ISessionScope runSession(String sessionName);

    /**
     * Run an application or script and return the scope that controls it.
     * 
     * @param behavior
     * @return
     */
    ISessionScope runApplication(String behaviorName);

}
