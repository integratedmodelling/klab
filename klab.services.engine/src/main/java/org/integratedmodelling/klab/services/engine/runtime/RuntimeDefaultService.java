package org.integratedmodelling.klab.services.engine.runtime;

import java.io.Serializable;

import org.integratedmodelling.klab.api.engine.IContextScope;
import org.integratedmodelling.klab.api.engine.IEngineService;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;

public class RuntimeDefaultService implements IEngineService.Runtime, Serializable {

    private static final long serialVersionUID = 7867128555056555430L;

    @Override
    public <T extends IArtifact> T run(IDataflow<T> dataflow, IContextScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

}
