package org.integratedmodelling.klab.api.knowledge;

import java.util.EnumSet;
import java.util.Set;

/**
 * Fundamental concept types for rapid classification.
 * 
 * @author ferdinando.villa
 *
 */
public enum SemanticType {
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
        return CONTINUOUS_QUALITY_TYPES.contains(this);
    }

    public boolean isQuality() {
        return ALL_QUALITY_TYPES.contains(this);
    }

    public boolean admitsUnits() {
        return this == EXTENSIVE_PROPERTY || this == INTENSIVE_PROPERTY || this == NUMEROSITY;
    }

    public boolean admitsCurrency() {
        return this == MONETARY_VALUE;
    }

    public boolean isCountable() {
        return DIRECT_OBSERVABLE_TYPES.contains(this);
    }

    public boolean isPredicate() {
        return this == ROLE || TRAIT_TYPES.contains(this);
    }

    public boolean isTrait() {
        return TRAIT_TYPES.contains(this);
    }
    
    /**
     * All declarable concept bits set. Each observable AND this must yield a set of
     * size 1.
     */
    public static final EnumSet<SemanticType> DECLARABLE_TYPES = EnumSet.of(SemanticType.QUALITY, SemanticType.SUBJECT, SemanticType.AGENT, SemanticType.EVENT,
            SemanticType.CONFIGURATION, SemanticType.DOMAIN, SemanticType.RELATIONSHIP, SemanticType.EXTENT, SemanticType.PROCESS, SemanticType.ATTRIBUTE, SemanticType.REALM,
            SemanticType.IDENTITY, SemanticType.ROLE);

    public static final EnumSet<SemanticType> MODELABLE_TYPES = EnumSet.of(SemanticType.QUALITY, SemanticType.SUBJECT, SemanticType.AGENT, SemanticType.EVENT,
            SemanticType.CONFIGURATION, SemanticType.RELATIONSHIP, SemanticType.PROCESS);

    /**
     * Qualities that are naturally inherent and should not be allowed to have
     * explicit inherency but just context.
     */
    public static final EnumSet<SemanticType> INHERENT_QUALITIES = EnumSet.of(SemanticType.PROPORTION, SemanticType.PROBABILITY, SemanticType.DISTANCE,
            SemanticType.VALUE, SemanticType.OCCURRENCE, SemanticType.PRESENCE, SemanticType.UNCERTAINTY, SemanticType.NUMEROSITY, SemanticType.OBSERVABILITY,
            SemanticType.RATE);

    public static final Set<SemanticType> OPERATOR_TYPES = EnumSet.of(SemanticType.CHANGE, SemanticType.NUMEROSITY, SemanticType.DISTANCE,
            /* FIXME MISSING: LEVEL */ SemanticType.MAGNITUDE, SemanticType.OBSERVABILITY, SemanticType.OCCURRENCE, SemanticType.PRESENCE,
            SemanticType.PROBABILITY, SemanticType.PROPORTION, SemanticType.RATIO, SemanticType.CLASS, SemanticType.UNCERTAINTY, SemanticType.VALUE,
            SemanticType.MONETARY_VALUE);

    /**
     * All quality type bits sets (not QUALITY itself). Each quality AND this must
     * yield a set of size 1.
     */
    public static final EnumSet<SemanticType> QUALITY_TYPES = EnumSet.of(SemanticType.CLASS, SemanticType.QUANTITY, SemanticType.ENERGY, SemanticType.ENTROPY,
            SemanticType.LENGTH, SemanticType.MASS, SemanticType.VOLUME, SemanticType.WEIGHT, SemanticType.MONEY, SemanticType.DURATION, SemanticType.AREA, SemanticType.ACCELERATION,
            SemanticType.PRIORITY, SemanticType.ELECTRIC_POTENTIAL, SemanticType.CHARGE, SemanticType.RESISTANCE, SemanticType.RESISTIVITY, SemanticType.PRESSURE,
            SemanticType.ANGLE, SemanticType.VELOCITY, SemanticType.TEMPERATURE, SemanticType.VISCOSITY, SemanticType.UNCERTAINTY, SemanticType.RATIO, SemanticType.PROPORTION,
            SemanticType.PROBABILITY, SemanticType.NUMEROSITY, SemanticType.DISTANCE, SemanticType.VALUE, SemanticType.MONETARY_VALUE, SemanticType.OCCURRENCE,
            SemanticType.PRESENCE, SemanticType.AMOUNT, SemanticType.RATE);

    /**
     * All quality type bits sets including QUALITY itself. Each quality AND this
     * must yield a set of size 0.
     */
    public static final EnumSet<SemanticType> ALL_QUALITY_TYPES = EnumSet.of(SemanticType.CLASS, SemanticType.QUALITY, SemanticType.QUANTITY,
            SemanticType.ENERGY, SemanticType.ENTROPY, SemanticType.LENGTH, SemanticType.MASS, SemanticType.VOLUME, SemanticType.WEIGHT, SemanticType.MONEY, SemanticType.DURATION,
            SemanticType.AREA, SemanticType.ACCELERATION, SemanticType.PRIORITY, SemanticType.ELECTRIC_POTENTIAL, SemanticType.CHARGE, SemanticType.RESISTANCE,
            SemanticType.RESISTIVITY, SemanticType.PRESSURE, SemanticType.ANGLE, SemanticType.VELOCITY, SemanticType.TEMPERATURE, SemanticType.VISCOSITY,
            SemanticType.UNCERTAINTY, SemanticType.RATIO, SemanticType.PROPORTION, SemanticType.PROBABILITY, SemanticType.NUMEROSITY, SemanticType.DISTANCE, SemanticType.VALUE,
            SemanticType.OCCURRENCE, SemanticType.PRESENCE, SemanticType.AMOUNT, SemanticType.RATE, SemanticType.MONETARY_VALUE);

    /**
     * All qualities that are expressed through a continuous numeric state.
     */
    public static final EnumSet<SemanticType> CONTINUOUS_QUALITY_TYPES = EnumSet.of(SemanticType.QUANTITY, SemanticType.ENERGY, SemanticType.ENTROPY,
            SemanticType.LENGTH, SemanticType.MASS, SemanticType.VOLUME, SemanticType.WEIGHT, SemanticType.MONEY, SemanticType.DURATION, SemanticType.AREA, SemanticType.ACCELERATION,
            SemanticType.PRIORITY, SemanticType.ELECTRIC_POTENTIAL, SemanticType.CHARGE, SemanticType.RESISTANCE, SemanticType.RESISTIVITY, SemanticType.PRESSURE,
            SemanticType.ANGLE, SemanticType.VELOCITY, SemanticType.TEMPERATURE, SemanticType.VISCOSITY, SemanticType.UNCERTAINTY, SemanticType.RATIO, SemanticType.PROPORTION,
            SemanticType.PROBABILITY, SemanticType.NUMEROSITY, SemanticType.DISTANCE, SemanticType.VALUE, SemanticType.OCCURRENCE, SemanticType.PRESENCE, SemanticType.AMOUNT,
            SemanticType.MAGNITUDE, SemanticType.RATE, SemanticType.MONETARY_VALUE);

    /**
     * All direct observables
     */
    public final static EnumSet<SemanticType> DIRECT_OBSERVABLE_TYPES = EnumSet.of(SemanticType.DIRECT_OBSERVABLE, SemanticType.SUBJECT,
            SemanticType.AGENT, SemanticType.EVENT, SemanticType.RELATIONSHIP, SemanticType.PROCESS, SemanticType.CONFIGURATION, SemanticType.COUNTABLE,
            /* FIXME ??? */SemanticType.ABSTRACT);

    /**
     * All base observables
     */
    public final static EnumSet<SemanticType> BASE_OBSERVABLE_TYPES = EnumSet.of(SemanticType.SUBJECT, SemanticType.EVENT, SemanticType.RELATIONSHIP,
            SemanticType.PROCESS, SemanticType.QUALITY, SemanticType.AGENT);

    /**
     * Everything we can write a model for
     */
    public final static EnumSet<SemanticType> BASE_MODELABLE_TYPES = EnumSet.of(SemanticType.SUBJECT, SemanticType.EVENT, SemanticType.RELATIONSHIP,
            SemanticType.PROCESS, SemanticType.QUALITY, SemanticType.AGENT, SemanticType.TRAIT, SemanticType.CONFIGURATION);

    /**
     * All trait type bits set (not TRAIT itself). Each trait AND this must yield a
     * set of size 1.
     */
    public static final EnumSet<SemanticType> TRAIT_TYPES = EnumSet.of(SemanticType.ATTRIBUTE, SemanticType.REALM, SemanticType.IDENTITY);

    /**
     * All trait type bits set (including TRAIT itself). Each trait AND this must
     * yield a set of size 1.
     */
    public static final EnumSet<SemanticType> ALL_TRAIT_TYPES = EnumSet.of(SemanticType.ATTRIBUTE, SemanticType.REALM, SemanticType.IDENTITY,
            SemanticType.TRAIT, SemanticType.OBSERVABILITY);


}
