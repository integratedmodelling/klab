package org.integratedmodelling.kdl.api;

import java.util.Collection;

public interface IKdlStatement {

	/**
	 * True if there are any errors
	 * 
	 * @return
	 */
	boolean isErrors();

	/**
	 * Return any error messages
	 * 
	 * @return
	 */
	Collection<String> getErrors();

}
