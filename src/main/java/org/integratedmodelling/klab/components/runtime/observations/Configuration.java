package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.components.runtime.RuntimeContext;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.owl.Observable;

public class Configuration extends CountableObservation implements IConfiguration {

    public Configuration(String name, Observable observable, Scale scale, RuntimeContext context) {
        super(name, observable, scale, context);
        // TODO Auto-generated constructor stub
    }

    private static final long serialVersionUID = -7911688766542253051L;

    @Override
    public Configuration next() {
      return (Configuration)super.next();
    }



}
