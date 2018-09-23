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
package org.integratedmodelling.klab.api.data;

import java.util.Map;

import org.integratedmodelling.klab.api.knowledge.IProject;

/**
 * The Interface IResourceCatalog.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IResourceCatalog extends Map<String, IResource> {

	/**
	 * Selectively clear resources linked to the passed objects.
	 * 
	 * @param objects
	 *            resources, projects, IDs or anything that can be linked to a
	 *            specific resource or set thereof.
	 * @throws IllegalArgumentException
	 *             if the input cannot be linked to a (set of) resource(s).
	 */
    void clearOnly(Object... objects);

	/**
	 * Move resource to a different project. Return new resource.
	 * 
	 * @param resource
	 * @param destinationProject
	 * @return
	 */
    IResource move(IResource resource, IProject destinationProject);

    /**
     * Copy resource to a different project. Return new resource.
     * 
     * @param resource
     * @param destinationProject
     * @return
     */
    IResource copy(IResource resource, IProject destinationProject);

    /**
     * Rename a local resource's URN.
     * 
     * @param resource
     * @param newUrn
     * @return
     */
    IResource rename(IResource resource, String newUrn);

}
