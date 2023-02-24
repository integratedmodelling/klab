/**
 * 
 */
package org.integratedmodelling.klab.api.exceptions;

import org.integratedmodelling.klab.api.knowledge.KArtifact;

/**
 * The KlabResourceAccessException
 * 
 * @author Enrico Girotto
 *
 */
public class KResourceAccessException extends KException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1836731508724767114L;

	/**
	 * 
	 */
	public KResourceAccessException() {
	}

	/**
	 * @param message
	 * @param arg1
	 */
	public KResourceAccessException(String message, Throwable arg1) {
		super(message, arg1);
	}

	/**
	 * @param message
	 */
	public KResourceAccessException(String message) {
		super(message);
	}

	/**
	 * @param message
	 */
	public KResourceAccessException(Throwable message) {
		super(message);
	}

	/**
	 * @param message
	 * @param scope
	 */
	public KResourceAccessException(String message, KArtifact scope) {
		super(message, scope);
	}

	/**
	 * @param message
	 * @param scope
	 */
	public KResourceAccessException(Throwable message, KArtifact scope) {
		super(message, scope);
	}

}
