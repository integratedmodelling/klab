package org.integratedmodelling.klab.engine.events;

import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.services.HubUserProfile;
import org.integratedmodelling.klab.rest.ScaleReference;
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
    
    public void logout(final HubUserProfile profile, final Session session, final boolean forced) {
    	final UserEventLogout event = new UserEventLogout(profile, session, forced);
    	applicationEventPublisher.publishEvent(event);
    }
    
    public void history(final HubUserProfile profile, final Session session, SessionActivity activity) {
        final UserEventHistory event = new UserEventHistory(profile, session);
        event.setActivity(activity);
        applicationEventPublisher.publishEvent(event);
    }

    public void scale(HubUserProfile profile, Session session, ScaleReference scale) {
        final UserEventScale event = new UserEventScale(profile, session);
        event.setScale(scale);
        applicationEventPublisher.publishEvent(event);
    }

    public void context(HubUserProfile profile, Session session, ISubject context) {
        final UserEventContext event = new UserEventContext(profile, session);
        event.setContext(context);
        applicationEventPublisher.publishEvent(event);
    }

    public void observation(HubUserProfile profile, Session session, IObservation observation, ISubject context) {
        final UserEventObservation event = new UserEventObservation(profile, session);
        event.setContext(context);
        event.setObservation(observation);
        applicationEventPublisher.publishEvent(event);
    }
}
