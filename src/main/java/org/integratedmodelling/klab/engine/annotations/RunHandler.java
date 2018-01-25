package org.integratedmodelling.klab.engine.annotations;

import java.util.Map;

import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.api.auth.IIdentity.Type;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class RunHandler  implements Annotations.Handler {

    @Override
    public Object process(IKimObject target, Map<String, Object> arguments, IMonitor monitor) {

        if (monitor.getIdentity().is(Type.SCRIPT)) {

            // safe cast as the annotation is limited to observers
            IObserver observer = (IObserver)target;
            
            
            
            /*
             * TODO must check if the object's namespace is being "run" by the engine, and if so,
             * run the test.
             */
            
        }
        
        return null;
    }

}
