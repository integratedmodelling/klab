package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.components.runtime.RuntimeContext;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

public class Process extends DirectObservation implements IProcess {

    public Process(String name, Observable observable, Scale scale, RuntimeContext context) {
        super(name, observable, scale, context);
        // TODO Auto-generated constructor stub
    }

}
