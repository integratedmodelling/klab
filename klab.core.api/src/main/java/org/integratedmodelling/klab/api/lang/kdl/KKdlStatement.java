package org.integratedmodelling.klab.api.lang.kdl;

import java.util.Collection;

import org.integratedmodelling.klab.api.lang.KStatement;

public interface KKdlStatement extends KStatement {

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
