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

import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.knowledge.IAuthority.Identity;
import org.integratedmodelling.klab.api.knowledge.IConcept;

/**
 * The Interface IAuthorityService. For the time being simply a catalog of
 * installed authorities.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IAuthorityService {

	/**
	 * 
	 * @return
	 */
	Collection<IAuthority> getAuthorities();

	/**
	 * 
	 * @param authorityId
	 * @return
	 */
	IAuthority getAuthority(String authorityId);

	/**
	 * 
	 * @param authority
	 */
	void deactivateAuthority(String authority);

	/**
	 * 
	 * @param authorityId
	 * @param identityId
	 * @return
	 */
	Identity getIdentity(String authorityId, String identityId);

    /**
     * Get the authority official code for the passed concept, which must come from an authority.
     * 
     * @param c
     * @return
     */
    String getAuthorityCode(IConcept c);

    /**
     * Get the authority this concept is part of, or null.
     * 
     * @param c
     * @return
     */
    IAuthority getAuthority(IConcept c);

}
