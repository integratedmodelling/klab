package org.integratedmodelling.kim.api;

import org.eclipse.xtext.util.Pair;

public interface IKimClassification extends IKimStatement, Iterable<Pair<IKimConcept, IKimClassifier>> {
    
    /**
     * True if this was declared and validated as a discretization.
     * 
     * @return
     */
    boolean isDiscretization();
}
