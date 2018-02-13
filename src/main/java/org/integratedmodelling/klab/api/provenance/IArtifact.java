package org.integratedmodelling.klab.api.provenance;

import java.util.Collection;
import java.util.Iterator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.provenance.IProvenance.Node;

/**
 * An Artifact can be any of the first-class citizens in k.LAB: Observation or Model (when the model
 * has been produced by another, such as a learning model). Observations are the final results of a
 * successful contextualization. We also allow Artifacts to represent groups of observations (e.g.
 * all the Subjects instantiated by resolving a subject observable), to avoid creating monster
 * graphs.
 * 
 * Artifacts are also iterators of artifacts so that groups of artifacts can be seen and returned as
 * one artifact. Check {@link #hasNext()} to see if the returned artifact is the first of a group.
 * 
 * Each artifact can return the provenance graph it's part of, allowing all k.LAB operations to
 * simply return an Artifact.
 * 
 * @author Ferd
 * @param <T>
 */
public interface IArtifact extends Node, Iterator<IArtifact> {

  /**
   * @return
   */
  IAgent getConsumer();

  /**
   * @return
   */
  IAgent getOwner();

  /**
   * @return
   */
  Collection<IArtifact> getAntecedents();

  /**
   * @return
   */
  Collection<IArtifact> getConsequents();

  /**
   * Trace the nearest artifact of the passed concept (or with the passed role/trait) up the
   * provenance chain.
   * 
   * @param concept
   * @return
   */
  IArtifact trace(IConcept concept);

  /**
   * Collect all artifacts of the passed concept (or with the passed role/trait) up the provenance
   * chain.
   * 
   * @param concept
   * @return
   */
  Collection<IArtifact> collect(IConcept concept);

  /**
   * Trace the nearest artifact with the passed role within the passed observation up the provenance
   * chain.
   * 
   * @param role
   * @param roleContext
   * 
   * @return
   */
  IArtifact trace(IConcept role, IDirectObservation roleContext);

  /**
   * Collect all artifacts with the passed role within the passed observation up the provenance
   * chain.
   * 
   * @param role
   * @param roleContext
   * 
   * @param concept
   * @return
   */
  Collection<IArtifact> collect(IConcept role, IDirectObservation roleContext);

}
