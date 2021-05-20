package org.integratedmodelling.klab.engine.events;

import org.integratedmodelling.klab.engine.services.HubUserProfile;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.engine.runtime.Session;

public class UserEventContext extends GenericUserEvent<HubUserProfile, Session>{

    private ISubject context;

    public UserEventContext( HubUserProfile profile, Session session ) {
        super(profile, session);
        this.type = UserEventType.CONTEXT;
    }

    public void setContext(ISubject context) {
        this.context = context;
    }

    public ISubject getContext() {
        return context;
    }

}
