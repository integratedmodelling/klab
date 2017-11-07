package org.integratedmodelling.klab.api.observations;

import java.io.Serializable;

import org.integratedmodelling.kim.api.IObservable;
import org.integratedmodelling.klab.api.runtime.IContext;

public interface IObservation extends Serializable {

    /**
     * 
     * @return
     */
    String getId();
    
    /**
     * Return the observable, including the main type and the observation type.
     * 
     * @return the observation's observable
     */
    IObservable getObservable();

    /**
     * Return the scale seen by this object, merging all the extents declared for the
     * subject in the observation context.
     *
     * @return the observation's scale
     */
    IScale getScale();

    /**
     * Observation may have been made in the context of another direct observation. This
     * will always return non-null in indirect observations, and may return null in direct
     * ones when they represent the "root" context.
     * 
     * @return the context for the observation, if any.
     */
    IDirectObservation getContextObservation();
    
    /**
     * The overall "world" that this observation is part of, with the root subject at the
     * top and all informations about resolution and provenance.
     * 
     * @return
     */
    IContext getContext();

    /**
     * The subject observation that contextualized this observation. This is not the same
     * as the context observation: it allows recording different viewpoints on
     * observations that are contextual to the same observable - e.g. qualities of the
     * same subject seen by different child subjects in it. If null, this was made by the
     * "root subject" that represents the session user. Later we may create a subject to
     * represent the session user.
     * 
     * @return the subject that provides the viewpoint for this observation, or null if
     *  this was a user-made observation.
     */
    ISubject getObserver();
}
