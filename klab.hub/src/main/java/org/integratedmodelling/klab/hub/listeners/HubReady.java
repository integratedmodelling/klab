package org.integratedmodelling.klab.hub.listeners;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

public class HubReady extends ApplicationEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5171484932474072198L;

	public HubReady(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
}
