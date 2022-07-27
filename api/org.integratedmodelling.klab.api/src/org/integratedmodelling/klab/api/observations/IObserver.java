package org.integratedmodelling.klab.api.observations;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.provenance.IAgent;
import org.integratedmodelling.klab.api.runtime.ITask;

/**
 * An observer can observe a context subject and potentially other observables. 
 * 
 * @author Ferd
 *
 */
public interface IObserver<T extends IObservation> extends IAgent {

    /**
     * Observe a new context of the passed type, returning an asynchronous future. This is the
     * primary, asynchronous context observation method for the k.LAB modeling API. Interactive
     * sessions and applications use the ISessionState to organize and schedule observations. When
     * this method is used, the task should not have started, so the user has a chance to apply
     * listeners, scenarios and other options.
     * 
     * @param observable
     * @param geometry
     * @return
     */
    ITask<ISubject> observe(IObservable observable, IGeometry geometry);


    /**
     * Observe the resolvable correspondent to the passed URN in this context, returning an
     * asynchronous future that will produce an observation. Equivalent to
     * {@link #observe(IObservable)} but will permit calling model URNs as well as semantic
     * observables. This is available in code usage but is not exposed through the public API.
     * 
     * @param observable a URN that resolves to a resolvable object - a concept, observable,
     *        observer or model (semantic or non-semantic).
     * @return a paused task to optionally configure and use by calling get() or start().
     */
    ITask<T> observe(String resolvableUrn);
}
