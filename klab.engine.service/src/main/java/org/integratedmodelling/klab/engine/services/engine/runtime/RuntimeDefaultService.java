package org.integratedmodelling.klab.engine.services.engine.runtime;

import org.integratedmodelling.klab.api.engine.IContextScope;
import org.integratedmodelling.klab.api.engine.IEngineService;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;

public class RuntimeDefaultService implements IEngineService.Runtime {

    @Override
    public <T extends IArtifact> T run(IDataflow<T> dataflow, IContextScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

}
