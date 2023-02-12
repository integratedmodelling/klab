package org.integratedmodelling.klab.api.engine;

import java.util.concurrent.Future;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.engine.IEngineService.Reasoner;
import org.integratedmodelling.klab.api.engine.IEngineService.Resolver;
import org.integratedmodelling.klab.api.engine.IEngineService.ResourceManager;
import org.integratedmodelling.klab.api.engine.IEngineService.Runtime;
import org.integratedmodelling.klab.api.model.IAcknowledgement;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;

/**
 * Scope for any observation operation. A root scope is given for the user upon authentication and
 * more scopes can be asked from it to handle sessions, applications and contexts. This should
 * eventually carry all user states and substitute ISessionState. Scopes are hierarchical and there
 * is a scope per application, context and session (which becomes transparently handled).
 * 
 */
public interface IObservationScope {

    enum Type {
        USER, // root-level scope
        SCRIPT, // session-level scope
        API, // session for the REST API through a client
        APPLICATION, // session for an application, including the Explorer
        SESSION, // raw session for direct use within Java code
        CONTEXT // context, on which observe() can be called
    }

    Reasoner getReasoner();

    Resolver getResolver();

    Runtime getRuntime();

    ResourceManager getResources();

    IEngineService getEngine();

    /**
     * The token is needed to communicate with the scope. It can represent the user, an application,
     * or a context; the hierarchial structure of these is reflected in the token, which always
     * starts with the user token possibly followed by slash-separated tokens for the running app
     * (or raw session) and context.
     * 
     * @return
     */
    String getToken();

    /**
     * 
     * @return
     */
    IObservation getContext();
    /**
     * 
     * @return
     */
    IActorIdentity<?> getObserver();

    /**
     * Never null. User scopes have empty geometry; context scopes have the geometry of the context
     * or the union of the contexts if >1 root observations have been set.
     * 
     * @return
     */
    IGeometry getGeometry();

    /**
     * 
     * @param scenarios
     * @return
     */
    IObservationScope withScenarios(String... scenarios);

    /**
     * Return a scope focused on a specific root observation as the context for its
     * {@link #observe(Object...)} calls.
     * 
     * @param observation
     * @return
     */
    IObservationScope within(IDirectObservation observation);

    /**
     * 
     * @param scenarios
     * @return
     */
    IObservationScope withObserver(IActorIdentity<?> observer);

    /**
     * Run an application or script and return the scope for it.
     * 
     * @param behavior
     * @return
     */
    IObservationScope run(IBehavior behavior);

    /**
     * Make an observation. Must be called on a context scope, possibly focused on a given root
     * observation using {@link #within(IDirectObservation)}. If no root observation is present in
     * the scope, the arguments must fully specify a subject, either through an
     * {@link IAcknowledgement} or a subject observable + a scale.
     * <p>
     * In case the observable specifies a relationship, k.LAB will attempt to instantiate it,
     * observing its source/target endpoints as well, unless two subject observations are passed, in
     * which case a specified relationship will be instantiated between them. In the latter case,
     * each relationship will be resolved but configuration detection will only happen upon exiting
     * the scope where observe() is called.
     * <p>
     * If the observation is at root level, or connecting two root-level subject through a
     * relationship, the overall geometry of the context will be automatically adjusted.
     * 
     * @param observables either a {@link IObservable} (with a {@link IGeometry} if root subject) or
     *        a {@link IAcknowledgement} for a pre-specified root subject.
     * @return a future for the observation being contextualized.
     */
    Future<IObservation> observe(Object... observables);

}