package org.integratedmodelling.klab.api.lang.kim.impl;

import java.lang.module.ResolutionException;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.collections.impl.Range;
import org.integratedmodelling.klab.api.knowledge.KArtifact.Type;
import org.integratedmodelling.klab.api.lang.ValueOperator;
import org.integratedmodelling.klab.api.lang.kim.KKimConcept;
import org.integratedmodelling.klab.api.lang.kim.KKimObservable;

public class KimObservable extends KimStatement implements KKimObservable {

    private static final long serialVersionUID = -727467882879783393L;

    @Override
    public KKimConcept getMain() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Range getRange() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getUnit() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getCurrency() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFormalName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getValue() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getDefaultValue() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<ResolutionException> getResolutionExceptions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Pair<ValueOperator, Object>> getValueOperators() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasAttributeIdentifier() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isOptional() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getModelReference() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Type getNonSemanticType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDefinition() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getCodeName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isGeneric() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isGlobal() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isExclusive() {
        // TODO Auto-generated method stub
        return false;
    }

}
