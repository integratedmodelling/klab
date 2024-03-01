package org.integratedmodelling.klab.hub.listeners;

import org.springframework.context.ApplicationEvent;

public class LicenseStartupReady extends ApplicationEvent {

	private static final long serialVersionUID = -7958424747341745456L;

	public LicenseStartupReady(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

}