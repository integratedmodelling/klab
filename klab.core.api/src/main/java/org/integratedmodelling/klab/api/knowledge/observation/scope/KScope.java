package org.integratedmodelling.klab.api.knowledge.observation.scope;

import java.io.Serializable;

import org.integratedmodelling.klab.api.collections.KParameters;
import org.integratedmodelling.klab.api.identities.KUserIdentity;
import org.integratedmodelling.klab.api.services.runtime.KChannel;

public interface KScope extends KChannel, Serializable {

    enum Type {
        USER, // root-level scope
        SCRIPT, // session-level scope
        API, // session for the REST API through a client
        APPLICATION, // session for an application, including the Explorer
        SESSION, // raw session for direct use within Java code
        CONTEXT // context, on which observe() can be called
    }

//    /**
//     * Return the reasoner service assigned to this scope.
//     * 
//     * @return
//     */
//    Reasoner getReasoner();
//
//    /**
//     * Return the resolver service assigned to this scope.
//     * 
//     * @return
//     */
//    Resolver getResolver();
//
//    /**
//     * Return the runtime service assigned to this scope.
//     * 
//     * @return
//     */
//    Runtime getRuntime();
//
//    /**
//     * Return the resources service assigned to this scope.
//     * 
//     * @return
//     */
//    Resources getResources();

    /**
     * The scope is created for an authenticated user by the engine.
     * 
     * @return
     */
    KUserIdentity getUser();

    /**
     * Each scope can carry arbitrary data linked to it.
     * 
     * @return
     */
    KParameters<String> getData();

    /**
     * Start a raw session with a given identifier and return the scope that controls it.
     * 
     * @param sessionName
     * @return
     */
    KSessionScope runSession(String sessionName);

    /**
     * Run an application or script and return the scope that controls it.
     * 
     * @param behavior
     * @return
     */
    KSessionScope runApplication(String behaviorName);

}
