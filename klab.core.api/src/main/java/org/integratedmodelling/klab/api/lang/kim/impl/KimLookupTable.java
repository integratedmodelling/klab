package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.List;

import org.integratedmodelling.klab.api.knowledge.KArtifact.Type;
import org.integratedmodelling.klab.api.lang.kim.KKimClassifier;
import org.integratedmodelling.klab.api.lang.kim.KKimLookupTable;
import org.integratedmodelling.klab.api.lang.kim.KKimTable;

public class KimLookupTable extends KimStatement implements KKimLookupTable {

    private static final long serialVersionUID = 1081054386576296191L;

    @Override
    public Type getLookupType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Argument> getArguments() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKimTable getTable() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isTwoWay() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<KKimClassifier> getRowClassifiers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<KKimClassifier> getColumnClassifiers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getLookupColumnIndex() {
        // TODO Auto-generated method stub
        return 0;
    }

}
