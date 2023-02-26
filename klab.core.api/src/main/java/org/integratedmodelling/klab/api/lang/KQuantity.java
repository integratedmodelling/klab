package org.integratedmodelling.klab.api.lang;

/**
 * Just a number with units.
 * 
 * @author ferdinando.villa
 *
 */
public interface KQuantity {
	
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
	
	/**
	 * 
	 * @return
	 */
	String getCurrency();
}
