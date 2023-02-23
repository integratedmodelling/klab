package org.integratedmodelling.klab.api.services.runtime;

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
		Silent, Normal, Verbose
	}

	/**
	 * The notifying identity
	 * 
	 * @return
	 */
	String getIdentity();

	/**
	 * This will be the string representation of the silly Java level, which was
	 * born before enums existed.
	 * 
	 * @return
	 */
	String getLevel();

	/**
	 * System time of notification
	 * 
	 * @return
	 */
	long getTimestamp();

	String getMessage();

	Type getType();
}