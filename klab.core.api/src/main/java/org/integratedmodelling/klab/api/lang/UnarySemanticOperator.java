package org.integratedmodelling.klab.api.lang;

import java.util.EnumSet;
import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.SemanticType;

/**
 * All the semantic operators available in k.IM.
 * 
 * @author Ferd
 *
 */
public enum UnarySemanticOperator {

    NOT(new SemanticType[]{SemanticType.DENIABLE}, SemanticType.TRAIT, "not"),
    PRESENCE(new SemanticType[]{SemanticType.COUNTABLE}, SemanticType.QUALITY, "presence of"),
    // FIXME does not account for the different operands
    PROPORTION(new SemanticType[]{SemanticType.TRAIT, SemanticType.QUANTIFIABLE}, SemanticType.QUALITY, "proportion of", "in"),
    PERCENTAGE(
            new SemanticType[]{SemanticType.TRAIT, SemanticType.QUANTIFIABLE}, SemanticType.QUALITY, "percentage of",
            "in"),
    RATIO(new SemanticType[]{SemanticType.QUANTIFIABLE}, SemanticType.QUALITY, "ratio of", "to"),

    // also must be geolocated
    DISTANCE(new SemanticType[]{SemanticType.COUNTABLE}, SemanticType.QUALITY, "distance to"),
    PROBABILITY(new SemanticType[]{SemanticType.EVENT}, SemanticType.QUALITY, "probability of"),
    UNCERTAINTY(
            new SemanticType[]{SemanticType.QUALITY}, SemanticType.QUALITY, "uncertainty of"),
    COUNT(new SemanticType[]{SemanticType.COUNTABLE}, SemanticType.QUALITY, "count of"),
    VALUE(
            new SemanticType[]{SemanticType.OBSERVABLE, SemanticType.CONFIGURATION}, SemanticType.QUALITY, "value of",
            "over"),
    MONETARY_VALUE(new SemanticType[]{SemanticType.OBSERVABLE, SemanticType.CONFIGURATION}, SemanticType.QUALITY, "monetary value of"),
    OCCURRENCE(
            new SemanticType[]{SemanticType.COUNTABLE}, SemanticType.QUALITY, "occurrence of"),
    ASSESSMENT(new SemanticType[]{SemanticType.QUALITY}, SemanticType.PROCESS, 
            "assessment of"),
    CHANGE(new SemanticType[]{SemanticType.QUALITY}, SemanticType.PROCESS, "change in"),
    CHANGED(new SemanticType[]{SemanticType.QUALITY}, SemanticType.EVENT, 
            "changed"),
    RATE(new SemanticType[]{SemanticType.QUALITY}, SemanticType.QUALITY,
            "change rate of"),
    OBSERVABILITY(new SemanticType[]{SemanticType.OBSERVABLE}, SemanticType.QUALITY, 
            "observability of"),
    MAGNITUDE(new SemanticType[]{SemanticType.QUANTIFIABLE}, SemanticType.QUALITY, 
            "magnitude of"),
    LEVEL(new SemanticType[]{SemanticType.QUANTIFIABLE}, SemanticType.CLASS, 
            "level of"),
    TYPE(new SemanticType[]{SemanticType.TRAIT}, SemanticType.QUALITY, "type of");

    public String[] declaration;
    public Set<SemanticType> allowedOperandTypes = EnumSet.noneOf(SemanticType.class);
    public SemanticType returnType;
    
    UnarySemanticOperator(SemanticType[] allowedOpTypes, SemanticType returnType, String... decl) {
        this.declaration = decl;
        this.returnType = returnType;
        for (SemanticType type : allowedOpTypes) {
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
    
    public Set<SemanticType> getAllowedOperandTypes() {
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