package org.integratedmodelling.klab.data.adapters.container;

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

/**
 * A container resource is an empty box where to put provenance entities,
 * vocabularies and codelists for reference in other resources.
 * 
 * @author Ferd
 *
 */
@ResourceAdapter(type = ContainerResourceAdapter.ID, version = Version.CURRENT, canCreateEmpty = true, handlesFiles = true)
public class ContainerResourceAdapter implements IResourceAdapter {

	public static final String ID = "container";
	
	@Override
	public String getName() {
		return ID;
	}

	@Override
	public IResourceValidator getValidator() {
		return new ContainerResourceValidator();
	}

	@Override
	public IResourcePublisher getPublisher() {
		return new ContainerResourcePublisher();
	}

	@Override
	public IResourceEncoder getEncoder() {
		return new ContainerResourceEncoder();
	}

	@Override
	public IResourceImporter getImporter() {
		return new ContainerResourceImporter();
	}

	@Override
	public IResourceCalculator getCalculator(IResource resource) {
		return null;
	}

	@Override
	public Collection<IPrototype> getResourceConfiguration() {
		return Collections.singleton(new Prototype(
				Dataflows.INSTANCE.declare(getClass().getClassLoader().getResource("prototypes/container.kdl"))
						.getActuators().iterator().next(),
				null));
	}

}
