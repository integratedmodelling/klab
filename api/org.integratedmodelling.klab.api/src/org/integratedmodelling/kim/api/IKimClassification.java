package org.integratedmodelling.kim.api;

import org.integratedmodelling.klab.utils.Pair;

public interface IKimClassification extends IKimStatement, Iterable<Pair<IKimConcept, IKimClassifier>> {
    
    /**
     * True if this was declared and validated as a discretization.
     * 
     * @return true if discretization
     */
    boolean isDiscretization();
}
