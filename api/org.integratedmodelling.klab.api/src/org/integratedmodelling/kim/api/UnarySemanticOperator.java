package org.integratedmodelling.kim.api;

/**
 * All the semantic operators available in k.IM.
 * 
 * @author Ferd
 *
 */
public enum UnarySemanticOperator {

    PRESENCE("presence of"),
    PROPORTION("proportion of", "in"),
    RATIO("ratio of", "to"),
    DISTANCE("distance to"),
    PROBABILITY("probability of"),
    UNCERTAINTY("uncertainty of"),
    COUNT("count of"),
    VALUE("value of", "over"),
    OCCURRENCE("occurrence of"),
    ASSESSMENT("assessment of"),
    OBSERVABILITY("observability of"),
    MAGNITUDE("magnitude of"),
    TYPE("type of");

    public String[] declaration;

    UnarySemanticOperator(String... decl) {
        this.declaration = decl;
    }
}