package org.integratedmodelling.klab.engine.events;

import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.services.HubUserProfile;
import org.integratedmodelling.klab.rest.SessionActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    
    
    public void login(final HubUserProfile profile, final Session session) {
    	final UserEventLogin event = new UserEventLogin(profile, session);
    	applicationEventPublisher.publishEvent(event);
    }
    
    public void logout(final HubUserProfile profile, final Session session) {
    	final UserEventLogout event = new UserEventLogout(profile, session);
    	applicationEventPublisher.publishEvent(event);
    }
    
    public void history(final HubUserProfile profile, final Session session, SessionActivity activity) {
        final UserEventHistory event = new UserEventHistory(profile, session);
        event.setActivity(activity);
        applicationEventPublisher.publishEvent(event);
    }
}
