package org.integratedmodelling.klab.engine.events;

import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.services.AgentServiceCheck;
import org.integratedmodelling.klab.engine.services.HubUserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import io.micrometer.core.lang.NonNull;

@Component
public class UserEventListener implements ApplicationListener<GenericUserSpringEvent<HubUserProfile, Session>>{

	@Autowired AgentServiceCheck service;
	
	@Override
	public void onApplicationEvent(@NonNull final GenericUserSpringEvent<HubUserProfile, Session> event) {
		UserEventLogin.class.equals(event.getClass());
	}

}
