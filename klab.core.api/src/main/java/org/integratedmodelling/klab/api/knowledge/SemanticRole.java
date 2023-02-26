package org.integratedmodelling.klab.api.knowledge;


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
public enum SemanticRole {

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

    SemanticRole(boolean subsumesObservable, String kimDeclaration) {
        this.subsumesObservable = subsumesObservable;
        this.kimDeclaration = kimDeclaration;
    }

    public boolean appliesTo(SemanticType type) {
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
            return type != SemanticType.SUBJECT && type != SemanticType.AGENT;
        case COOCCURRENT:
            break;
        case GOAL:
            break;
        case GROUP_CLOSE:
            break;
        case GROUP_OPEN:
            break;
        case INHERENT:
            return type != SemanticType.SUBJECT && type != SemanticType.AGENT;
        case INLINE_VALUE:
            break;
        case LOGICAL_OPERATOR:
            break;
        case RELATIONSHIP_SOURCE:
        case RELATIONSHIP_TARGET:
            return type == SemanticType.RELATIONSHIP;
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