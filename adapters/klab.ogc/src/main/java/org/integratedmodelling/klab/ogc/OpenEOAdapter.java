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
import org.integratedmodelling.klab.openeo.OpenEOCalculator;
import org.integratedmodelling.klab.openeo.OpenEOEncoder;
import org.integratedmodelling.klab.openeo.OpenEOImporter;
import org.integratedmodelling.klab.openeo.OpenEOResourcePublisher;
import org.integratedmodelling.klab.openeo.OpenEOResourceValidator;

@ResourceAdapter(type = OpenEOAdapter.ID, version = Version.CURRENT, canCreateEmpty = true, handlesFiles = false)
public class OpenEOAdapter implements IResourceAdapter {

    public static final String ID = "openeo";
    
    @Override
    public String getName() {
        return ID;
    }

    @Override
    public IResourceValidator getValidator() {
        return new OpenEOResourceValidator();
    }

    @Override
    public IResourcePublisher getPublisher() {
        return new OpenEOResourcePublisher();
    }

    @Override
    public IResourceEncoder getEncoder() {
        return new OpenEOEncoder();
    }

    @Override
    public IResourceImporter getImporter() {
        return new OpenEOImporter();
    }

    @Override
    public IResourceCalculator getCalculator(IResource resource) {
        return new OpenEOCalculator();
    }

    @Override
    public Collection<IPrototype> getResourceConfiguration() {
        return Collections.singleton(new Prototype(
                Dataflows.INSTANCE.declare(getClass().getClassLoader().getResource("ogc/prototypes/openeo.kdl"))
                        .getActuators().iterator().next(),
                null));
    }

}
