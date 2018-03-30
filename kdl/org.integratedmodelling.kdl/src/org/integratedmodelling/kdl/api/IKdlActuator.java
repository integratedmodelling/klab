package org.integratedmodelling.kdl.api;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.integratedmodelling.kdl.utils.Range;

/**
 * The actor definition parsed from a KDL file. Used as a "contract" (i.e. an interface
 * specification for a matching contextualizer class, with empty computation or only transformations
 * for inputs) or as a complete computation, naming contextualizers in its computation.
 * 
 * @author ferdinando.villa
 *
 */
public interface IKdlActuator extends IKdlStatement {

  /**
   * Type contextualized by the actor.
   * 
   * @author ferdinando.villa
   *
   */
  enum Type {
    /**
     * Contextualizes number states.
     */
    NUMBER,
    /**
     * Contextualizes presence/absence states
     */
    BOOLEAN,
    /**
     * Contextualizes category states
     */
    CONCEPT,
    /**
     * Contextualizes processes
     */
    PROCESS,
    /**
     * Instantiates or contextualizes objects, according to arity.
     */
    OBJECT,
    /**
     * Produces text values, to be transformed by successive contextualizers. Illegal in contracts.
     */
    TEXT,
    /**
     * Contextualizes any quality. Only legal in contracts.
     */
    VALUE,
    /**
     * Produces range values. Only legal in parameters
     */
    RANGE,
    /**
     * Produce one of a set of values. Only legal in parameters, values are specified externally.
     */
    ENUM,
    /**
     * Produce extents other than time or space
     */
    EXTENT,
    /**
     * Produce temporal extents
     */
    TEMPORALEXTENT,
    /**
     * Produce spatial extents
     */
    SPATIALEXTENT,
    /**
     * Specify annotation contracts
     */
    ANNOTATION,
    /**
     * A list value
     */
    LIST, 
    /**
     * Only for CLI command prototypes
     */
    VOID,
    /**
     * Only for service prototypes returning contextualizers
     */
    CONTEXTUALIZER;

    /**
     * Classify a POD type producing the type that represents it.
     * 
     * @param o
     * @return a type for o. If o == null, VALUE is returned.
     */
    public static Type classify(Object o) {
      if (o instanceof Number) {
        return NUMBER;
      } else if (o instanceof Boolean) {
        return BOOLEAN;
      } else if (o instanceof String) {
        return TEXT;
      } else if (o instanceof Range) {
        return RANGE;
      } else if (o instanceof List) {
        return LIST;
      } 
      return VALUE;
    }
  }

  public enum Target {
    MODELS, OBSERVERS, CONCEPTS
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
   * @return
   */
  String getNewObservationType();

  /**
   * Return the URN of the observation that we must connect to at this step, if any. Alternative to
   * {@link IKdlActuator#getNewObservationType()} - if one is not null, the other will be. Both can
   * be null (e.g. in instantiating actuators).
   * 
   * @return
   */
  String getNewObservationUrn();

  /**
   * Optional description. If not given returns empty string.
   * 
   * @return
   */
  String getDescription();

  /**
   * Computation. Contains contextualizers or transformations - only the latter are allowed in
   * contracts. If more than one computations are returned, they can execute in parallel.
   * 
   * @return
   */
  Collection<IKdlComputation> getComputations();

  /**
   * All actuators that are imported. They may be resolved (@link {@link #getComputation()} != null)
   * or not.
   * 
   * @return
   */
  Collection<IKdlActuator> getInputs();

  /**
   * Get all parameters declared in the actuator.
   * 
   * @return
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
   * @return
   */
  boolean isExported();

  /**
   * True if this contextualizer describes an input of its parent contextualizer.
   * 
   * @return
   */
  boolean isImported();

  /**
   * If true, this contextualizer specifies a single value
   * 
   * @return
   */
  boolean isParameter();

  /**
   * If true, this contextualizer is not necessary for computation.
   * 
   * @return
   */
  boolean isOptional();

  /**
   * If both {@link #isParameter()} and {@link #isOptional()} return true, this should return the
   * value to use as a default.
   * 
   * @return
   */
  Object getDefaultValue();

  /**
   * The class name of a Java peer may be defined for each actuator.
   * 
   * @return
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
   * @return
   */
  List<IKdlActuator> getActors();

  /**
   * Any workflow-local alias defined for the actuator ('as' <name>), or null.
   * 
   * @return
   */
  String getAlias();

  /**
   * The geometry of the actuator. Null means simple, arbitrary data.
   * 
   * @return
   */
  String getGeometry();

  /**
   * If any specific subscale has been given for the actuator, return the generating
   * extent functions.
   * 
   * @return
   */
  List<IKdlContextualizer> getCoverage();

}
