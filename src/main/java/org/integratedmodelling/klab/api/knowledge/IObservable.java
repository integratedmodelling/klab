package org.integratedmodelling.klab.api.knowledge;

import java.util.Optional;

import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.services.IObservableService;

public interface IObservable extends ISemantic {

    String getName();

    /**
     * The canonical declaration in terms of the worldview, using a consistent structure so that
     * equality of declarations means equality of observables. Observables can always be reconstructed
     * by calling {@link IObservableService#declare(IConcept)}.
     * 
     * @return the k.IM declaration
     */
    String getDeclaration();

}
