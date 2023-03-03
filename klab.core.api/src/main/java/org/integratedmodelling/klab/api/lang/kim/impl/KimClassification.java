package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.lang.kim.KKimClassification;
import org.integratedmodelling.klab.api.lang.kim.KKimClassifier;
import org.integratedmodelling.klab.api.lang.kim.KKimConcept;

public class KimClassification extends KimStatement implements KKimClassification {

    private static final long serialVersionUID = 2314681226321826507L;
    
    private boolean discretization;
    private List<Pair<KKimConcept, KKimClassifier>> classifiers = new ArrayList<>();

    @Override
    public boolean isDiscretization() {
        return discretization;
    }

    @Override
    public List<Pair<KKimConcept, KKimClassifier>> getClassifiers() {
        return classifiers;
    }

    public void setDiscretization(boolean discretization) {
        this.discretization = discretization;
    }

    public void setClassifiers(List<Pair<KKimConcept, KKimClassifier>> classifiers) {
        this.classifiers = classifiers;
    }

}
