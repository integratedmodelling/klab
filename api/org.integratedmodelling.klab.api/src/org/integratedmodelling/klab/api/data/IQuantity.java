package org.integratedmodelling.klab.api.data;

/**
 * Just a number with units.
 * 
 * @author ferdinando.villa
 *
 */
public interface IQuantity {
	
	/**
	 * May be an integer or a double.
	 * 
	 * @return
	 */
	Number getValue();

	/**
	 * Unvalidated unit as a string.
	 * 
	 * @return
	 */
	String getUnit();
}
