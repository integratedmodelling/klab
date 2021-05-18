package org.integratedmodelling.klab.engine.events;

import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.services.HubUserProfile;

public class UserEventObservation extends UserEventContext{

    private IObservation observation;

    public UserEventObservation( HubUserProfile profile, Session session ) {
        super(profile, session);
        this.type = UserEventType.OBSERVATION;
    }

    public void setObservation(IObservation observation) {
        this.observation = observation;
    }

    public IObservation getObservation() {
        return observation;
    }
        
}
