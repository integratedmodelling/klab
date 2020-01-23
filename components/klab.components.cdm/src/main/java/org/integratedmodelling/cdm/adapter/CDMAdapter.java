package org.integratedmodelling.cdm.adapter;

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

@ResourceAdapter(type = "cdm", version = Version.CURRENT)
public class CDMAdapter implements IResourceAdapter {

	@Override
	public String getName() {
		return "cdm";
	}

	@Override
	public IResourceValidator getValidator() {
		return new CDMValidator();
	}

	@Override
	public IResourcePublisher getPublisher() {
		return new CDMPublisher();
	}

	@Override
	public IResourceEncoder getEncoder() {
		return new CDMEncoder();
	}

	@Override
	public IResourceImporter getImporter() {
		return new CDMImporter();
	}

	@Override
	public IPrototype getResourceConfiguration() {
		return new Prototype(Dataflows.INSTANCE
				.declare(getClass().getClassLoader()
						.getResource("components/org.integratedmodelling.cdm/adapter/cdm.kdl"))
				.getActuators().iterator().next(), null);
	}

	@Override
	public IResourceCalculator getCalculator(IResource resource) {
		// TODO Auto-generated method stub
		return null;
	}

}
