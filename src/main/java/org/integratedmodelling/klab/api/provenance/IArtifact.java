package org.integratedmodelling.klab.api.provenance;

import java.util.Collection;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.artifacts.IModelArtifact;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IProvenance.Node;

/**
 * An Artifact can be any of the first-class products of a k.LAB task: a non-semantic
 * {@link IDataArtifact} or {@link IObjectArtifact}, a semantic {@link IObservation} (as produced by
 * most activities in k.LAB) or a {@link IKimModel k.IM model description} when the model has been
 * produced by an observation activity, such as a learning model.
 * <p>
 * By implementing {@link Iterable}, we also allow Artifacts to represent groups of artifacts (e.g.
 * all the {@link ISubject subjects} instantiated by resolving a subject {@link IObservable
 * observable}). This enables simpler handling of provenance, as each observation activity returns
 * one artifact, possibly iterable as a group.
 * <p>
 * Each artifact exposes the provenance graph it's part of, allowing all k.LAB tasks to simply
 * return an {@code IArtifact} and provide full information on what happened.
 * <p>
 * The API to use a {@link IKimModel} as an artifact ({@link IModelArtifact}) is not fully
 * developed yet.
 * <p>
 * 
 * @author Ferd
 */
public interface IArtifact extends Node, Iterable<IArtifact> {

  /**
   * The geometry linked to the observation. Observational artifacts will specialize this as IScale.
   * 
   * @return the geometry
   */
  IGeometry getGeometry();

  /**
   * Metadata. Never null, possibly empty.
   * 
   * @return the metadata
   */
  IMetadata getMetadata();

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

  /**
   * The size of the group that this artifact is part of. Any artifact is part of
   * a group including at least itself.
   * 
   * @return 1 or more
   */
  int groupSize();

}
