package org.integratedmodelling.controlcenter.api;

public interface IProduct {

	/**
	 * Name of product is a lowercase string, short and without spaces,
	 * corresponding to the directory where the product is hosted.
	 * 
	 * @return product ID
	 */
	String getId();

	/**
	 * Name of product is the user-readable name, potentially with more words but
	 * short and suitable for buttons or choice boxes.
	 * 
	 * @return product name
	 */
	String getName();
	
	
}
