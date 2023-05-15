package org.integratedmodelling.klab.ogc;

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
import org.integratedmodelling.klab.stac.STACCalculator;
import org.integratedmodelling.klab.stac.STACEncoder;
import org.integratedmodelling.klab.stac.STACImporter;
import org.integratedmodelling.klab.stac.STACPublisher;
import org.integratedmodelling.klab.stac.STACValidator;

@ResourceAdapter(type = STACAdapter.ID, version = Version.CURRENT, canCreateEmpty = true, handlesFiles = false)
public class STACAdapter implements IResourceAdapter {

    public static final String ID = "stac";

    @Override
    public String getName() {
        return ID;
    }

    @Override
    public IResourceValidator getValidator() {
        return new STACValidator();
    }

    @Override
    public IResourcePublisher getPublisher() {
        return new STACPublisher();
    }

    @Override
    public IResourceEncoder getEncoder() {
        return new STACEncoder();
    }

    @Override
    public IResourceImporter getImporter() {
        return new STACImporter();
    }

    @Override
    public IResourceCalculator getCalculator(IResource resource) {
        return new STACCalculator();
    }

    @Override
    public Collection<IPrototype> getResourceConfiguration() {
        return Collections.singleton(new Prototype(
                Dataflows.INSTANCE.declare(getClass().getClassLoader().getResource("ogc/prototypes/stac.kdl"))
                        .getActuators().iterator().next(),
                null));    }
    
}
