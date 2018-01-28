package org.integratedmodelling.klab.api.auth;

import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public abstract interface IRuntimeIdentity extends IIdentity {

    /**
     * A runtime identity exposes a monitor to report conditions and affect
     * contextualization.
     *  
     * @return
     */
    IMonitor getMonitor();
    
}
