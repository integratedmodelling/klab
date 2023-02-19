package org.integratedmodelling.klab.services.actors;

import org.integratedmodelling.klab.api.actors.IBehavior;

public class SessionAgent extends KAgent {

    public SessionAgent(String name) {
        super(name);
    }

    
    public SessionAgent(IBehavior application ) {
        super(application.getName());
    }

}
