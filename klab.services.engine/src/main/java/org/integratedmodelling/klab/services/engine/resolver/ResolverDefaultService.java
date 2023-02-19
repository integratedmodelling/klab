package org.integratedmodelling.klab.services.engine.resolver;

import java.io.Serializable;

import org.integratedmodelling.klab.api.engine.IContextScope;
import org.integratedmodelling.klab.api.engine.IEngineService;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;

public class ResolverDefaultService implements IEngineService.Resolver, Serializable {

    private static final long serialVersionUID = 5362936085092151002L;

    @Override
    public IDataflow<?> resolve(Object observable, IContextScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

}
