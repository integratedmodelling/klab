package org.integratedmodelling.klab.api.lang.kim;

import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.SemanticRole;
import org.integratedmodelling.klab.api.knowledge.SemanticType;
import org.integratedmodelling.klab.api.lang.UnarySemanticOperator;

/**
 * A IKimConcept is the declaration of a concept, i.e. a semantic expression
 * built out of known concepts and conforming to k.IM semantic constraints.
 * Concept expressions compile to this structure, which retains the final
 * concept names only as fully qualified names. External infrastructure can
 * create the actual concepts that a reasoner can operate on.
 * 
 * @author ferdinando.villa
 *
 */
public interface KKimConcept extends KKimStatement {

	enum Expression {
		SINGLETON, UNION, INTERSECTION
	}


//	/**
//	 * All declarable concept bits set. Each observable AND this must yield a set of
//	 * size 1.
//	 */
//	public static final EnumSet<Type> DECLARABLE_TYPES = EnumSet.of(Type.QUALITY, Type.SUBJECT, Type.AGENT, Type.EVENT,
//			Type.CONFIGURATION, Type.DOMAIN, Type.RELATIONSHIP, Type.EXTENT, Type.PROCESS, Type.ATTRIBUTE, Type.REALM,
//			Type.IDENTITY, Type.ROLE);
//
//	public static final EnumSet<Type> MODELABLE_TYPES = EnumSet.of(Type.QUALITY, Type.SUBJECT, Type.AGENT, Type.EVENT,
//			Type.CONFIGURATION, Type.RELATIONSHIP, Type.PROCESS);
//
//	/**
//	 * Qualities that are naturally inherent and should not be allowed to have
//	 * explicit inherency but just context.
//	 */
//	public static final EnumSet<Type> INHERENT_QUALITIES = EnumSet.of(Type.PROPORTION, Type.PROBABILITY, Type.DISTANCE,
//			Type.VALUE, Type.OCCURRENCE, Type.PRESENCE, Type.UNCERTAINTY, Type.NUMEROSITY, Type.OBSERVABILITY,
//			Type.RATE);
//
//	public static final Set<Type> OPERATOR_TYPES = EnumSet.of(Type.CHANGE, Type.NUMEROSITY, Type.DISTANCE,
//			/* FIXME MISSING: LEVEL */ Type.MAGNITUDE, Type.OBSERVABILITY, Type.OCCURRENCE, Type.PRESENCE,
//			Type.PROBABILITY, Type.PROPORTION, Type.RATIO, Type.CLASS, Type.UNCERTAINTY, Type.VALUE,
//			Type.MONETARY_VALUE);
//
//	/**
//	 * All quality type bits sets (not QUALITY itself). Each quality AND this must
//	 * yield a set of size 1.
//	 */
//	public static final EnumSet<Type> QUALITY_TYPES = EnumSet.of(Type.CLASS, Type.QUANTITY, Type.ENERGY, Type.ENTROPY,
//			Type.LENGTH, Type.MASS, Type.VOLUME, Type.WEIGHT, Type.MONEY, Type.DURATION, Type.AREA, Type.ACCELERATION,
//			Type.PRIORITY, Type.ELECTRIC_POTENTIAL, Type.CHARGE, Type.RESISTANCE, Type.RESISTIVITY, Type.PRESSURE,
//			Type.ANGLE, Type.VELOCITY, Type.TEMPERATURE, Type.VISCOSITY, Type.UNCERTAINTY, Type.RATIO, Type.PROPORTION,
//			Type.PROBABILITY, Type.NUMEROSITY, Type.DISTANCE, Type.VALUE, Type.MONETARY_VALUE, Type.OCCURRENCE,
//			Type.PRESENCE, Type.AMOUNT, Type.RATE);
//
//	/**
//	 * All quality type bits sets including QUALITY itself. Each quality AND this
//	 * must yield a set of size 0.
//	 */
//	public static final EnumSet<Type> ALL_QUALITY_TYPES = EnumSet.of(Type.CLASS, Type.QUALITY, Type.QUANTITY,
//			Type.ENERGY, Type.ENTROPY, Type.LENGTH, Type.MASS, Type.VOLUME, Type.WEIGHT, Type.MONEY, Type.DURATION,
//			Type.AREA, Type.ACCELERATION, Type.PRIORITY, Type.ELECTRIC_POTENTIAL, Type.CHARGE, Type.RESISTANCE,
//			Type.RESISTIVITY, Type.PRESSURE, Type.ANGLE, Type.VELOCITY, Type.TEMPERATURE, Type.VISCOSITY,
//			Type.UNCERTAINTY, Type.RATIO, Type.PROPORTION, Type.PROBABILITY, Type.NUMEROSITY, Type.DISTANCE, Type.VALUE,
//			Type.OCCURRENCE, Type.PRESENCE, Type.AMOUNT, Type.RATE, Type.MONETARY_VALUE);
//
//	/**
//	 * All qualities that are expressed through a continuous numeric state.
//	 */
//	public static final EnumSet<Type> CONTINUOUS_QUALITY_TYPES = EnumSet.of(Type.QUANTITY, Type.ENERGY, Type.ENTROPY,
//			Type.LENGTH, Type.MASS, Type.VOLUME, Type.WEIGHT, Type.MONEY, Type.DURATION, Type.AREA, Type.ACCELERATION,
//			Type.PRIORITY, Type.ELECTRIC_POTENTIAL, Type.CHARGE, Type.RESISTANCE, Type.RESISTIVITY, Type.PRESSURE,
//			Type.ANGLE, Type.VELOCITY, Type.TEMPERATURE, Type.VISCOSITY, Type.UNCERTAINTY, Type.RATIO, Type.PROPORTION,
//			Type.PROBABILITY, Type.NUMEROSITY, Type.DISTANCE, Type.VALUE, Type.OCCURRENCE, Type.PRESENCE, Type.AMOUNT,
//			Type.MAGNITUDE, Type.RATE, Type.MONETARY_VALUE);
//
//	/**
//	 * All direct observables
//	 */
//	public final static EnumSet<Type> DIRECT_OBSERVABLE_TYPES = EnumSet.of(Type.DIRECT_OBSERVABLE, Type.SUBJECT,
//			Type.AGENT, Type.EVENT, Type.RELATIONSHIP, Type.PROCESS, Type.CONFIGURATION, Type.COUNTABLE,
//			/* FIXME ??? */Type.ABSTRACT);
//
//	/**
//	 * All base observables
//	 */
//	public final static EnumSet<Type> BASE_OBSERVABLE_TYPES = EnumSet.of(Type.SUBJECT, Type.EVENT, Type.RELATIONSHIP,
//			Type.PROCESS, Type.QUALITY, Type.AGENT);
//
//	/**
//	 * Everything we can write a model for
//	 */
//	public final static EnumSet<Type> BASE_MODELABLE_TYPES = EnumSet.of(Type.SUBJECT, Type.EVENT, Type.RELATIONSHIP,
//			Type.PROCESS, Type.QUALITY, Type.AGENT, Type.TRAIT, Type.CONFIGURATION);
//
//	/**
//	 * All trait type bits set (not TRAIT itself). Each trait AND this must yield a
//	 * set of size 1.
//	 */
//	public static final EnumSet<Type> TRAIT_TYPES = EnumSet.of(Type.ATTRIBUTE, Type.REALM, Type.IDENTITY);
//
//	/**
//	 * All trait type bits set (including TRAIT itself). Each trait AND this must
//	 * yield a set of size 1.
//	 */
//	public static final EnumSet<Type> ALL_TRAIT_TYPES = EnumSet.of(Type.ATTRIBUTE, Type.REALM, Type.IDENTITY,
//			Type.TRAIT, Type.OBSERVABILITY);

	/**
	 * A leaf declaration contains a name (e.g. 'elevation:Geography'); all others
	 * do not. When the name is not null, there still may be a negation or a
	 * semantic operator.
	 * 
	 * @return the concept name or null.
	 */
	String getName();

	/**
	 * The type contains all declared attributes for the concept. An empty type
	 * denotes an inconsistent concept. The k.IM validator ensures that any
	 * non-empty types are internally consistent.
	 * 
	 * @return the set of types
	 */
	Set<SemanticType> getType();

	/**
	 * The main observable, which must be unique. This is null in a leaf
	 * declaration, where {@link #getName()} returns a non-null value.
	 * 
	 * @return the main observable
	 */
	KKimConcept getObservable();

	KKimConcept getContext();

	KKimConcept getInherent();

	KKimConcept getMotivation();

	KKimConcept getCausant();

	KKimConcept getCaused();

	KKimConcept getCompresent();

	KKimConcept getComparisonConcept();

	String getAuthorityTerm();

	String getAuthority();

	UnarySemanticOperator getSemanticModifier();

	KKimConcept getRelationshipSource();

	KKimConcept getRelationshipTarget();

	List<KKimConcept> getTraits();

	List<KKimConcept> getRoles();

	boolean isTemplate();

	boolean isNegated();

	String getDefinition();

	boolean is(SemanticType type);

	/**
	 * 
	 * @param visitor
	 */
	void visit(Visitor visitor);

	/**
	 * If {@link #getExpressionType()} returns anything other than
	 * {@link Expression#SINGLETON}, the operands are other declarations this is
	 * part of a union or intersection with.
	 * 
	 * @return the operands
	 */
	List<KKimConcept> getOperands();

	/**
	 * Type of expression. If anything other than {@link Expression#SINGLETON},
	 * {@link #getOperands()} will return a non-empty list.
	 * 
	 * @return the expression type
	 */
	Expression getExpressionType();

	/**
	 * Get the fundamental type of this concept - one of the concrete trait or
	 * observable types, including configuration and extent.
	 * 
	 * @return
	 */
	SemanticType getFundamentalType();

	/**
	 * Get the 'co-occurrent' (during) event type if any.
	 * 
	 * @return
	 */
	KKimConcept getCooccurrent();

	/**
	 * Get the concept that this is stated to be adjacent to if any.
	 * 
	 * @return
	 */
	KKimConcept getAdjacent();

	/**
	 * Return a string suitable for naming a k.IM object after this concept.
	 * 
	 * @return
	 */
	String getCodeName();

	SemanticRole getDistributedInherent();

	boolean isTraitObservable();

	/**
	 * Return any temporal inherency for this occurrent ('during each').
	 * 
	 * @return
	 */
	KKimConcept getTemporalInherent();

}
