package org.integratedmodelling.opencpu.adapters;

import java.util.Collection;

import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;

@ResourceAdapter(version = Version.CURRENT, canCreateEmpty = true, handlesFiles = false, type = OpenCPUAdapter.ID)
public class OpenCPUAdapter implements IResourceAdapter {

    public static final String ID = "opencpu";
    
    @Override
    public String getName() {
        return ID;
    }

    @Override
    public IResourceValidator getValidator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResourcePublisher getPublisher() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResourceEncoder getEncoder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResourceImporter getImporter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResourceCalculator getCalculator(IResource resource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IPrototype> getResourceConfiguration() {
        // TODO Auto-generated method stub
        return null;
    }


}
