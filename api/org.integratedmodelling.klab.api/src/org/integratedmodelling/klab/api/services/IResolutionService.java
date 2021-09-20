package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;

/**
 * The resolution service takes a query and returns a dataflow to pass to the runtime service for
 * execution. It should be possible to build these services independently although each observation
 * needs a runtime service to be created.
 * <p>
 * Sessions are an orthogonal concept, managed by the runtime and used to organize observations and
 * activities. Having sessions is not mandatory although it is the paradigm used in the reference
 * implementation (with sessions being the identity that owns all observations). The session is
 * retrievable through the observations'identity hierarchy if they are made within one.
 * 
 * @author Ferd
 *
 */
public interface IResolutionService {

    /**
     * Create a stand-alone observation of a subject in a scale. If the subject can be resolved,
     * resolve it before generating the observation, unless a runtime service is not available.
     * 
     * @param concept
     * @param scale
     * @return
     */
    IObservation observe(IConcept concept, IScale scale);

    /**
     * Resolve the passed concept in the given context and return the dataflow, which will be a
     * child of the root dataflow retrievable from the context observation.
     * 
     * @param concept
     * @param context
     * @return
     */
    IDataflow<?> resolve(IConcept concept, IObservation context);

}
