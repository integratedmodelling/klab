package org.integratedmodelling.kim.api;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

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
		 * Predicates are traits, roles and domains.
		 */
		PREDICATE,
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
		INTERSECTION,
		/**
		 * Specifier for values; affects validation of currencies
		 */
		MONETARY_VALUE,
		/**
		 * Makes an attribute a rescaling transformation, which does not preserve
		 * observation semantics
		 */
		RESCALING,
		/**
		 * A process that defines the change of its inherent quality.
		 */
		CHANGE,
		/**
		 * A quality that describes the speed of change of its inherent quality.
		 */
		RATE,
		/**
		 * An event that results from a change of value in the inherent quality.
		 */
		CHANGED,
		/**
		 * Concept that have the syntax of authority references (with the uppercase
		 * namespace) get this type even if not recognized by an online authority (in
		 * which case they won't have the IDENTITY type but will still have this, so
		 * that the syntactic validation won't fail).
		 */
		AUTHORITY_IDENTITY;

		public boolean isNumeric() {
			return IKimConcept.CONTINUOUS_QUALITY_TYPES.contains(this);
		}

		public boolean isQuality() {
			return IKimConcept.ALL_QUALITY_TYPES.contains(this);
		}

		public boolean admitsUnits() {
			return this == EXTENSIVE_PROPERTY || this == INTENSIVE_PROPERTY || this == NUMEROSITY;
		}

		public boolean admitsCurrency() {
			return this == MONETARY_VALUE;
		}

		public boolean isCountable() {
			return IKimConcept.DIRECT_OBSERVABLE_TYPES.contains(this);
		}

		public boolean isPredicate() {
			return this == ROLE || IKimConcept.TRAIT_TYPES.contains(this);
		}

		public boolean isTrait() {
			return IKimConcept.TRAIT_TYPES.contains(this);
		}

	}

	/**
	 * Roles of each component of an observable expression, to ease modifications
	 * (e.g. in builders) and inspect specific clauses. The conceptual components
	 * can be used to selectively remove parts of the observable in observable
	 * inspection. The structural components are used to report on admissible tokens
	 * during interactive building of observables.
	 * 
	 * FIXME this has inconsistent coverage and should be reorganized along with the
	 * APIs that use it.
	 */
	public enum ObservableRole {

		// conceptual components
		OBSERVABLE(false, ""), TRAIT(false, ""), ROLE(false, ""),

		/*
		 * these correspond to modifiers. FIXME make this consistent with others
		 * eventually - just mention SemanticModifier instead of each of them
		 * individually. The SEMANTIC_MODIFIER role is added for correct use, referring
		 * to all below.
		 */
		CONTEXT(true, "within"), INHERENT(true, "of"), ADJACENT(true, "adjacent to"), CAUSED(true, "caused by"),
		CAUSANT(true, "causing"), COMPRESENT(true, "with"), GOAL(true, "for"), COOCCURRENT(true, "during"),
		TEMPORAL_INHERENT(true, "during each"), RELATIONSHIP_SOURCE(true, "linking"), RELATIONSHIP_TARGET(true, "to"),

		// other structural components
		SEMANTIC_MODIFIER(false, ""), VALUE_OPERATOR(false, ""), UNIT(false, "in"), DISTRIBUTED_UNIT(false, "per"),
		CURRENCY(false, "in"), LOGICAL_OPERATOR(true, ""), INLINE_VALUE(false, ""), UNARY_OPERATOR(true, ""),
		BINARY_OPERATOR(true, ""),
		/** grouping scope for parenthesized logical expression */
		GROUP_OPEN(false, ""), GROUP_CLOSE(false, "");

		/**
		 * If true, this represents an operator that can have a complex logical
		 * expression as argument.
		 */
		public boolean subsumesObservable;
		public String kimDeclaration;

		ObservableRole(boolean subsumesObservable, String kimDeclaration) {
			this.subsumesObservable = subsumesObservable;
			this.kimDeclaration = kimDeclaration;
		}

		public boolean appliesTo(Type type) {
			switch (this) {
			case ADJACENT:
				return type.isCountable();
			case CAUSANT:
				break;
			case CAUSED:
				break;
			case COMPRESENT:
				break;
			case CONTEXT:
				return type != Type.SUBJECT && type != Type.AGENT;
			case COOCCURRENT:
				break;
			case GOAL:
				break;
			case GROUP_CLOSE:
				break;
			case GROUP_OPEN:
				break;
			case INHERENT:
				return type != Type.SUBJECT && type != Type.AGENT;
			case INLINE_VALUE:
				break;
			case LOGICAL_OPERATOR:
				break;
			case RELATIONSHIP_SOURCE:
			case RELATIONSHIP_TARGET:
				return type == Type.RELATIONSHIP;
			case ROLE:
				break;
			case TEMPORAL_INHERENT:
				break;
			case TRAIT:
				break;
			case UNARY_OPERATOR:
				break;
			case UNIT:
				break;
			case VALUE_OPERATOR:
				break;
			default:
				break;

			}
			return true;
		}
	}

	/**
	 * All declarable concept bits set. Each observable AND this must yield a set of
	 * size 1.
	 */
	public static final EnumSet<Type> DECLARABLE_TYPES = EnumSet.of(Type.QUALITY, Type.SUBJECT, Type.AGENT, Type.EVENT,
			Type.CONFIGURATION, Type.DOMAIN, Type.RELATIONSHIP, Type.EXTENT, Type.PROCESS, Type.ATTRIBUTE, Type.REALM,
			Type.IDENTITY, Type.ROLE);

	public static final EnumSet<Type> MODELABLE_TYPES = EnumSet.of(Type.QUALITY, Type.SUBJECT, Type.AGENT, Type.EVENT,
			Type.CONFIGURATION, Type.RELATIONSHIP, Type.PROCESS);

	/**
	 * Qualities that are naturally inherent and should not be allowed to have
	 * explicit inherency but just context.
	 */
	public static final EnumSet<Type> INHERENT_QUALITIES = EnumSet.of(Type.PROPORTION, Type.PROBABILITY, Type.DISTANCE,
			Type.VALUE, Type.OCCURRENCE, Type.PRESENCE, Type.UNCERTAINTY, Type.NUMEROSITY, Type.OBSERVABILITY,
			Type.RATE);

	public static final Set<Type> OPERATOR_TYPES = EnumSet.of(Type.CHANGE, Type.NUMEROSITY, Type.DISTANCE,
			/* FIXME MISSING: LEVEL */ Type.MAGNITUDE, Type.OBSERVABILITY, Type.OCCURRENCE, Type.PRESENCE,
			Type.PROBABILITY, Type.PROPORTION, Type.RATIO, Type.CLASS, Type.UNCERTAINTY, Type.VALUE,
			Type.MONETARY_VALUE);

	/**
	 * All quality type bits sets (not QUALITY itself). Each quality AND this must
	 * yield a set of size 1.
	 */
	public static final EnumSet<Type> QUALITY_TYPES = EnumSet.of(Type.CLASS, Type.QUANTITY, Type.ENERGY, Type.ENTROPY,
			Type.LENGTH, Type.MASS, Type.VOLUME, Type.WEIGHT, Type.MONEY, Type.DURATION, Type.AREA, Type.ACCELERATION,
			Type.PRIORITY, Type.ELECTRIC_POTENTIAL, Type.CHARGE, Type.RESISTANCE, Type.RESISTIVITY, Type.PRESSURE,
			Type.ANGLE, Type.VELOCITY, Type.TEMPERATURE, Type.VISCOSITY, Type.UNCERTAINTY, Type.RATIO, Type.PROPORTION,
			Type.PROBABILITY, Type.NUMEROSITY, Type.DISTANCE, Type.VALUE, Type.MONETARY_VALUE, Type.OCCURRENCE,
			Type.PRESENCE, Type.AMOUNT, Type.RATE);

	/**
	 * All quality type bits sets including QUALITY itself. Each quality AND this
	 * must yield a set of size 0.
	 */
	public static final EnumSet<Type> ALL_QUALITY_TYPES = EnumSet.of(Type.CLASS, Type.QUALITY, Type.QUANTITY,
			Type.ENERGY, Type.ENTROPY, Type.LENGTH, Type.MASS, Type.VOLUME, Type.WEIGHT, Type.MONEY, Type.DURATION,
			Type.AREA, Type.ACCELERATION, Type.PRIORITY, Type.ELECTRIC_POTENTIAL, Type.CHARGE, Type.RESISTANCE,
			Type.RESISTIVITY, Type.PRESSURE, Type.ANGLE, Type.VELOCITY, Type.TEMPERATURE, Type.VISCOSITY,
			Type.UNCERTAINTY, Type.RATIO, Type.PROPORTION, Type.PROBABILITY, Type.NUMEROSITY, Type.DISTANCE, Type.VALUE,
			Type.OCCURRENCE, Type.PRESENCE, Type.AMOUNT, Type.RATE, Type.MONETARY_VALUE);

	/**
	 * All qualities that are expressed through a continuous numeric state.
	 */
	public static final EnumSet<Type> CONTINUOUS_QUALITY_TYPES = EnumSet.of(Type.QUANTITY, Type.ENERGY, Type.ENTROPY,
			Type.LENGTH, Type.MASS, Type.VOLUME, Type.WEIGHT, Type.MONEY, Type.DURATION, Type.AREA, Type.ACCELERATION,
			Type.PRIORITY, Type.ELECTRIC_POTENTIAL, Type.CHARGE, Type.RESISTANCE, Type.RESISTIVITY, Type.PRESSURE,
			Type.ANGLE, Type.VELOCITY, Type.TEMPERATURE, Type.VISCOSITY, Type.UNCERTAINTY, Type.RATIO, Type.PROPORTION,
			Type.PROBABILITY, Type.NUMEROSITY, Type.DISTANCE, Type.VALUE, Type.OCCURRENCE, Type.PRESENCE, Type.AMOUNT,
			Type.MAGNITUDE, Type.RATE, Type.MONETARY_VALUE);

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
	 * Everything we can write a model for
	 */
	public final static EnumSet<Type> BASE_MODELABLE_TYPES = EnumSet.of(Type.SUBJECT, Type.EVENT, Type.RELATIONSHIP,
			Type.PROCESS, Type.QUALITY, Type.AGENT, Type.TRAIT, Type.CONFIGURATION);

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

	UnarySemanticOperator getSemanticModifier();

	IKimConcept getRelationshipSource();

	IKimConcept getRelationshipTarget();

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

	ObservableRole getDistributedInherent();

	boolean isTraitObservable();

	/**
	 * Return any temporal inherency for this occurrent ('during each').
	 * 
	 * @return
	 */
	IKimConcept getTemporalInherent();

	IKimConcept getValidParent();

}
