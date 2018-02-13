package org.integratedmodelling.klab.observation;

import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.owl.Observable;

public class Configuration extends CountableObservation implements IConfiguration {

    protected Configuration(String name, Observable observable, Scale scale, IMonitor monitor) {
        super(name, observable, scale, monitor);
        // TODO Auto-generated constructor stub
    }

    private static final long serialVersionUID = -7911688766542253051L;

}
