package org.integratedmodelling.klab.api.provenance;

import java.util.Collection;
import java.util.Iterator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IProvenance.Node;

/**
 * An Artifact can be any of the first-class products of a k.LAB task: a {@link IObservation} (in
 * most situations) or a {@link IModel} when the model has been produced by another, such as a
 * learning model. Observations are the usual final results of a successful contextualization. By
 * implementing {@link Iterator}, we also allow Artifacts to represent groups of artifacts (e.g. all
 * the Subjects instantiated by resolving a subject {@link IObservable}). This enables much simpler
 * handling of provenance.
 * 
 * Each artifact can return the provenance graph it's part of, allowing all k.LAB tasks to simply
 * return an IArtifact and provide full information on what happened.
 * 
 * @author Ferd
 * @param <T>
 */
public interface IArtifact extends Node, Iterator<IArtifact> {

  /**
   * 
   * @return
   */
  String getUrn();

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
