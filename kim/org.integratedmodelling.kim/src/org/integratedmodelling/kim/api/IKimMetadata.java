package org.integratedmodelling.kim.api;

/**
 * Metadata in k.IM are multimaps - you can add multiple values for the same
 * field.
 * 
 * @author Ferd
 *
 */
public interface IKimMetadata extends IKimStatement {

	/**
	 * The value of this map becomes a List if multiple values
	 * are added.
	 * 
	 * @return
	 */
	public IParameters getData();
	
}
