package org.integratedmodelling.klab.kim;

import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.Kim.FunctionDescriptor;
import org.integratedmodelling.kim.model.Kim.UrnDescriptor;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class KimValidator implements Kim.Validator {

    IMonitor monitor;
    
    public KimValidator(IMonitor monitor) {
        this.monitor = monitor;
    }
    
    @Override
    public void synchronizeNamespaceWithRuntime(IKimNamespace namespace) {
        // TODO Auto-generated method stub
    }
    
    @Override
    public FunctionDescriptor classifyFunction(String functionId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UrnDescriptor classifyUrn(String urn) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long classifyCoreType(String string) {
        // TODO Auto-generated method stub
        return 0;
    }

}
