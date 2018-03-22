package org.integratedmodelling.klab.api.runtime;

import java.util.Collection;
import org.eclipse.core.commands.IParameter;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.ICountableObservation;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

/**
 * The runtime context holds all information about the computation being run. It is passed to
 * dataflows and down to actuators and contextualizers, appropriately customized to reflect names
 * and locators that each actuator expects.
 * 
 * The {@link IParameters} methods access user-defined parameters, including any passed to the
 * calling functions or URNs and, if appropriate, context-localized POD values for states (e.g. the
 * specific value at the point of computation). The actual data objects are always available through
 * {@link #getData(String)}.
 * 
 * @author Ferd
 *
 */
public interface IComputationContext extends IParameters {

  /**
   * The namespace of reference in this context. Usually that of the running model or observer.
   * 
   * @return the namespace of reference. Null in empty contexts or during non-semantic computations.
   */
  INamespace getNamespace();

  /**
   * 
   * @return the provenance graph. Null in an empty context.
   */
  IProvenance getProvenance();

  /**
   * 
   * @return the event bus. Null in an empty context.
   */
  IEventBus getEventBus();

  /**
   * 
   * @param observation
   * @return
   */
  Collection<IRelationship> getOutgoingRelationships(ISubject observation);

  /**
   * 
   * @param observation
   * @return
   */
  Collection<IRelationship> getIncomingRelationships(ISubject observation);

  /**
   * 
   * @return
   */
  Collection<ISubject> getAllSubjects();

  /**
   * Get the resolved {@link IArtifact object} corresponding to the passed local name. Use
   * {@link IParameter IParameter get methods} to retrieve contextualized values for states or
   * parameters.
   * 
   * @param localName
   * @return the artifact, null if not found.
   */
  IArtifact getData(String localName);

  /**
   * Return all known artifacts of the passed class. For example, all data artifacts known at the
   * time of computation can be retrieved using <code>getData(IDataArtifact.class)</code>.
   * 
   * @param type
   * @return a collection of artifacts, possibly empty, never null.
   */
  <T extends IArtifact> Collection<T> getData(Class<T> type);

  /**
   * Return a valid monitor for any communication.
   * 
   * @return the monitor for this computation. Never null.
   */
  IMonitor getMonitor();

  /**
   * Return the concept type describing the artifact being computed.
   * 
   * @return the type of the observation
   */
  IKimConcept.Type getArtifactType();
  
  /**
   * Create a new observation of the specified countable observable and with the specified geometry.
   * Use in {@link IInstantiator instantiators} to create new objects. Use
   * {@link #newRelationship(IObservable, IGeometry, IObjectArtifact, IObjectArtifact)} to create a
   * relationship.
   * <p>
   * While any k.LAB-aware implementation will receive a {@link IScale} instead of a
   * {@link IGeometry} and return a {@link ICountableObservation} rather than just
   * {@link IObjectArtifact}, we keep the basic, non-semantic types in the signature for consistency
   * with derived APIs of remote services and other non-semantic computations.
   * <p>
   * As the runtime provider is responsible for creating the {@code IComputationContext}, this is
   * where it can control the type and features of any new object created.
   * <p>
   * 
   * @param observable
   * @param geometry
   * @return a new observation for the observable and geometry
   * @throw IllegalArgumentException if the observable describes a non-countable or a relationship.
   */
  IObjectArtifact newObservation(IObservable observable, IGeometry geometry);

  /**
   * Create a new observation of the specified relationship with with the specified geometry, source
   * and target subjects. Use in {@link IInstantiator relationship instantiators} to create new
   * objects.
   * <p>
   * See {@link #newObservation(IObservable, IGeometry)} for API design choices.
   * <p>
   * 
   * @param observable
   * @param geometry
   * @param source
   * @param target
   * @return a new observation for the observable and geometry
   * @throw IllegalArgumentException if the observable does not describe a relationship.
   */
  IObjectArtifact newRelationship(IObservable observable, IGeometry geometry,
      IObjectArtifact source, IObjectArtifact target);

}
