package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.List;

import org.integratedmodelling.klab.api.lang.kim.KKimClassifier;
import org.integratedmodelling.klab.api.lang.kim.KKimTable;

public class KimTable extends KimStatement implements KKimTable {
    private static final long serialVersionUID = -8528877830924327222L;

    @Override
    public List<String> getHeaders() {
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
    public int getRowCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getColumnCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public KKimClassifier[] getRow(int i) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<KKimClassifier[]> getRows() {
        // TODO Auto-generated method stub
        return null;
    }


}
