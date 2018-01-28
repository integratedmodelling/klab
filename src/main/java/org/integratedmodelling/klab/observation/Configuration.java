package org.integratedmodelling.klab.observation;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.IContext;

public class Configuration extends DirectObservation {

    protected Configuration(String name, IObservable observable, IScale scale, IContext context) {
        super(name, observable, scale, context);
        // TODO Auto-generated constructor stub
    }

    private static final long serialVersionUID = -7911688766542253051L;

}
