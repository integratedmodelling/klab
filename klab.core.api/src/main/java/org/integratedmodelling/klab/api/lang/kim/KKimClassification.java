package org.integratedmodelling.klab.api.lang.kim;

import org.integratedmodelling.klab.api.collections.impl.Pair;

public interface KKimClassification extends KKimStatement, Iterable<Pair<KKimConcept, KKimClassifier>> {
    
    /**
     * True if this was declared and validated as a discretization.
     * 
     * @return true if discretization
     */
    boolean isDiscretization();
}
