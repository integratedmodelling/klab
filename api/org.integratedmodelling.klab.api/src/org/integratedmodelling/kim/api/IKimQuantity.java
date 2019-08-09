package org.integratedmodelling.kim.api;

/**
 * Just a number with units, engendered by a nnn.unit statement where it's
 * accepted.
 * 
 * @author ferdinando.villa
 *
 */
public interface IKimQuantity extends IKimStatement {
	
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
