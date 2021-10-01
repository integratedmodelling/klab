package org.integratedmodelling.kim.api;

/**
 * Semantic subsetters for concepts. Should only include the first part; the remaining (unit and
 * value operators) are superseded by the appropriate enums and must be removed as soon as the new
 * search/composer system is in place.
 * 
 * @author Ferd
 *
 */
public enum SemanticModifier {

    // Concept modifiers. Keep these only. Add their admitted arguments and 
    WITHIN("within"),
    OF("of"),
    FOR("for"),
    WITH("with"),
    CAUSED_BY("caused by"),
    ADJACENT_TO("adjacent to"),
    CONTAINED_IN("contained in"),
    CONTAINING("containing"),
    CAUSING("causing"),
    DURING("during"),
    LINKING("linking"),
    TO("to"),

    /*
     * TODO remove these
     */
    @Deprecated
    IN("in"),
    @Deprecated
    PER("per"),

    // Observable modifiers. TODO remove these in favor of ValueOperator
    @Deprecated
    BY("by"),
    @Deprecated
    DOWN_TO("down to"),
    @Deprecated
    GREATER(">"),
    @Deprecated
    LESS("<"),
    @Deprecated
    GREATEREQUAL(">="),
    @Deprecated
    LESSEQUAL("<="),
    @Deprecated
    IS("="),
    @Deprecated
    SAMEAS("=="),
    @Deprecated
    WITHOUT("without"),
    @Deprecated
    WHERE("where"),
    @Deprecated
    PLUS("plus"),
    @Deprecated
    MINUS("minus"),
    @Deprecated
    TIMES("times"),
    @Deprecated
    OVER("over");

    public String[] declaration;

    SemanticModifier(String... decl) {
        this.declaration = decl;
    }

    public static SemanticModifier getModifier(String valueModifier) {
        for (SemanticModifier m : SemanticModifier.values()) {
            if (m.declaration[0].equals(valueModifier)) {
                return m;
            }
        }
        return null;
    }

}
