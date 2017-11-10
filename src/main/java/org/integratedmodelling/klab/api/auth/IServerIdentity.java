package org.integratedmodelling.klab.api.auth;

import java.util.Date;

public abstract interface IServerIdentity extends IIdentity {
    
    String getName();
    
    Date getBootTime();
}
