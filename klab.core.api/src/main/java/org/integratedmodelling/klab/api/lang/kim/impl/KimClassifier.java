package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.ArrayList;

import org.integratedmodelling.klab.api.collections.impl.Range;
import org.integratedmodelling.klab.api.knowledge.KArtifact.Type;
import org.integratedmodelling.klab.api.lang.kim.KKimClassifier;
import org.integratedmodelling.klab.api.lang.kim.KKimConcept;
import org.integratedmodelling.klab.api.lang.kim.KKimDate;
import org.integratedmodelling.klab.api.lang.kim.KKimExpression;
import org.integratedmodelling.klab.api.lang.kim.KKimQuantity;

/**
 * Syntactic bean for a k.IM classifier, used in both classifications and
 * lookup tables.
 * 
 * @author ferdinando.villa
 *
 */
public class KimClassifier extends KimStatement implements KKimClassifier {

    private static final long serialVersionUID = 8284840092691497201L;

    @Override
    public boolean isCatchAll() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCatchAnything() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isNegated() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public KKimConcept getConceptMatch() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double getNumberMatch() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean getBooleanMatch() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<KKimClassifier> getClassifierMatches() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Range getIntervalMatch() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isNullMatch() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public KKimExpression getExpressionMatch() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getStringMatch() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<KKimConcept> getConceptMatches() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKimQuantity getQuantityMatch() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKimDate getDateMatch() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        return null;
    }


}
