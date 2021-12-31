package org.integratedmodelling.klab.api.data.general;

import org.integratedmodelling.klab.api.provenance.IArtifact.ValuePresentation;

/**
 * Values used in k.LAB states that may assume different representations besides
 * PODs and concepts should be reducible, so that a POD representation is always
 * possible.
 * 
 * For the time being in k.LAB these can be statistical distributions and
 * tables.
 * 
 * @author Ferd
 *
 */
public interface IReducible {

	/**
	 * A reducible must have one of these and it should not be VALUE.
	 * 
	 * @return
	 */
	ValuePresentation getValuePresentation();

	/**
	 * Return the most reduced form possible for the object. The returned value may
	 * be of the passed class, or another IReducible that contains any remaining
	 * unresolved keys. Passing force = true will reduce even the irreducible values
	 * and mandatorily return a value of the passed class.
	 * 
	 * @param <T>
	 * @param cls
	 * @return
	 */
	Object reduce(Class<?> cls, boolean forceReduction);

}
