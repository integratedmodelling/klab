package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.kim.api.IKimClassification;
import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.kim.Classification;
import org.integratedmodelling.klab.utils.Pair;

public class KimClassification extends KimStatement implements IKimClassification {

    private static final long serialVersionUID = -1224375911697989510L;

    private boolean discretization;
    private List<Pair<IKimConcept, IKimClassifier>> classifiers = new ArrayList<>();
    
    public KimClassification(Classification classification, boolean discretization, IKimStatement parent) {
        super(classification, parent);
        this.discretization = discretization;
        // TODO
    }

    @Override
    public Iterator<Pair<IKimConcept, IKimClassifier>> iterator() {
        return classifiers.iterator();
    }

    @Override
    public boolean isDiscretization() {
        return discretization;
    }

}
