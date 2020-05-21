package org.integratedmodelling.klab.hub.listeners;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class NewUserPublisher implements ApplicationEventPublisherAware{

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
		
	}
	
	private ApplicationEventPublisher publisher;
	
    public void publish(NewUserAdded event) {
        this.publisher.publishEvent(event);
    }
	

}
