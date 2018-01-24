package org.integratedmodelling.klab.engine.annotations;

import java.util.Map;

import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class TestHandler implements Annotations.Handler {

    @Override
    public Object process(IKimObject target, Map<String, Object> arguments, IMonitor monitor) {
        
        // TODO Auto-generated method stub

        System.out.println("Dio bidoncino, running test on " + target.getName());
        
        return null;
    }

}
