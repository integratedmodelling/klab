package org.integratedmodelling.klab;

import java.util.List;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.services.IModelService;

public enum Models implements IModelService {

    INSTANCE;
    
    @Override
    public void releaseNamespace(String name) {
        // TODO remove all artifacts from local kbox
    }

    @Override
    public void index(IModel model) {
        
    }

    @Override
    public List<RankedModel> resolve(IObservable observable, IResolutionScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

}
