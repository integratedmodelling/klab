package org.integratedmodelling.klab.observation;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.IContext;

public class Process extends DirectObservation implements IProcess {

    private Process(String name, IObservable observable, IScale scale, IContext context) {
        super(name, observable, scale, context);
        // TODO Auto-generated constructor stub
    }

    private static final long serialVersionUID = -5563009298178472641L;
    
    public static Process create(String name, IObservable observable, IScale scale, ISubject context) {
        return null;
    }

}
