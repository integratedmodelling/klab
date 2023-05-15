package org.integratedmodelling.klab.openeo;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class OpenEOCalculator implements IResourceCalculator {

    @Override
    public <T> T eval(IParameters<String> arguments, Class<? extends T> cls, IMonitor monitor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T eval(IContextualizationScope scope, ILocator locator, Class<? extends T> cls, IMonitor monitor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource getResource() {
        // TODO Auto-generated method stub
        return null;
    }

}
