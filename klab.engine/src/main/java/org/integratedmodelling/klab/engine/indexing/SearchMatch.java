package org.integratedmodelling.klab.engine.indexing;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.BinarySemanticOperator;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.SemanticModifier;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.api.services.IIndexingService;
import org.integratedmodelling.klab.rest.SearchMatch.TokenClass;

public class SearchMatch implements IIndexingService.Match {

    String id;
    String name;
    String description = "";
    float score;
    Type matchType;
    Map<String, String> indexableFields = new HashMap<>();
    Set<IKimConcept.Type> conceptType = EnumSet.noneOf(IKimConcept.Type.class);
    Set<IKimConcept.Type> semantics = EnumSet.noneOf(IKimConcept.Type.class);
    UnarySemanticOperator unaryOperator = null;
    BinarySemanticOperator binaryOperator = null;
    ValueOperator valueOperator = null;
    SemanticModifier modifier = null;

    boolean isAbstract = false;

    public org.integratedmodelling.klab.rest.SearchMatch getReference() {
        org.integratedmodelling.klab.rest.SearchMatch ret = new org.integratedmodelling.klab.rest.SearchMatch();
        ret.setId(this.id);
        ret.setDescription(this.description);
        ret.getSemanticType().addAll(this.semantics);
        ret.setMatchType(this.matchType);
        return ret;
    }
    
    public SearchMatch() {
    }

    public SearchMatch(org.integratedmodelling.klab.rest.SearchMatch descriptor) {
        this.id = descriptor.getId();
        this.description = descriptor.getDescription();
        this.score = 1;
        this.semantics.addAll(descriptor.getSemanticType());
        this.matchType = descriptor.getMatchType();
    }

    public SearchMatch(Type matchType, Set<org.integratedmodelling.kim.api.IKimConcept.Type> conceptType) {
        this.matchType = matchType;
        this.conceptType.addAll(conceptType);
    }

    public SearchMatch(UnarySemanticOperator op) {
        this.unaryOperator = op;
        this.matchType = Type.PREFIX_OPERATOR;
        this.id = this.name = op.declaration[0];
    }

    public SearchMatch(ValueOperator op) {
        this.valueOperator = op;
        this.matchType = Type.VALUE_OPERATOR;
        this.id = this.name = op.declaration;
    }

    public SearchMatch(BinarySemanticOperator op) {
        this.binaryOperator = op;
        this.matchType = Type.INFIX_OPERATOR;
        this.id = this.name = op.name().toLowerCase();
    }

    public SearchMatch(SemanticModifier op) {
        this.modifier = op;
        this.matchType = Type.MODIFIER;
        this.id = this.name = modifier.declaration[0];
    }

    @Override
    public String getId() {
        return id;
    }

    public TokenClass getTokenClass() {

        if (this.modifier != null) {
            switch(this.modifier) {
            case ADJACENT_TO:
            case BY:
            case CAUSED_BY:
            case CAUSING:
            case CONTAINED_IN:
            case CONTAINING:
            case DOWN_TO:
            case DURING:
            case FOR:
            case WITH:
            case WITHIN:
            case OF:
                break;

            case WITHOUT:
                // only for concepts
                break;

            case GREATER:
            case GREATEREQUAL:
            case LESS:
            case LESSEQUAL:
            case MINUS:
            case OVER:
            case TIMES:
            case PLUS:
                return TokenClass.DOUBLE;
            case IN:
                return this.semantics.contains(IKimConcept.Type.MONEY) || this.semantics.contains(IKimConcept.Type.MONETARY_VALUE)
                        ? TokenClass.CURRENCY
                        : TokenClass.UNIT;
            case PER:
                // also, should be contextual - spatial and/or temporal only
                return TokenClass.UNIT;
            case IS:
            case SAMEAS:
                // numeric, boolean or concept
                break;
            case WHERE:
                // another contextualizer = parenthesis
                break;
            default:
                break;
            }
        }

        return TokenClass.TOKEN;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public float getScore() {
        return score;
    }

    @Override
    public Type getMatchType() {
        return matchType;
    }

    @Override
    public Set<IKimConcept.Type> getConceptType() {
        return conceptType;
    }

    @Override
    public Map<String, String> getIndexableFields() {
        return indexableFields;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setMatchType(Type matchType) {
        this.matchType = matchType;
    }

    public void setIndexableFields(Map<String, String> indexableFields) {
        this.indexableFields = indexableFields;
    }

    public void setConceptType(Set<IKimConcept.Type> conceptType) {
        this.conceptType = conceptType;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    public Set<IKimConcept.Type> getSemantics() {
        return semantics;
    }

    public void setSemantics(Set<IKimConcept.Type> semantics) {
        this.semantics = semantics;
    }

    public UnarySemanticOperator getUnaryOperator() {
        return unaryOperator;
    }

    public void setUnaryOperator(UnarySemanticOperator unaryOperator) {
        this.unaryOperator = unaryOperator;
    }

    public BinarySemanticOperator getBinaryOperator() {
        return binaryOperator;
    }

    public void setBinaryOperator(BinarySemanticOperator binaryOperator) {
        this.binaryOperator = binaryOperator;
    }

    public SemanticModifier getModifier() {
        return modifier;
    }

    public void setModifier(SemanticModifier modifier) {
        this.modifier = modifier;
    }

    @Override
    public String toString() {
        return "SearchMatch [id=" + id + ", matchType=" + matchType + ", semantics=" + semantics + "]";
    }

}
