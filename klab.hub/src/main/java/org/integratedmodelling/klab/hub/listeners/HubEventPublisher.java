package org.integratedmodelling.klab.hub.listeners;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class HubEventPublisher<E extends ApplicationEvent>  implements ApplicationEventPublisherAware{

	private ApplicationEventPublisher publisher;
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}
	
    public void publish(E event) {
        this.publisher.publishEvent(event);
    }

}
