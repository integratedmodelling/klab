package org.integratedmodelling.kim.api;

import java.util.EnumSet;
import java.util.List;

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
public interface IKimConcept extends IKimStatement {

	enum Expression {
		SINGLETON, UNION, INTERSECTION
	}

	/**
	 * Fundamental concept types for rapid classification.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	enum Type {
		/**
		 * 
		 */
		OBSERVABLE,
		/**
		 * 
		 */
		QUALITY,
		/**
		 * 
		 */
		PROCESS,
		/**
		 * 
		 */
		SUBJECT,
		/**
		 * 
		 */
		EVENT,
		/**
		 * 
		 */
		RELATIONSHIP,
		/**
		 * 
		 */
		EXTENSIVE_PROPERTY,
		/**
		 * 
		 */
		INTENSIVE_PROPERTY,
		/**
		 * 
		 */
		TRAIT,
		/**
		 * 
		 */
		IDENTITY,
		/**
		 * 
		 */
		ATTRIBUTE,
		/**
		 * 
		 */
		REALM,
		/**
		 * 
		 */
		SUBJECTIVE,
		/**
		 * 
		 */
		INTERNAL,
		/**
		 * 
		 */
		ROLE,
		/**
		 * 
		 */
		DENIABLE,
		/**
		 * 
		 */
		CONFIGURATION,
		/**
		 * 
		 */
		ABSTRACT,
		/**
		 * 
		 */
		NOTHING,
		/**
		 * 
		 */
		ORDERING,
		/**
		 * 
		 */
		CLASS,
		/**
		 * 
		 */
		QUANTITY,
		/**
		 * 
		 */
		DOMAIN,
		/**
		 * 
		 */
		ENERGY,
		/**
		 * 
		 */
		ENTROPY,
		/**
		 * 
		 */
		LENGTH,
		/**
		 * 
		 */
		MASS,
		/**
		 * 
		 */
		VOLUME,
		/**
		 * 
		 */
		WEIGHT,
		/**
		 * 
		 */
		MONEY,
		/**
		 * 
		 */
		DURATION,
		/**
		 * 
		 */
		AREA,
		/**
		 * 
		 */
		ACCELERATION,
		/**
		 * 
		 */
		PRIORITY,
		/**
		 * 
		 */
		ELECTRIC_POTENTIAL,
		/**
		 * 
		 */
		CHARGE,
		/**
		 * 
		 */
		RESISTANCE,
		/**
		 * 
		 */
		RESISTIVITY,
		/**
		 * 
		 */
		PRESSURE,
		/**
		 * 
		 */
		ANGLE,
		/**
		 * 
		 */
		VELOCITY,
		/**
		 * 
		 */
		TEMPERATURE,
		/**
		 * 
		 */
		VISCOSITY,
		/**
		 * 
		 */
		AGENT,
		/**
		 * 
		 */
		FUNCTIONAL,
		/**
		 * 
		 */
		STRUCTURAL,
		/**
		 * 
		 */
		BIDIRECTIONAL,
		/**
		 * 
		 */
		UNIDIRECTIONAL,
		/**
		 * 
		 */
		DELIBERATIVE,
		/**
		 * 
		 */
		INTERACTIVE,
		/**
		 * 
		 */
		REACTIVE,
		/**
		 * 
		 */
		DIRECT_OBSERVABLE,
		/**
		 * 
		 */
		COUNTABLE,
		/**
		 * 
		 */
		UNCERTAINTY,
		/**
		 * 
		 */
		PROBABILITY,
		/**
		 * 
		 */
		PROPORTION,
		/**
		 * 
		 */
		PERCENTAGE,
		/**
		 * 
		 */
		NUMEROSITY,
		/**
		 * 
		 */
		DISTANCE,
		/**
		 * 
		 */
		RATIO,
		/**
		 * 
		 */
		VALUE,
		/**
		 * 
		 */
		OCCURRENCE,
		/**
		 * 
		 */
		PRESENCE,
		/**
		 * 
		 */
		EXTENT,
		/**
		 * 
		 */
		MACRO,
		/**
		 * 
		 */
		AMOUNT,
		// /**
		// *
		// */
		// ASSESSMENT,
		/**
		 * 
		 */
		OBSERVABILITY,
		/**
		 * Only for concept peers of non-semantic types: this should never appear in a
		 * declared concept
		 */
		CATEGORY,
		/**
		 * 
		 */
		MAGNITUDE,
		/**
		 * A quality that can be quantified numerically
		 */
		QUANTIFIABLE,
		/**
		 * Reserved for unions built from declarations
		 */
		UNION,
		/**
		 * Reserved for intersections built from declarations
		 */
		INTERSECTION

	}

	/**
	 * All declarable concept bits set. Each observable AND this must yield a set of
	 * size 1.
	 */
	public static final EnumSet<Type> DECLARABLE_TYPES = EnumSet.of(Type.QUALITY, Type.SUBJECT, Type.AGENT, Type.EVENT,
			Type.CONFIGURATION, Type.DOMAIN, Type.RELATIONSHIP, Type.EXTENT, Type.PROCESS, Type.ATTRIBUTE, Type.REALM,
			Type.IDENTITY, Type.ROLE);

	/**
	 * Qualities that are naturally inherent and should not be allowed to have
	 * explicit inherency but just context.
	 */
	public static final EnumSet<Type> INHERENT_QUALITIES = EnumSet.of(Type.PROPORTION, Type.PROBABILITY, Type.DISTANCE,
			Type.VALUE, Type.OCCURRENCE, Type.PRESENCE, Type.UNCERTAINTY, Type.NUMEROSITY, Type.OBSERVABILITY);

	/**
	 * All quality type bits sets (not QUALITY itself). Each quality AND this must
	 * yield a set of size 1.
	 */
	public static final EnumSet<Type> QUALITY_TYPES = EnumSet.of(Type.CLASS, Type.QUANTITY, Type.ENERGY, Type.ENTROPY,
			Type.LENGTH, Type.MASS, Type.VOLUME, Type.WEIGHT, Type.MONEY, Type.DURATION, Type.AREA, Type.ACCELERATION,
			Type.PRIORITY, Type.ELECTRIC_POTENTIAL, Type.CHARGE, Type.RESISTANCE, Type.RESISTIVITY, Type.PRESSURE,
			Type.ANGLE, Type.VELOCITY, Type.TEMPERATURE, Type.VISCOSITY, Type.UNCERTAINTY, Type.RATIO, Type.PROPORTION,
			Type.PROBABILITY, Type.NUMEROSITY, Type.DISTANCE, Type.VALUE, Type.OCCURRENCE, Type.PRESENCE, Type.AMOUNT);

	/**
	 * All quality type bits sets including QUALITY itself. Each quality AND this
	 * must yield a set of size 0.
	 */
	public static final EnumSet<Type> ALL_QUALITY_TYPES = EnumSet.of(Type.CLASS, Type.QUALITY, Type.QUANTITY,
			Type.ENERGY, Type.ENTROPY, Type.LENGTH, Type.MASS, Type.VOLUME, Type.WEIGHT, Type.MONEY, Type.DURATION,
			Type.AREA, Type.ACCELERATION, Type.PRIORITY, Type.ELECTRIC_POTENTIAL, Type.CHARGE, Type.RESISTANCE,
			Type.RESISTIVITY, Type.PRESSURE, Type.ANGLE, Type.VELOCITY, Type.TEMPERATURE, Type.VISCOSITY,
			Type.UNCERTAINTY, Type.RATIO, Type.PROPORTION, Type.PROBABILITY, Type.NUMEROSITY, Type.DISTANCE, Type.VALUE,
			Type.OCCURRENCE, Type.PRESENCE, Type.AMOUNT);

	/**
	 * All qualities that are expressed through a continuous numeric state.
	 */
	public static final EnumSet<Type> CONTINUOUS_QUALITY_TYPES = EnumSet.of(Type.QUANTITY, Type.ENERGY, Type.ENTROPY,
			Type.LENGTH, Type.MASS, Type.VOLUME, Type.WEIGHT, Type.MONEY, Type.DURATION, Type.AREA, Type.ACCELERATION,
			Type.PRIORITY, Type.ELECTRIC_POTENTIAL, Type.CHARGE, Type.RESISTANCE, Type.RESISTIVITY, Type.PRESSURE,
			Type.ANGLE, Type.VELOCITY, Type.TEMPERATURE, Type.VISCOSITY, Type.UNCERTAINTY, Type.RATIO, Type.PROPORTION,
			Type.PROBABILITY, Type.NUMEROSITY, Type.DISTANCE, Type.VALUE, Type.OCCURRENCE, Type.PRESENCE, Type.AMOUNT,
			Type.MAGNITUDE);

	/**
	 * All direct observables
	 */
	public final static EnumSet<Type> DIRECT_OBSERVABLE_TYPES = EnumSet.of(Type.DIRECT_OBSERVABLE, Type.SUBJECT,
			Type.AGENT, Type.EVENT, Type.RELATIONSHIP, Type.PROCESS, Type.CONFIGURATION, Type.COUNTABLE,
			/* FIXME ??? */Type.ABSTRACT);

	/**
	 * All base observables
	 */
	public final static EnumSet<Type> BASE_OBSERVABLE_TYPES = EnumSet.of(Type.SUBJECT, Type.EVENT, Type.RELATIONSHIP,
			Type.PROCESS, Type.QUALITY, Type.AGENT);

	/**
	 * All trait type bits set (not TRAIT itself). Each trait AND this must yield a
	 * set of size 1.
	 */
	public static final EnumSet<Type> TRAIT_TYPES = EnumSet.of(Type.ATTRIBUTE, Type.REALM, Type.IDENTITY);

	/**
	 * All trait type bits set (including TRAIT itself). Each trait AND this must
	 * yield a set of size 1.
	 */
	public static final EnumSet<Type> ALL_TRAIT_TYPES = EnumSet.of(Type.ATTRIBUTE, Type.REALM, Type.IDENTITY,
			Type.TRAIT, Type.OBSERVABILITY);

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
	EnumSet<Type> getType();

	/**
	 * The main observable, which must be unique. This is null in a leaf
	 * declaration, where {@link #getName()} returns a non-null value.
	 * 
	 * @return the main observable
	 */
	IKimConcept getObservable();

	IKimConcept getContext();

	IKimConcept getInherent();

	IKimConcept getMotivation();

	IKimConcept getCausant();

	IKimConcept getCaused();

	IKimConcept getCompresent();

	IKimConcept getComparisonConcept();

	String getAuthorityTerm();

	String getAuthority();

	UnarySemanticOperator getObservationType();

	List<IKimConcept> getTraits();

	List<IKimConcept> getRoles();

	boolean isTemplate();

	boolean isNegated();

	String getDefinition();

	boolean is(Type type);

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
	List<IKimConcept> getOperands();

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
	Type getFundamentalType();

	/**
	 * Get the 'co-occurrent' (during) event type if any.
	 * 
	 * @return
	 */
	IKimConcept getCooccurrent();

	/**
	 * Get the concept that this is stated to be adjacent to if any.
	 * 
	 * @return
	 */
	IKimConcept getAdjacent();

	/**
	 * Return a string suitable for naming a k.IM object after this concept.
	 * 
	 * @return
	 */
	String getCodeName();

}
