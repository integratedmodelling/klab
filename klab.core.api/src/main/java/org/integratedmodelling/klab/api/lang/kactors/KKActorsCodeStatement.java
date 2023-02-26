package org.integratedmodelling.klab.api.lang.kactors;

import org.integratedmodelling.klab.api.lang.KStatement;

/**
 * Any k.Actors code element, including whole behaviors. Actual statements are
 * {@link KKActorsStatement} and represent individual executable instructions.
 * 
 * @author Ferd
 *
 */
public interface KKActorsCodeStatement extends KStatement {

	String getTag();

}
