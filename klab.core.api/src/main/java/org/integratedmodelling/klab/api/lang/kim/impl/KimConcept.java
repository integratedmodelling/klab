package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.SemanticRole;
import org.integratedmodelling.klab.api.knowledge.SemanticType;
import org.integratedmodelling.klab.api.lang.UnarySemanticOperator;
import org.integratedmodelling.klab.api.lang.kim.KKimConcept;

/**
 * A IKimConcept is the declaration of a concept, i.e. a semantic expression
 * built out of known concepts and conforming to k.IM semantic constraints.
 * Concept expressions compile to this structure, which retains the final
 * concept names only as fully qualified names. External infrastructure can
 * create the actual concepts that a reasoner can operate on.
 * 
 * @author ferdinando.villa
 *
 */
public class KimConcept extends KimStatement implements KKimConcept {

    private static final long serialVersionUID = 8531431719010407385L;

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<SemanticType> getType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKimConcept getObservable() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKimConcept getContext() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKimConcept getInherent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKimConcept getMotivation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKimConcept getCausant() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKimConcept getCaused() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKimConcept getCompresent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKimConcept getComparisonConcept() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAuthorityTerm() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAuthority() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UnarySemanticOperator getSemanticModifier() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKimConcept getRelationshipSource() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKimConcept getRelationshipTarget() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<KKimConcept> getTraits() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<KKimConcept> getRoles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isTemplate() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isNegated() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getDefinition() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean is(SemanticType type) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<KKimConcept> getOperands() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Expression getExpressionType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SemanticType getFundamentalType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKimConcept getCooccurrent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKimConcept getAdjacent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getCodeName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SemanticRole getDistributedInherent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isTraitObservable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public KKimConcept getTemporalInherent() {
        // TODO Auto-generated method stub
        return null;
    }


}
