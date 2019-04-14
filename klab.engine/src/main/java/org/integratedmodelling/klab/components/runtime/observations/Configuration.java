package org.integratedmodelling.klab.components.runtime.observations;

import java.util.Collection;

import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.components.runtime.RuntimeContext;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

public class Configuration extends CountableObservation implements IConfiguration {

	private Collection<IObservation> targets;
	
    public Configuration(String name, Observable observable, Scale scale, RuntimeContext context) {
        super(name, observable, scale, context);
    }

    /**
     * Called by the runtime context just after creation, to set the targets before 
     * the dataflow is run.
     * 
     * @param configurationTargets
     */
	public void setTargets(Collection<IObservation> configurationTargets) {
		this.targets = configurationTargets;
	}

	@Override
	public Collection<IObservation> getTargetObservations() {
		return targets;
	}

	@Override
	public boolean is(Class<?> cls) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T as(Class<?> cls) {
		// TODO Auto-generated method stub
		return null;
	}

}
