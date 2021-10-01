package org.integratedmodelling.kim.api;

import java.util.EnumSet;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;

/**
 * All the semantic operators available in k.IM.
 * 
 * @author Ferd
 *
 */
public enum UnarySemanticOperator {

    NOT(new Type[]{Type.DENIABLE}, "not"),
    PRESENCE(new Type[]{Type.COUNTABLE}, "presence of"),
    // FIXME does not account for the different operands
    PROPORTION(new Type[]{Type.TRAIT, Type.QUANTIFIABLE}, "proportion of", "in"),
    PERCENTAGE(
            new Type[]{Type.TRAIT, Type.QUANTIFIABLE}, "percentage of",
            "in"),
    RATIO(new Type[]{Type.QUANTIFIABLE}, "ratio of", "to"),

    // also must be geolocated
    DISTANCE(new Type[]{Type.COUNTABLE}, "distance to"),
    PROBABILITY(new Type[]{Type.EVENT}, "probability of"),
    UNCERTAINTY(
            new Type[]{Type.QUALITY}, "uncertainty of"),
    COUNT(new Type[]{Type.COUNTABLE}, "count of"),
    VALUE(
            new Type[]{Type.OBSERVABLE, Type.CONFIGURATION}, "value of",
            "over"),
    MONETARY_VALUE(new Type[]{Type.OBSERVABLE, Type.CONFIGURATION}, "monetary value of"),
    OCCURRENCE(
            new Type[]{Type.COUNTABLE}, "occurrence of"),
    ASSESSMENT(new Type[]{Type.QUALITY},
            "assessment of"),
    CHANGE(new Type[]{Type.QUALITY}, "change in"),
    CHANGED(new Type[]{Type.EVENT, Type.CHANGED},
            "changed"),
    RATE(new Type[]{Type.QUALITY, Type.RATE},
            "change rate of"),
    OBSERVABILITY(new Type[]{Type.OBSERVABLE},
            "observability of"),
    MAGNITUDE(new Type[]{Type.QUANTIFIABLE},
            "magnitude of"),
    LEVEL(new Type[]{Type.QUANTIFIABLE},
            "level of"),
    TYPE(new Type[]{Type.TRAIT}, "type of");

    public String[] declaration;
    public Set<Type> allowedOperandTypes = EnumSet.noneOf(Type.class);

    UnarySemanticOperator(Type[] allowedOpTypes, String... decl) {
        this.declaration = decl;
        for (Type type : allowedOpTypes) {
            allowedOperandTypes.add(type);
        }
    }

    public Set<Type> getAllowedOperandTypes() {
        return allowedOperandTypes;
    }

    public String getReferenceName(String conceptName, String other) {
        String ret = declaration[0].replaceAll(" ", "_") + "_" + conceptName;
        if (other != null) {
            ret += "_" + declaration[1] + "_" + other;
        }
        return ret;
    }

    public String getCodeName(String conceptName, String other) {
        String ret = declaration[0].replaceAll(" ", "-") + "-" + conceptName;
        if (other != null) {
            ret += "-" + declaration[1] + "-" + other;
        }
        return ret;
    }
}