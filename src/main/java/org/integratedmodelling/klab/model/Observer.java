package org.integratedmodelling.klab.model;

import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.klab.api.model.IObserver;

public class Observer extends KimObject implements IObserver {

    private static final long serialVersionUID = 2777161073171784334L;

    public Observer(IKimObserver statement) {
        super(statement);
    }
}
