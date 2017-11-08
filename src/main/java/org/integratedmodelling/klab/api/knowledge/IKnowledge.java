/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.api.knowledge;

import java.util.Collection;

/**
 * IKnowledge defines the methods that are common to both IConcept and IProperty. As IConcept can be both concrete and abstract, and IInstances are always concrete, IKnowledge has all the methods that pertain to IRelationships of this with other IKnowledge objects. 
 * TODO consider adding specialization and generalization methods to specialize for concepts and properties.
 * @author  Ferdinando Villa
 */
public interface IKnowledge extends ISemantic {

    /**
     * All knowledge has a URI.
     * 
     * @return
     */
    String getURI();

    /**
     * All IKnowledge objects have a simple local name 
     * @return local name within namespace
     */
    String getLocalName();
    
    /**
     * The concept space is the simple name of the namespace or ontology the knowledge
     * comes from. 
     * @return
     */
    String getConceptSpace();

    /**
     * True if this is subsumed by the passed resource.
     * @param concept 
     * @return true if this knowledge is the passed knowledge
     */
    boolean is(ISemantic concept);

    /**
     * The notion of an abstract concept is extremely important in k.LAB: abstract knowledge
     * represents questions, where concrete knowledge represents answers (observations). 
     * @return true if concept is abstract (no instances can be created).
     */
    boolean isAbstract();

    /**
     * Get the semantic closure in the current environment (may be using a reasoner or
     * not).
     * 
     * @return the set of things we are.
     */
    Collection<IConcept> getSemanticClosure();

    /**
     * Return the domain of the owning namespace, or null if the 
     * knowledge is not part of any domain.
     * 
     * @return the concept describing our domain.
     */
    IConcept getDomain();

    /**
     * All knowledge comes from an ontology.
     * 
     * @return
     */
    IOntology getOntology();

    /**
     * All knowledge may have metadata, which may be empty but will never be null.
     * 
     * @return
     */
    IMetadata getMetadata();

}
