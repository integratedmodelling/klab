package org.integratedmodelling.klab.engine.services.engine.resources;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.engine.IContextScope;
import org.integratedmodelling.klab.api.engine.IEngineService.ResourceManager;
import org.integratedmodelling.klab.api.engine.IScope;

public class ResourceDefaultService implements ResourceManager {

    @Override
    public IResource resolve(String urn, IScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource contextualize(IResource originalResource, IContextScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IKlabData getData(IResource contextualizedResource, IScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

}
