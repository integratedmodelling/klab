package org.integratedmodelling.kim.api;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.utils.Pair;

/**
 * A computable resource is a declaration that specifies a processing step for a
 * dataflow. In k.IM this can be represented by:
 * <p>
 * <ul>
 * <li>a literal value;</li>
 * <li>the URN for a data source or computation;</li>
 * <li>a service call explicitly given in k.IM;</li>
 * <li>an executable expression in a supported language;</li>
 * <li>a classification or lookup table;</li>
 * <li>a conversion between a source and a target value semantics (e.g. unit or
 * currency)</li>
 * </ul>
 * <p>
 * It is the runtime's task to turn any computable resource into a uniform
 * service call, which produces a IContextualizer to be inserted in a dataflow.
 * 
 * @author Ferd
 *
 */
public interface IComputableResource extends IKimStatement {

	/**
	 * The target observable for this computation; null if the target is the main
	 * observable in the correspondent actuator. Otherwise the computation affects
	 * other artifacts, as in the case of internal dependencies due to indirect
	 * observables being used in subsequent computation to produce the main one.
	 * 
	 * @return the target name
	 */
	IObservable getTarget();

	/**
	 * The target artifact ID when this computation is a mediation. In this case the
	 * computation means "send this artifact through this mediator". 
	 * <p>
	 * This may be merged with getTarget() at some point as the use cases for it are
	 * similar.
	 * 
	 * @return the mediation target ID.
	 */
	String getMediationTargetId();

	/**
	 * Each computation may use a different language. Null means the default
	 * supported expression language.
	 * 
	 * @return the language or null
	 */
	String getLanguage();

	/**
	 * A literal constant produced in lieu of this computation. Only one among
	 * getLiteral(), getServiceCall(), getUrn(), getClassification(),
	 * getAccordingTo(), getLookupTable() and getExpression() will return a non-null
	 * value.
	 * 
	 * @return any literal
	 */
	Object getLiteral();

	/**
	 * A literal constant produced in lieu of this computation Only one among
	 * getLiteral(), getServiceCall(), getUrn(), getClassification(),
	 * getAccordingTo(), getLookupTable() and getExpression() will return a non-null
	 * value.
	 * 
	 * @return the service call
	 */
	IServiceCall getServiceCall();

	/**
	 * A literal constant produced in lieu of this computation. Only one among
	 * getLiteral(), getServiceCall(), getUrn(), getClassification(),
	 * getAccordingTo(), getLookupTable() and getExpression() will return a non-null
	 * value.
	 * 
	 * @return the expression
	 */
	String getExpression();

	/**
	 * A classification of the input. Only one among getLiteral(), getServiceCall(),
	 * getUrn(), getClassification(), getAccordingTo(), getLookupTable() and
	 * getExpression() will return a non-null value.
	 * 
	 * @return the classification
	 */
	IKimClassification getClassification();

	/**
	 * A lookup table translating the inputs. Only one among getLiteral(),
	 * getServiceCall(), getUrn(), getClassification(), getAccordingTo(),
	 * getLookupTable() and getExpression() will return a non-null value.
	 * 
	 * @return the lookup table
	 */
	IKimLookupTable getLookupTable();

	/**
	 * An implicit classification built by matching values of an annotation property
	 * to subclasses of the observable. Only one among getLiteral(),
	 * getServiceCall(), getUrn(), getClassification(), getAccordingTo(),
	 * getLookupTable() and getExpression() will return a non-null value.
	 * 
	 * @return the classifier property
	 */
	String getAccordingTo();

	/**
	 * A URN specifying a remote computation. Only one among getLiteral(),
	 * getServiceCall(), getUrn() and getExpression() will return a non-null value.
	 * 
	 * @return the urn
	 */
	String getUrn();

	/**
	 * Resources such as expressions or URN-specified remote computations may have
	 * requirements that must be satisfied within the model where the computation
	 * appears. These will be made available in appropriate form (scalar or not) by
	 * the runtime environment.
	 * 
	 * @return the requirements as a collection of name and type pairs.
	 */
	Collection<Pair<String, IArtifact.Type>> getRequiredResourceNames();

	/**
	 * Any parameters set for the computation, e.g. in the case of a function call
	 * or a URN with optional values.
	 * 
	 * @return parameter map, never null, possibly empty.
	 */
	Map<String, Object> getParameters();

	/**
	 * This computation may be linked to a condition, which is another computation
	 * producing a boolean. This is always empty if this resource is itself a
	 * condition.
	 * 
	 * @return the condition or an empty container.
	 */
	Optional<IComputableResource> getCondition();

	/**
	 * The computation may consist in a mediation of a quantity represented by the
	 * first element in the returned tuple, which must be converted into a value
	 * represented by the second.
	 * <p>
	 * If the resource is created, the mediators must be guaranteed compatible.
	 * <p>
	 * 
	 * @return a tuple containing the original and target value semantics.
	 */
	Pair<IValueMediator, IValueMediator> getConversion();

	/**
	 * Only meaningful if this computable is a condition computing a (scalar or
	 * distributed) boolean, this specifies whether this condition was given with
	 * negative ('unless' instead of 'if') semantics.
	 * 
	 * @return true if negated ('unless')
	 */
	boolean isNegated();

	/**
	 * True if this computation is a mediation, expected to output a transformation
	 * of the artifact passed to it, to be used in its place to match a specific
	 * observation semantics.
	 * 
	 * @return true if mediator
	 */
	boolean isMediation();

}
