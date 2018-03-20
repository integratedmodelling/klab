package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.klab.api.data.raw.IObjectData;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.owl.Observable;

public class Process extends DirectObservation implements IProcess {

    private Process(String name, Observable observable, Scale scale, IObjectData data, IMonitor monitor) {
        super(name, observable, scale, data, monitor);
        // TODO Auto-generated constructor stub
    }

    private static final long serialVersionUID = -5563009298178472641L;
    
    public static Process create(String name, IObservable observable, IScale scale, IObjectData data, ISubject context, IMonitor monitor) {
        return null;
    }

    @Override
    public Process next() {
      return (Process)super.next();
    }


}
