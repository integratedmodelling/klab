package org.integratedmodelling.klab.api.provenance;

import java.util.Collection;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IProvenance.Node;

/**
 * An Artifact can be any of the first-class citizens in k.LAB: Observation or Model
 * (when the model has been produced by another, such as a learning model).
 * Observations are the final results of a successful contextualization. We also allow
 * Observations that represent groups of observations (e.g. all the Subjects created
 * by resolving a subject observable), to avoid creating monster graphs.
 * 
 * @author Ferd
 */
public interface Artifact extends Node {

    /**
     * @return
     */
    ISemantic getArtifact();

    /**
     * @return
     */
    Agent getConsumer();

    /**
     * 
     * @return
     */
    IConcept getObservable();

    /**
     * @return
     */
    Agent getOwner();

    /**
     * @return
     */
    IModel getModel();

    /**
     * @return
     */
    Collection<Artifact> getAntecedents();

    /**
     * @return
     */
    Collection<Artifact> getConsequents();

    /**
     * Return the temporal extent implied for this artifact by the provenance chain.
     * This includes (in order of preemption) any specific temporal constraint
     * specified by the model, and the temporal scale of all the observations
     * upstream. Temporal elements are merged upwards until a complete scale is
     * defined. Returns null only if there is no time upstream of this observation.
     * 
     * @return
     */
    ITime getTime();

    /**
     * Return the spatial extent implied for this artifact by the provenance chain.
     * This includes (in order of preemption) any specific temporal constraint
     * specified by the model, and the temporal scale of all the observations
     * upstream. Temporal elements are merged upwards until a complete scale is
     * defined. Returns null only if there is no space upstream of this observation.
     * 
     * @return
     */
    ISpace getSpace();

    /**
     * Trace the nearest artifact of the passed concept (or with the passed
     * role/trait) up the provenance chain.
     * 
     * @param cls
     * @return
     */
    Artifact trace(IConcept concept);

    /**
     * Collect all artifacts of the passed concept (or with the passed role/trait) up
     * the provenance chain.
     * 
     * @param concept
     * @return
     */
    Collection<Artifact> collect(IConcept concept);

    /**
     * Trace the nearest artifact with the passed role within the passed observation
     * up the provenance chain.
     * 
     * @param cls
     * @return
     */
    Artifact trace(IConcept role, IDirectObservation roleContext);

    /**
     * Collect all artifacts with the passed role within the passed observation up the
     * provenance chain.
     * 
     * @param concept
     * @return
     */
    Collection<Artifact> collect(IConcept role, IDirectObservation roleContext);

}