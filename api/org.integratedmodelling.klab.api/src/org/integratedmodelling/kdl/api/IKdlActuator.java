package org.integratedmodelling.kdl.api;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.provenance.IArtifact.Type;

/**
 * The actor definition parsed from a KDL file. Used as a "contract" (i.e. an interface
 * specification for a matching contextualizer class, with empty computation or only transformations
 * for inputs) or as a complete computation, naming contextualizers in its computation.
 * 
 * @author ferdinando.villa
 *
 */
public interface IKdlActuator extends IKdlStatement {

  public enum Target {
    MODELS, OBSERVERS, CONCEPTS, DEFINITIONS
  }

  /**
   * Mandatory name for the actuator.
   * 
   * @return the name.
   */
  String getName();

  /**
   * Mandatory type.
   * 
   * @return the type.
   */
  Type getType();

  /**
   * Return the declared type of the empty observation that must be created at this step, if any.
   * Alternative to {@link IKdlActuator#getNewObservationUrn()} - if one is not null, the other will
   * be. Both can be null (e.g. in instantiating actuators).
   * 
   * @return the new observation type
   */
  String getNewObservationType();

  /**
   * Return the URN of the observation that we must connect to at this step, if any. Alternative to
   * {@link IKdlActuator#getNewObservationType()} - if one is not null, the other will be. Both can
   * be null (e.g. in instantiating actuators).
   * 
   * @return the new observation urn
   */
  String getNewObservationUrn();

  /**
   * Optional description. If not given returns empty string.
   * 
   * @return the description or ""
   */
  String getDescription();

  /**
   * Computation. Contains contextualizers or transformations - only the latter are allowed in
   * contracts. If more than one computations are returned, they can execute in parallel.
   * 
   * @return the computation
   */
  Collection<IKdlComputation> getComputations();

  /**
   * All actuators that are imported. They may be resolved (@link {@link #getComputations()} !=
   * null) or not.
   * 
   * @return the input actuators
   */
  Collection<IKdlActuator> getInputs();

  /**
   * Get all parameters declared in the actuator.
   * 
   * @return the parameters for the actuator
   */
  Collection<IKdlActuator> getParameters();

  /**
   * Return the applicable target (non-empty only in annotation contracts).
   * 
   * @return the set of target the specified annotation can be applied to.
   */
  Collection<Target> getTargets();

  /**
   * True if this contextualizer describes an output of its parent contextualizer.
   * 
   * @return true if output
   */
  boolean isExported();

  /**
   * True if this contextualizer describes an input of its parent contextualizer.
   * 
   * @return true if input
   */
  boolean isImported();

  /**
   * If true, this contextualizer specifies a single value
   * 
   * @return true if single valued
   */
  boolean isParameter();

  /**
   * If true, this contextualizer is not necessary for computation.
   * 
   * @return true if optional
   */
  boolean isOptional();

  /**
   * If both {@link #isParameter()} and {@link #isOptional()} return true, this should return the
   * value to use as a default.
   * 
   * @return the default value
   */
  Object getDefaultValue();

  /**
   * The class name of a Java peer may be defined for each actuator.
   * 
   * @return the java class specified
   */
  String getJavaClass();

  /**
   * Parameter actuators can have values defined by an enum, which is returned here.
   * 
   * @return set of enum values or empty string.
   */
  Set<String> getEnumValues();

  /**
   * All actuators defined inside this one.
   * 
   * @return all actuators
   */
  List<IKdlActuator> getActors();

  /**
   * Any workflow-local alias defined for the actuator ('as' <name>), or null.
   * 
   * @return the alias
   */
  String getAlias();

  /**
   * The geometry of the actuator. Null means simple, arbitrary data.
   * 
   * @return the geometry
   */
  String getGeometry();

  /**
   * If any specific subscale has been given for the actuator, return the generating extent
   * functions.
   * 
   * @return the coverage
   */
  List<IKdlContextualizer> getCoverage();

}
