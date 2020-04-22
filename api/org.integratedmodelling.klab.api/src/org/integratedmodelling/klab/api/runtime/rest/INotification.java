package org.integratedmodelling.klab.api.runtime.rest;

public interface INotification {

	/**
	 * Additional classification info. Can be used for display or other purposes.
	 * Will be filled as things progress.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	public enum Type {
		None, Success, Failure
	}
	
	public enum Mode {
		Silent,
		Normal,
		Verbose
	}
	
	String getLevel();

	long getTimestamp();

	String getMessage();

	Type getType();
}