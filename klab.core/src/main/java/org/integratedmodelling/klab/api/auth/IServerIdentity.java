package org.integratedmodelling.klab.api.auth;

import java.util.Date;

public abstract interface IServerIdentity extends IRuntimeIdentity {
    
    String getName();
    
    Date getBootTime();
}
