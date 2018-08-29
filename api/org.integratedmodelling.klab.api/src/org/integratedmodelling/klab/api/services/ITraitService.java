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
package org.integratedmodelling.klab.api.services;

import java.util.Collection;
import org.integratedmodelling.klab.api.knowledge.IConcept;

/**
 * The Interface ITraitService.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface ITraitService {

    /**
     * Return all traits, i.e. identities, attributes and realms.
     *
     * @param concept a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     * @return a {@link java.util.Collection} object.
     */
    Collection<IConcept> getTraits(IConcept concept);


    /**
     * Return all identities.
     * 
     * @param concept
     * @return identities
     */
	Collection<IConcept> getIdentities(IConcept concept);

	/**
	 * Return all attributes
	 * @param concept
	 * @return attributes
	 */
	Collection<IConcept> getAttributes(IConcept concept);

	/**
	 * Return all realms.
	 * 
	 * @param concept
	 * @return realms
	 */
	Collection<IConcept> getRealms(IConcept concept);
    
    /**
     * <p>getBaseParentTrait.</p>
     *
     * @param trait a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     * @return a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     */
    IConcept getBaseParentTrait(IConcept trait);

    /**
     * Check if concept k carries the passed trait. Uses is() on all explicitly expressed
     * traits.
     *
     * @param type a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     * @param trait a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     * @return a boolean.
     */
    boolean hasTrait(IConcept type, IConcept trait);

    /**
     * Check if concept k carries a trait T so that the passed trait is-a T.
     *
     * @param type a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     * @param trait a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     * @return a boolean.
     */
    boolean hasParentTrait(IConcept type, IConcept trait);


    /**
     * Like {@link #getTraits(IConcept)} but only returns the traits directly attributed to
     * this concept.
     * 
     * @param concept
     * @return
     */
	Collection<IConcept> getDirectTraits(IConcept concept);


}
