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
 * The Interface IRoleService.
 */
public interface IRoleService {

    /**
     * Retrieve any roles that were declared directly with the concept. This only
     * applies to observables declared in models, as there is no other legal place to
     * declare observables with roles.
     * 
     * @param concept
     * @return any role adopted
     */
    Collection<IConcept> getRoles(IConcept concept);

    /**
     * Check for adoption of a particular role
     * @param type
     * @param role
     * @return true if type has the role
     */
    boolean hasRole(IConcept type, IConcept role);

    /**
     * True if k has a role R so that the passed role is-a R.
     * @param type 
     * @param role 
     *
     * @return true if type has a role of the passed type
     */
    boolean hasParentRole(IConcept type, IConcept role);

}
