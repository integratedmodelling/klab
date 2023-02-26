package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.Iterator;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.lang.kim.KKimClassification;
import org.integratedmodelling.klab.api.lang.kim.KKimClassifier;
import org.integratedmodelling.klab.api.lang.kim.KKimConcept;

public class KimClassification extends KimStatement implements KKimClassification {

    private static final long serialVersionUID = 2314681226321826507L;

    @Override
    public Iterator<Pair<KKimConcept, KKimClassifier>> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isDiscretization() {
        // TODO Auto-generated method stub
        return false;
    }
    
}
