package org.integratedmodelling.klab.engine.events;

import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.services.HubUserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    
    
    public void login(final HubUserProfile profile, final Session session) {
    	final GenericUserEvent<HubUserProfile, Session> event = new UserEventLogin(profile, session);
    	applicationEventPublisher.publishEvent(event);
    }
    
    public void logout(final HubUserProfile profile, final Session session) {
    	final GenericUserEvent<HubUserProfile, Session> event = new UserEventLogout(profile, session);
    	applicationEventPublisher.publishEvent(event);
    }
}
