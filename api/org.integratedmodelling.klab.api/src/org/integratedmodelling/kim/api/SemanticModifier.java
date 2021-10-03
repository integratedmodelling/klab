package org.integratedmodelling.kim.api;

import org.integratedmodelling.kim.api.IKimConcept.ObservableRole;

/**
 * Semantic subsetters for concepts. Should only include the first part; the remaining (unit and
 * value operators) are superseded by the appropriate enums and must be removed as soon as the new
 * search/composer system is in place.
 * 
 * @author Ferd
 *
 */
public enum SemanticModifier {

    WITHIN(ObservableRole.CONTEXT, "within"),
    OF(ObservableRole.INHERENT, "of"),
    FOR(ObservableRole.GOAL, "for"),
    WITH(ObservableRole.COMPRESENT, "with"),
    CAUSED_BY(ObservableRole.CAUSANT, "caused by"),
    ADJACENT_TO(ObservableRole.ADJACENT, "adjacent to"),
    CONTAINED_IN(null, "contained in"),
    CONTAINING(null, "containing"),
    CAUSING(ObservableRole.CAUSED, "causing"),
    DURING(ObservableRole.COOCCURRENT, "during"),
    LINKING(ObservableRole.RELATIONSHIP_SOURCE, "linking"),
    TO(ObservableRole.RELATIONSHIP_TARGET, "to"),

    /*
     * TODO remove these
     */
    @Deprecated
    IN(null, "in"),
    @Deprecated
    PER(null, "per"),

    // Observable modifiers. TODO remove these in favor of ValueOperator
    @Deprecated
    BY(null, "by"),
    @Deprecated
    DOWN_TO(null, "down to"),
    @Deprecated
    GREATER(null, ">"),
    @Deprecated
    LESS(null, "<"),
    @Deprecated
    GREATEREQUAL(null, ">="),
    @Deprecated
    LESSEQUAL(null, "<="),
    @Deprecated
    IS(null, "="),
    @Deprecated
    SAMEAS(null, "=="),
    @Deprecated
    WITHOUT(null, "without"),
    @Deprecated
    WHERE(null, "where"),
    @Deprecated
    PLUS(null, "plus"),
    @Deprecated
    MINUS(null, "minus"),
    @Deprecated
    TIMES(null, "times"),
    @Deprecated
    OVER(null, "over");

    public String[] declaration;
    public ObservableRole role;
    
    SemanticModifier(ObservableRole role, String... decl) {
        this.declaration = decl;
        this.role = role;
    }

    public static SemanticModifier forCode(String code) {
        for (SemanticModifier val : values()) {
            if (code.equals(val.declaration[0])) {
                return val;
            }
        }
        return null;
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
