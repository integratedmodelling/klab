/**
 * 
 */
package org.integratedmodelling.klab.api.exceptions;

import org.integratedmodelling.klab.api.knowledge.IArtifact;

/**
 * The KlabResourceAccessException
 * 
 * @author Enrico Girotto
 *
 */
public class KlabResourceAccessException extends KlabException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1836731508724767114L;

	/**
	 * 
	 */
	public KlabResourceAccessException() {
	}

	/**
	 * @param message
	 * @param arg1
	 */
	public KlabResourceAccessException(String message, Throwable arg1) {
		super(message, arg1);
	}

	/**
	 * @param message
	 */
	public KlabResourceAccessException(String message) {
		super(message);
	}

	/**
	 * @param message
	 */
	public KlabResourceAccessException(Throwable message) {
		super(message);
	}

	/**
	 * @param message
	 * @param scope
	 */
	public KlabResourceAccessException(String message, IArtifact scope) {
		super(message, scope);
	}

	/**
	 * @param message
	 * @param scope
	 */
	public KlabResourceAccessException(Throwable message, IArtifact scope) {
		super(message, scope);
	}

}
