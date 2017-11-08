/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any
 * other authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable
 * modular, collaborative, integrated development of interoperable data and model
 * components. For details, see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms
 * of the Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any
 * warranty; without even the implied warranty of merchantability or fitness for a
 * particular purpose. See the Affero General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite
 * 330, Boston, MA 02111-1307, USA. The license is also available at:
 * https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.api.knowledge;

import java.io.File;
import java.util.Collection;

import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * Ontologies are not first-class objects in k.LAB. All retrieval of concepts should be
 * done through INamespace.
 */
public interface IOntology  {
    

    /**
     * The official URI of this ontology.
     * 
     */
    String getURI();

    /**
     * The simple name of this ontology.
     * 
     * @return
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
     * @param ID
     *            the concept's ID
     * @return the concept or null
     */
    IConcept getConcept(String ID);

    /**
     * Return a property, or null if not found. The ID of the corresponding
     * observable concept for a relationship may be passed as well as that of
     * a straight property defined in OWL or k.IM.
     * 
     * @param ID
     *            the property's ID
     * @return the property or null
     */
    IProperty getProperty(String ID);

    /**
     * Write the ontology to the passed physical location. If writeImported is true, also
     * write all imported ontologies with the same URI prefix using sensible file names.
     * 
     * @param file
     * @param writeImported
     *            if true, export also all the imports recursively.
     * 
     * @return true if write completed OK.
     * @throws KlabException
     */
    boolean write(File file, boolean writeImported) throws KlabException;

    /**
     * Define the ontology from a collection of axioms. Must work incrementally.
     * 
     * @param axioms
     * @return a list of error messages if any happened. Should not throw exceptions.
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
     * @return
     */
    IMetadata getMetadata();


}
