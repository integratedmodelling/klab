package org.integratedmodelling.klab.api.runtime;

import org.integratedmodelling.klab.api.auth.ITaskIdentity;

public interface ITask extends ITaskIdentity {

    IContext finish();
    
}
