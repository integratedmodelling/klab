package org.integratedmodelling.klab.api.runtime.rest;

public interface INotification {

	String getLevel();

	long getTimestamp();

	String getMessage();

}