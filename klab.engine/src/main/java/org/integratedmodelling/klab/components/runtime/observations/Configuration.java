package org.integratedmodelling.klab.components.runtime.observations;

import java.util.Collection;

import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.INetwork;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.components.network.model.Network;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

public class Configuration extends DirectObservation implements IConfiguration {

	private Collection<IObservation> targets;

	public Configuration(String name, Observable observable, Scale scale, RuntimeScope context) {
		super(name, observable, scale, context);
	}
	
	protected Configuration(Configuration other) {
		super(other);
	}

	/**
	 * Called by the runtime context just after creation, to set the targets before
	 * the dataflow is run.
	 * 
	 * @param configurationTargets
	 */
	public void setTargets(Collection<IObservation> configurationTargets) {
		this.targets = configurationTargets;
		if (configurationTargets.iterator().next().iterator().next() instanceof IRelationship) {
			this.addPeer(new Network(this), INetwork.class);
		}
	}

	@Override
	public Collection<IObservation> getTargetObservations() {
		return targets;
	}

}
