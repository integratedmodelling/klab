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

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The Interface IConcept.
 *
 * @author Ferdinando Villa
 * @version $Id: $Id
 */
public interface IConcept extends IKnowledge {

    /**
     * The quick version of the reasoner-based {@link org.integratedmodelling.klab.api.knowledge.IKnowledge#is(ISemantic)} uses
     * the {@link org.integratedmodelling.kim.api.IKimConcept.Type} enum to instantly assess the concept type without needing any
     * costly inference.
     *
     * @param type a {@link org.integratedmodelling.kim.api.IKimConcept.Type} object.
     * @return a boolean.
     */
    boolean is(Type type);

    /**
     * Return a collection of all the direct parent classes.
     *
     * @return a collection of parents
     */
    Collection<IConcept> getParents();

    /**
     * Return a collection of all direct and indirect parent classes. Should use a
     * reasoner if installed, and only follow transitive superclass relationships if not.
     *
     * @return a collection of all the parent concepts.
     */
    Collection<IConcept> getAllParents();

    /**
     * Return a collection of all direct subclasses.
     *
     * @return all child concepts.
     */
    Collection<IConcept> getChildren();

    /**
     * Return all properties that are defined as having this in their domain
     * subproperties.
     *
     * @return properties in our domain
     */
    Collection<IProperty> getProperties();

    /**
     * It includes all properties inherited by its superclasses, or having undefined
     * domains
     *
     * @return all properties in our domain recursively
     */
    Collection<IProperty> getAllProperties();

    /**
     * Return the range of the passed property in the context of this concept, considering
     * restrictions.
     *
     * @param property a {@link org.integratedmodelling.klab.api.knowledge.IProperty} object.
     * @return the range of the passed property in our context
     * @throws org.integratedmodelling.klab.exceptions.KlabException
     */
    Collection<IConcept> getPropertyRange(IProperty property) throws KlabException;

    /**
     * Get the value that the passed data property is restricted to in this concept, or
     * null if there is no restriction.
     *
     * @param property a {@link org.integratedmodelling.klab.api.knowledge.IProperty} object.
     * @return value for data property in our context, or null
     * @throws org.integratedmodelling.klab.exceptions.KlabException
     */
    Object getValueOf(IProperty property) throws KlabException;

    /**
     * Return the (only) parent class, or throw an unchecked exception if there's more
     * than one parent.
     *
     * @return the parent concept, assuming we have one only
     */
    IConcept getParent();

    /**
     * get the number of properties for this type
     *
     * @param property a {@link java.lang.String} object.
     * @return number of properties of this type in our context
     */
    int getPropertiesCount(String property);

    /**
     * <p>getLeastGeneralCommonConcept.</p>
     *
     * @param c a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
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
     *
     * @param property a {@link org.integratedmodelling.klab.api.knowledge.IProperty} object.
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
     * @param target a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     * @return the property that restricts this concept in our context
     */
    Collection<IProperty> findRestrictingProperty(IConcept target);

    /**
     * Return the concept's canonical definition in terms of worldview concepts. This will
     * correspond to the fully qualified concept name for concepts that have been declared
     * directly in the worldview, or to their normalized k.IM declaration when the concept
     * has been created by composing other concepts. Concepts that are not
     * primary carry their definition in an annotation property. A concept definition can
     * always be turned into the correspondent concept as long as the same worldview is loaded.
     *
     * TODO consider adding worldview version and branch to metadata.
     *
     * @return the concept definition
     */
    String getDefinition();
    
    /**
     * If the concept is {@link Type#UNION} or {@link Type#INTERSECTION}, this will return the
     * list of operands. Otherwise it will return a list containing only the concept itself.
     *  
     * @return the list of operands
     */
    List<IConcept> getOperands();

}
