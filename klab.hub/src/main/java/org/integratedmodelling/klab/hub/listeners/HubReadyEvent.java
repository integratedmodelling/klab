package org.integratedmodelling.klab.hub.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class HubReadyEvent {
	
	public HubReadyEvent(HubEventPublisher<LicenseStartupReady> licenseStartupPublisher,
			HubEventPublisher<NodeNetworkStartupReady> nodeNetworkPublisher) {
		super();
		this.licenseStartupPublisher = licenseStartupPublisher;
		this.nodeNetworkPublisher = nodeNetworkPublisher;
	}

	HubEventPublisher<LicenseStartupReady> licenseStartupPublisher;
	
	HubEventPublisher<NodeNetworkStartupReady> nodeNetworkPublisher;
	
	@EventListener
    public void startup(HubReady event) {
        licenseStartupPublisher.publish(new LicenseStartupReady(new Object()));
        nodeNetworkPublisher.publish(new NodeNetworkStartupReady(new Object()));
    }
}
