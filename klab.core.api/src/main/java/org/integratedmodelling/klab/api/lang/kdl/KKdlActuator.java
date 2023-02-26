package org.integratedmodelling.klab.api.lang.kdl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.KArtifact;
import org.integratedmodelling.klab.api.lang.KAnnotation;

/**
 * The actor definition parsed from a KDL file. Used as a "contract" (i.e. an
 * interface specification for a matching contextualizer class, with empty
 * computation or only transformations for inputs) or as a complete computation,
 * naming contextualizers in its computation.
 * 
 * @author ferdinando.villa
 *
 */
public interface KKdlActuator extends KKdlStatement {

	public enum Target {
		MODELS, OBSERVERS, CONCEPTS, DEFINITIONS, DEPENDENCIES
	}

	/**
	 * Mandatory name for the actuator.
	 * 
	 * @return the name.
	 */
	String getName();

	/**
	 * True if this is an import or export using an annotation tag instead of an
	 * explicit name. In that case one of {@link #isImport()} or {@link #isExport()}
	 * must return true, and all the remaining info will be the type (mandatory) and
	 * docstring/label (optional).
	 * 
	 * @return
	 */
	boolean isTaggingAnnotation();

	/**
	 * Mandatory type.
	 * 
	 * @return the type.
	 */
	KArtifact.Type getType();

	/**
	 * Optional label for display and provenance records
	 * 
	 * @return the label
	 */
	String getLabel();

	/**
	 * Return the declared type of the empty observation that must be created at
	 * this step, if any. Alternative to {@link KKdlActuator#getNewObservationUrn()}
	 * - if one is not null, the other will be. Both can be null (e.g. in
	 * instantiating actuators).
	 * 
	 * @return the new observation type
	 */
	String getNewObservationType();

	/**
	 * Return the URN of the observation that we must connect to at this step, if
	 * any. Alternative to {@link KKdlActuator#getNewObservationType()} - if one is
	 * not null, the other will be. Both can be null (e.g. in instantiating
	 * actuators).
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
	 * Computation. Contains contextualizers or transformations - only the latter
	 * are allowed in contracts. If more than one computations are returned, they
	 * can execute in parallel.
	 * 
	 * @return the computation
	 */
	Collection<KKdlComputation> getComputations();

	/**
	 * All actuators that are imported. They may be resolved (@link
	 * {@link #getComputations()} != null) or not.
	 * 
	 * @return the input actuators
	 */
	Collection<KKdlActuator> getInputs();

	/**
	 * All actuators that are exported.
	 * 
	 * @return the input actuators
	 */
	Collection<KKdlActuator> getOutputs();

	/**
	 * Get all parameters declared in the actuator.
	 * 
	 * @return the parameters for the actuator
	 */
	Collection<KKdlActuator> getParameters();

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
	boolean isExport();

	/**
	 * True if this contextualizer describes an input of its parent contextualizer.
	 * 
	 * @return true if input
	 */
	boolean isImport();

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
	 * If true, this contextualizer is declared final - which affects its possible
	 * use from specifications.
	 * 
	 * @return true if optional
	 */
	boolean isFinal();

	/**
	 * Abstract actuators are only used as a base for extensions of others in the
	 * same dataflow.
	 * 
	 * @return
	 */
	boolean isAbstract();

	/**
	 * For parameter specifications, this tags an input as an expression whose
	 * return value is that of the actuator.
	 * 
	 * @return
	 */
	boolean isExpression();

	/**
	 * If true, this actuator is a sub-dataflow to resolve each instance for the
	 * instantiator parent (if the name is the same as the parent's) or an attribute
	 * instantiated by a classifier filter in the computation.
	 * 
	 * @return
	 */
	boolean isResolution();

	/**
	 * Processors are computations that modify a pre-contextualized observation.
	 * 
	 * @return
	 */
	boolean isFilter();

	/**
	 * If both {@link #isParameter()} and {@link #isOptional()} return true, this
	 * should return the value to use as a default.
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
	 * Parameter actuators can have values defined by an enum, which is returned
	 * here.
	 * 
	 * @return set of enum values or empty string.
	 */
	Set<String> getEnumValues();

	/**
	 * All actuators defined inside this one.
	 * 
	 * @return all actuators
	 */
	List<KKdlActuator> getActors();

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
	 * If any specific subscale has been given for the actuator, return the
	 * generating extent functions.
	 * 
	 * @return the coverage
	 */
	List<KKdlContextualizer> getCoverage();

	/**
	 * Any annotations added in the specification.
	 * 
	 * @return annotations in order of specification
	 */
	List<KAnnotation> getAnnotations();

	/**
	 * Unit specs may be added to import statements. These will be validated only
	 * for syntax at this stage.
	 * 
	 * @return
	 */
	String getUnit();

	/**
	 * Const is for parameters that point to immutable resources, such as URNs or
	 * literals. Used in building the provenance diagram.
	 * 
	 * @return
	 */
	boolean isConst();

}
