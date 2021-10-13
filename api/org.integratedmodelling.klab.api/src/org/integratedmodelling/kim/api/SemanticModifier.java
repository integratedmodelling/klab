package org.integratedmodelling.kim.api;

import java.util.EnumSet;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
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

    WITHIN(ObservableRole.CONTEXT, "within", EnumSet.of(Type.OBSERVABLE), EnumSet.of(Type.AGENT, Type.SUBJECT)),
    OF(ObservableRole.INHERENT, "of", EnumSet.of(Type.OBSERVABLE), EnumSet.of(Type.COUNTABLE)),
    FOR(ObservableRole.GOAL, "for", EnumSet.of(Type.OBSERVABLE), EnumSet.of(Type.OBSERVABLE)),
    WITH(ObservableRole.COMPRESENT, "with", EnumSet.of(Type.OBSERVABLE), EnumSet.of(Type.COUNTABLE)),
    CAUSED_BY(ObservableRole.CAUSANT, "caused by", EnumSet.of(Type.OBSERVABLE), EnumSet.of(Type.OBSERVABLE)),
    ADJACENT_TO(ObservableRole.ADJACENT, "adjacent to", EnumSet.of(Type.OBSERVABLE), EnumSet.of(Type.COUNTABLE)),
    CONTAINED_IN(null, "contained in", EnumSet.of(Type.COUNTABLE), EnumSet.of(Type.COUNTABLE)),
    CONTAINING(null, "containing", EnumSet.of(Type.COUNTABLE), EnumSet.of(Type.COUNTABLE)),
    CAUSING(ObservableRole.CAUSED, "causing", EnumSet.of(Type.PROCESS, Type.EVENT), EnumSet.of(Type.OBSERVABLE)),
    DURING(ObservableRole.COOCCURRENT, "during", EnumSet.of(Type.OBSERVABLE), EnumSet.of(Type.PROCESS, Type.EVENT)),
    LINKING(ObservableRole.RELATIONSHIP_SOURCE, "linking", EnumSet.of(Type.RELATIONSHIP), EnumSet.of(Type.COUNTABLE)),
    TO(ObservableRole.RELATIONSHIP_TARGET, "to", EnumSet.of(Type.RELATIONSHIP), EnumSet.of(Type.COUNTABLE)),

    /*
     * TODO remove these
     */
    @Deprecated
    IN(null, "in", null, null),
    @Deprecated
    PER(null, "per", null, null),

    // Observable modifiers. TODO remove these in favor of ValueOperator
    @Deprecated
    BY(null, "by", null, null),
    @Deprecated
    DOWN_TO(null, "down to", null, null),
    @Deprecated
    GREATER(null, ">", null, null),
    @Deprecated
    LESS(null, "<", null, null),
    @Deprecated
    GREATEREQUAL(null, ">=", null, null),
    @Deprecated
    LESSEQUAL(null, "<=", null, null),
    @Deprecated
    IS(null, "=", null, null),
    @Deprecated
    SAMEAS(null, "==", null, null),
    @Deprecated
    WITHOUT(null, "without", null, null),
    @Deprecated
    WHERE(null, "where", null, null),
    @Deprecated
    PLUS(null, "plus", null, null),
    @Deprecated
    MINUS(null, "minus", null, null),
    @Deprecated
    TIMES(null, "times", null, null),
    @Deprecated
    OVER(null, "over", null, null);

    public String[] declaration;
    public ObservableRole role;
    public Set<IKimConcept.Type> applicable;
    public Set<IKimConcept.Type> argument;
    
    SemanticModifier(ObservableRole role, String decl, Set<IKimConcept.Type> applicable, Set<IKimConcept.Type> argument) {
        this.declaration = new String[] {decl};
        this.role = role;
        this.applicable = applicable;
        this.argument = argument;
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
