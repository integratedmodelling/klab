package org.integratedmodelling.klab.engine.events;

import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.services.HubUserProfile;
import org.integratedmodelling.klab.rest.ScaleReference;

public class UserEventScale extends GenericUserEvent<HubUserProfile, Session> {

    private ScaleReference scale;

    public UserEventScale( HubUserProfile profile, Session session ) {
        super(profile, session);
        this.type = UserEventType.SCALE;
    }

    public void setScale(ScaleReference scale) {
        this.scale = scale;
    }

    public ScaleReference getScale() {
        return scale;
    }


}
