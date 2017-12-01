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
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * 
 * @author Ferdinando Villa
 */
public interface IConcept extends IKnowledge {

    /**
     * The quick version of the reasoner-based {@link IKnowledge#is(ISemantic)} uses
     * the {@link Type} enum to instantly assess the concept type without needing any
     * costly inference.
     * 
     * @param type
     * @return
     */
    boolean is(Type type);
    
    /**
     * Return a collection of all the direct parent classes.
     * @return a collection of parents
     */
    Collection<IConcept> getParents();

    /**
     * Return a collection of all direct and indirect parent classes. Should use a
     * reasoner if installed, and only follow transitive superclass relationships if not.
     * @return a collection of all the parent concepts recursively.
     */
    Collection<IConcept> getAllParents();

    /**
     * Return a collection of all direct subclasses.
     * @return all child concepts.
     */
    Collection<IConcept> getChildren();

    /**
     * Return all properties that are defined as having this in their domain
     * subproperties.
     * @return properties in our domain
     */
    Collection<IProperty> getProperties();

    /**
     * It includes all properties inherited by its superclasses, or having undefined
     * domains
     * @return all properties in our domain recursively
     */
    Collection<IProperty> getAllProperties();

    /**
     * Return the range of the passed property in the context of this concept, considering
     * restrictions.
     * @param property
     * @return the range of the passed property in our context
     * @throws KlabException
     */
    Collection<IConcept> getPropertyRange(IProperty property) throws KlabException;

    /**
     * Get the value that the passed data property is restricted to in this concept, or
     * null if there is no restriction.
     * 
     * @param property
     * @return value for data property in our context, or null
     * @throws KlabException
     */
    Object getValueOf(IProperty property) throws KlabException;

    /**
     * Return the (only) parent class, or throw an unchecked exception if there's more
     * than one parent.
     * @return the parent concept, assuming we have one only
     */
    IConcept getParent();

    /**
     * get the number of properties for this type
     * @param property
     * @return number of properties of this type in our context
     */
    int getPropertiesCount(String property);

    /**
     * @param c
     * @return least general common concept compared with c
     */
    IConcept getLeastGeneralCommonConcept(IConcept c);

    /**
     * Return the full set of concepts that are subsumed by this concept, using whatever
     * reasoning strategy is implemented or configured in.
     * 
     * This is used quite a bit in kbox queries, so it pays to make it fast and/or memoize
     * results.
     * 
     * @return all concepts we are
     */
    Set<IConcept> getSemanticClosure();

    /**
     * Return min,max cardinality of property when applied to this concept. -1 on either
     * end indicates no cardinality given.
     * 
     * TODO use a range object
     * @param property
     * @return cardinality
     */
    int[] getCardinality(IProperty property);

    /**
     * Called on an abstract concept to retrieve all the children that are concrete and
     * disjoint, meaning those for which is(this) is true but both a.is(b) and b.is(a) is
     * false.
     * 
     * @return all disjoint concrete direct children
     */
    Collection<IConcept> getDisjointConcreteChildren();

    /**
     * Get the property or properties that restricts the passed concept for this one, if
     * any.
     * 
     * @param target
     * @return the property that restricts this concept in our context
     */
    Collection<IProperty> findRestrictingProperty(IConcept target);

    /**
     * Return the concept's definition in terms of primitive concepts. This will
     * correspond to the fully qualified concept name for concepts that have been declared
     * directly, or to a formula recursively detailing any traits, context and/or inherent
     * types when the concept has been created by composing others. Concepts that are not
     * primary carry their definition in an annotation property. A concept definition can
     * always be turned into the correspondent concept as long as the primary knowledge
     * components are known.
     * 
     * @return the concept definition
     */
    String getDefinition();


}
