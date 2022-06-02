package org.integratedmodelling.klab.api.knowledge;

import java.util.Collection;

/**
 * The knowledge structure is the graph of individuals and object property
 * instances that constitutes the t-box in an ontology. In k.LAB this is only
 * relevant when vocabularies are used in namespaces and 'define' statements are
 * used to define and link any individuals created from their classes. This is
 * used for provenance and other purposes, but not for the main observation
 * functionalities in k.LAB.
 * 
 * @author Ferd
 *
 */
public interface IStructure {

	/**
	 * All the individuals in this structure. They may or may not be linked to each
	 * other.
	 * 
	 * @return
	 */
	Collection<IIndividual> getIndividuals();

	/**
	 * All the links building the RDF graph for the ontology structure. Can be empty
	 * in any ontology.
	 * 
	 * @return
	 */
	Collection<ILink> getLinks();

}
