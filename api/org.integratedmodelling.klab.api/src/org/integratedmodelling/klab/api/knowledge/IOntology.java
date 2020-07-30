/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.api.knowledge;

import java.io.File;
import java.util.Collection;
import java.util.Set;

import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * Ontologies are not first-class objects in k.LAB. All retrieval of concepts
 * should be done through INamespace.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IOntology {

	/**
	 * The official URI of this ontology.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getURI();

	/**
	 * The simple name of this ontology.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

	/**
	 * Iterate over all concepts
	 *
	 * @return an iterator over all the concepts contained in the ontology.
	 */
	Collection<IConcept> getConcepts();

	/**
	 * Iterate over all properties
	 *
	 * @return an iterator over all the properties contained in the ontology.
	 */
	Collection<IProperty> getProperties();

	/**
	 * Return a concept, or null if not found.
	 *
	 * @param ID the concept's ID
	 * @return the concept or null
	 */
	IConcept getConcept(String ID);

	/**
	 * Return a property, or null if not found. The ID of the corresponding
	 * observable concept for a relationship may be passed as well as that of a
	 * straight property defined in OWL or k.IM.
	 *
	 * @param ID the property's ID
	 * @return the property or null
	 */
	IProperty getProperty(String ID);

	/**
	 * Create (if needed) and return the concept corresponding to the passed
	 * authority and identity, whose ID must be stable across all instances, using
	 * the closest authority service found on the k.LAB network.
	 * 
	 * @param authority
	 * @param authorityIdentity
	 * @return
	 */
	IConcept getIdentity(String authority, String authorityIdentity);

	/**
	 * Write the ontology to the passed physical location. If writeImported is true,
	 * also write all imported ontologies with the same URI prefix using sensible
	 * file names.
	 *
	 * @param file          a {@link java.io.File} object.
	 * @param writeImported if true, export also all the imports recursively.
	 * @return true if write completed OK.
	 * @throws org.integratedmodelling.klab.exceptions.KlabException
	 */
	boolean write(File file, boolean writeImported) throws KlabException;

	/**
	 * Define the ontology from a collection of axioms. Must work incrementally.
	 *
	 * @param axioms a {@link java.util.Collection} object.
	 * @return a list of error messages if any happened. Should not throw
	 *         exceptions.
	 *
	 *         TODO provide a quicker define(Axiom ... axioms)
	 */
	Collection<String> define(Collection<IAxiom> axioms);

	/**
	 * Return the number of (named, useful) concepts, hopefully quickly.
	 *
	 * @return number of concepts
	 */
	int getConceptCount();

	/**
	 * Return the number of (named, useful) properties, hopefully quickly.
	 *
	 * @return number of properties
	 */
	int getPropertyCount();

	/**
	 * Return any metadata or an empty metadata object.
	 *
	 * @return a {@link org.integratedmodelling.klab.api.knowledge.IMetadata}
	 *         object.
	 */
	IMetadata getMetadata();

	/**
	 * Return the imports, either direct (recursive = false) or the full closure.
	 * 
	 * @param recursive
	 * @return
	 */
	Set<IOntology> getImports(boolean recursive);

}
