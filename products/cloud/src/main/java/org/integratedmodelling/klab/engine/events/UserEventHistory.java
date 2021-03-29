package org.integratedmodelling.klab.engine.events;

import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.services.HubUserProfile;
import org.integratedmodelling.klab.rest.SessionActivity;

public class UserEventHistory extends GenericUserEvent<HubUserProfile, Session> {
    
    private SessionActivity activity;

    public UserEventHistory( HubUserProfile profile, Session session ) {
        super(profile, session);
        this.type = UserEventType.HISTORY;
        // TODO Auto-generated constructor stub
    }

    public SessionActivity getActivity() {
        return activity;
    }

    public void setActivity(SessionActivity activity) {
        this.activity = activity;
    }

}
