package org.integratedmodelling.klab.services.engine.resources;

import java.io.Serializable;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.engine.IContextScope;
import org.integratedmodelling.klab.api.engine.IEngineService.ResourceManager;
import org.integratedmodelling.klab.api.engine.IScope;

public class ResourceDefaultService implements ResourceManager, Serializable {

    private static final long serialVersionUID = 4655348900403339285L;

    @Override
    public IBehavior resolveBehavior(String urn, IScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource resolveResource(String urn, IScope scope) {
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
