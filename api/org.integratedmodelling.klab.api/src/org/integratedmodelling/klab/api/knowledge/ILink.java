package org.integratedmodelling.klab.api.knowledge;

/**
 * Poor name, but I need an interface for the instance of an IProperty, which
 * links two IIndividuals, and IRelationship is taken by something much more
 * important. TODO review the name.
 * 
 * @author Ferd
 *
 */
public interface ILink {

	/**
	 * 
	 * @return
	 */
	IProperty getType();

	/**
	 * 
	 * @return
	 */
	IIndividual getSource();

	/**
	 * 
	 * @return
	 */
	IIndividual getDestination();

	/**
	 * 
	 * @return
	 */
	IMetadata getMetadata();
}
