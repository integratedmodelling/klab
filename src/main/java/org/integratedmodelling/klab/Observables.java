package org.integratedmodelling.klab;

import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.services.IObservableService;
import org.integratedmodelling.klab.owl.ObservableBuilder;

public enum Observables implements IObservableService {

    INSTANCE;

    @Override
    public Builder declare(IConcept main) {
        return new ObservableBuilder(main);
    }

    @Override
    public Builder declare(String main, IConcept parent) {
        return new ObservableBuilder(main, parent);
    }

    @Override
    public Builder declare(String main, Set<Type> type) {
        return new ObservableBuilder(main, type);
    }

}
