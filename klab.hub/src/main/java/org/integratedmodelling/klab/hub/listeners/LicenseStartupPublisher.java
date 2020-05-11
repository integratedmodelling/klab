package org.integratedmodelling.klab.hub.listeners;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class LicenseStartupPublisher implements ApplicationEventPublisherAware {
	
	private ApplicationEventPublisher publisher;
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}
	
    public void publish(LicenseStartupReady licenseStartupReady) {
        this.publisher.publishEvent(licenseStartupReady);
    }

}
