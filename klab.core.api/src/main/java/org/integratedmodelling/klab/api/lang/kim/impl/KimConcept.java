package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.SemanticRole;
import org.integratedmodelling.klab.api.knowledge.SemanticType;
import org.integratedmodelling.klab.api.lang.UnarySemanticOperator;
import org.integratedmodelling.klab.api.lang.kim.KKimConcept;

/**
 * A IKimConcept is the declaration of a concept, i.e. a semantic expression built out of known
 * concepts and conforming to k.IM semantic constraints. Concept expressions compile to this
 * structure, which retains the final concept names only as fully qualified names. External
 * infrastructure can create the actual concepts that a reasoner can operate on.
 * 
 * @author ferdinando.villa
 *
 */
public class KimConcept extends KimStatement implements KKimConcept {

    private static final long serialVersionUID = 8531431719010407385L;

    private SemanticRole semanticRole;
    private boolean traitObservable;
    private String name;
    private Set<SemanticType> type;
    private KKimConcept observable;
    private KKimConcept context;
    private KKimConcept inherent;
    private KKimConcept motivation;
    private KKimConcept causant;
    private KKimConcept caused;
    private KKimConcept compresent;
    private KKimConcept comparisonConcept;
    private String authorityTerm;
    private String authority;
    private UnarySemanticOperator semanticModifier;
    private KKimConcept relationshipSource;
    private KKimConcept relationshipTarget;
    private List<KKimConcept> traits;
    private List<KKimConcept> roles;
    private boolean template;
    private boolean negated;
    private String definition;
    private List<KKimConcept> operands;
    private Expression expressionType;
    private SemanticType fundamentalType;
    private KKimConcept cooccurrent;
    private KKimConcept adjacent;
    private String codeName;
    private KKimConcept temporalInherent;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Set<SemanticType> getType() {
        return this.type;
    }

    @Override
    public KKimConcept getObservable() {
        return this.observable;
    }

    @Override
    public KKimConcept getContext() {
        return this.context;
    }

    @Override
    public KKimConcept getInherent() {
        return this.inherent;
    }

    @Override
    public KKimConcept getMotivation() {
        return this.motivation;
    }

    @Override
    public KKimConcept getCausant() {
        return this.causant;
    }

    @Override
    public KKimConcept getCaused() {
        return this.caused;
    }

    @Override
    public KKimConcept getCompresent() {
        return this.compresent;
    }

    @Override
    public KKimConcept getComparisonConcept() {
        return this.comparisonConcept;
    }

    @Override
    public String getAuthorityTerm() {
        return this.authorityTerm;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    @Override
    public UnarySemanticOperator getSemanticModifier() {
        return this.semanticModifier;
    }

    @Override
    public KKimConcept getRelationshipSource() {
        return this.relationshipSource;
    }

    @Override
    public KKimConcept getRelationshipTarget() {
        return this.relationshipTarget;
    }

    @Override
    public List<KKimConcept> getTraits() {
        return this.traits;
    }

    @Override
    public List<KKimConcept> getRoles() {
        return this.roles;
    }

    @Override
    public boolean isTemplate() {
        return this.template;
    }

    @Override
    public boolean isNegated() {
        return this.negated;
    }

    @Override
    public String getDefinition() {
        return this.definition;
    }

    @Override
    public boolean is(SemanticType type) {
        return this.type.contains(type);
    }

    @Override
    public List<KKimConcept> getOperands() {
        return this.operands;
    }

    @Override
    public Expression getExpressionType() {
        return this.expressionType;
    }

    @Override
    public SemanticType getFundamentalType() {
        return this.fundamentalType;
    }

    @Override
    public KKimConcept getCooccurrent() {
        return this.cooccurrent;
    }

    @Override
    public KKimConcept getAdjacent() {
        return this.adjacent;
    }

    @Override
    public String getCodeName() {
        return this.codeName;
    }

    @Override
    public SemanticRole getDistributedInherent() {
        return this.semanticRole;
    }

    @Override
    public boolean isTraitObservable() {
        return this.traitObservable;
    }

    @Override
    public KKimConcept getTemporalInherent() {
        return this.temporalInherent;
    }

    public SemanticRole getSemanticRole() {
        return semanticRole;
    }

    public void setSemanticRole(SemanticRole semanticRole) {
        this.semanticRole = semanticRole;
    }

    public void setTraitObservable(boolean traitObservable) {
        this.traitObservable = traitObservable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Set<SemanticType> type) {
        this.type = type;
    }

    public void setObservable(KKimConcept observable) {
        this.observable = observable;
    }

    public void setContext(KKimConcept context) {
        this.context = context;
    }

    public void setInherent(KKimConcept inherent) {
        this.inherent = inherent;
    }

    public void setMotivation(KKimConcept motivation) {
        this.motivation = motivation;
    }

    public void setCausant(KKimConcept causant) {
        this.causant = causant;
    }

    public void setCaused(KKimConcept caused) {
        this.caused = caused;
    }

    public void setCompresent(KKimConcept compresent) {
        this.compresent = compresent;
    }

    public void setComparisonConcept(KKimConcept comparisonConcept) {
        this.comparisonConcept = comparisonConcept;
    }

    public void setAuthorityTerm(String authorityTerm) {
        this.authorityTerm = authorityTerm;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setSemanticModifier(UnarySemanticOperator semanticModifier) {
        this.semanticModifier = semanticModifier;
    }

    public void setRelationshipSource(KKimConcept relationshipSource) {
        this.relationshipSource = relationshipSource;
    }

    public void setRelationshipTarget(KKimConcept relationshipTarget) {
        this.relationshipTarget = relationshipTarget;
    }

    public void setTraits(List<KKimConcept> traits) {
        this.traits = traits;
    }

    public void setRoles(List<KKimConcept> roles) {
        this.roles = roles;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }

    public void setNegated(boolean negated) {
        this.negated = negated;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setOperands(List<KKimConcept> operands) {
        this.operands = operands;
    }

    public void setExpressionType(Expression expressionType) {
        this.expressionType = expressionType;
    }

    public void setFundamentalType(SemanticType fundamentalType) {
        this.fundamentalType = fundamentalType;
    }

    public void setCooccurrent(KKimConcept cooccurrent) {
        this.cooccurrent = cooccurrent;
    }

    public void setAdjacent(KKimConcept adjacent) {
        this.adjacent = adjacent;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public void setTemporalInherent(KKimConcept temporalInherent) {
        this.temporalInherent = temporalInherent;
    }

}
