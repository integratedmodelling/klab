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

    NOT(new Type[]{Type.DENIABLE}, Type.TRAIT, "not"),
    PRESENCE(new Type[]{Type.COUNTABLE}, Type.QUALITY, "presence of"),
    // FIXME does not account for the different operands
    PROPORTION(new Type[]{Type.TRAIT, Type.QUANTIFIABLE}, Type.QUALITY, "proportion of", "in"),
    PERCENTAGE(
            new Type[]{Type.TRAIT, Type.QUANTIFIABLE}, Type.QUALITY, "percentage of",
            "in"),
    RATIO(new Type[]{Type.QUANTIFIABLE}, Type.QUALITY, "ratio of", "to"),

    // also must be geolocated
    DISTANCE(new Type[]{Type.COUNTABLE}, Type.QUALITY, "distance to"),
    PROBABILITY(new Type[]{Type.EVENT}, Type.QUALITY, "probability of"),
    UNCERTAINTY(
            new Type[]{Type.QUALITY}, Type.QUALITY, "uncertainty of"),
    COUNT(new Type[]{Type.COUNTABLE}, Type.QUALITY, "count of"),
    VALUE(
            new Type[]{Type.OBSERVABLE, Type.CONFIGURATION}, Type.QUALITY, "value of",
            "over"),
    MONETARY_VALUE(new Type[]{Type.OBSERVABLE, Type.CONFIGURATION}, Type.QUALITY, "monetary value of"),
    OCCURRENCE(
            new Type[]{Type.COUNTABLE}, Type.QUALITY, "occurrence of"),
    ASSESSMENT(new Type[]{Type.QUALITY}, Type.PROCESS, 
            "assessment of"),
    CHANGE(new Type[]{Type.QUALITY}, Type.PROCESS, "change in"),
    CHANGED(new Type[]{Type.QUALITY}, Type.EVENT, 
            "changed"),
    RATE(new Type[]{Type.QUALITY}, Type.QUALITY,
            "change rate of"),
//    OBSERVABILITY(new Type[]{Type.OBSERVABLE}, Type.QUALITY, 
//            "observability of"),
    MAGNITUDE(new Type[]{Type.QUANTIFIABLE}, Type.QUALITY, 
            "magnitude of"),
    LEVEL(new Type[]{Type.QUANTIFIABLE}, Type.CLASS, 
            "level of"),
    TYPE(new Type[]{Type.TRAIT}, Type.QUALITY, "type of");

    public String[] declaration;
    public Set<Type> allowedOperandTypes = EnumSet.noneOf(Type.class);
    public Type returnType;
    
    UnarySemanticOperator(Type[] allowedOpTypes, Type returnType, String... decl) {
        this.declaration = decl;
        this.returnType = returnType;
        for (Type type : allowedOpTypes) {
            allowedOperandTypes.add(type);
        }
    }

    public static UnarySemanticOperator forCode(String code) {
        for (UnarySemanticOperator val : values()) {
            if (code.equals(val.declaration[0])) {
                return val;
            }
        }
        return null;
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