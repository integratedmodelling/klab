package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.components.runtime.RuntimeContext;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

public class Configuration extends CountableObservation implements IConfiguration {

    public Configuration(String name, Observable observable, Scale scale, RuntimeContext context) {
        super(name, observable, scale, context);
        // TODO Auto-generated constructor stub
    }

}
