package org.integratedmodelling.klab.api.engine;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAcknowledgement;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;

/**
 * A redesign of the engine API and its subcomponents, eventually available as separate services.
 * Unimplemented and only for brainstorming at the moment.
 * 
 * @author Ferd
 *
 */
public interface IEngineService {

    /**
     * 
     * @author Ferd
     *
     */
    interface ResourceManager {

        /**
         * 
         * @param urn
         * @param scope
         * @return
         */
        IBehavior resolveBehavior(String urn, IScope scope);
        
        /**
         * 
         * @param urn
         * @param scope
         * @return
         */
        IResource resolveResource(String urn, IScope scope);
        
        /**
         * 
         * @param originalResource
         * @param scope
         * @return
         */
        IResource contextualize(IResource originalResource, IContextScope scope);
        
        /**
         * 
         * @param contextualizedResource
         * @param scope
         * @return
         */
        IKlabData getData(IResource contextualizedResource, IScope scope);
        
    }

    interface Reasoner {

    }

    interface Resolver {

        /**
         * Resolve an observable in the passed scope, producing the dataflow that will make the
         * observation.
         * 
         * @param observable an {@link IObservable}, {@link IAcknowledgement}, {@link IModel} or a
         *        string resolving to one of those.
         * @param scope if not an {@link IAcknowledgement}, must have a specified geometry. If the
         *        {@link IObservable} is not a subject, must have a set context observation.
         * @return the dataflow that will create the observation once run by a runtime.
         */
        IDataflow<?> resolve(Object observable, IContextScope scope);

    }

    interface Runtime {

        /**
         * Run the passed dataflow in the scale of the observer represented in the scope; return the
         * primary artifact.
         * 
         * @param <T>
         * @param dataflow
         * @param scope must be or procede from a context scope
         * @return
         */
        <T extends IArtifact> T run(IDataflow<T> dataflow, IContextScope scope);
        
        
    }

    /**
     * Login through an authenticated user identity and return the root scope for that user. The
     * scope for the user should be stored: if the user was logged in previously, the previously
     * logged in scope should be returned..
     * 
     * @param user
     * @return
     */
    IScope login(IEngineUserIdentity user);

}
