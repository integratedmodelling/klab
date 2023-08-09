package org.integratedmodelling.klab.components.network.model;

import java.util.Collection;
import java.util.Collections;

import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.kim.Prototype;

@ResourceAdapter(type = "NETWORK_ADAPTER", version = Version.CURRENT, canCreateEmpty = false, handlesFiles = true)
public class NetworkAdapter implements IResourceAdapter {

	@Override
	public String getName() {
		return "network";
	}

	@Override
	public IResourceValidator getValidator() {
		return new NetworkValidator();
	}

	@Override
	public IResourcePublisher getPublisher() {
		return new NetworkPublisher();
	}

	@Override
	public IResourceEncoder getEncoder() {
		return new NetworkEncoder();
	}

	@Override
	public IResourceImporter getImporter() {
		return new NetworkImporter();
	}

	@Override
	public IResourceCalculator getCalculator(IResource resource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IPrototype> getResourceConfiguration() {
		return Collections.singleton(new Prototype(
				Dataflows.INSTANCE.declare(getClass().getClassLoader().getResource("components/org.integratedmodelling.network/services/network.kdl"))
						.getActuators().iterator().next(),
				null));
	}
	
}