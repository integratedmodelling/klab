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

import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.utils.Pair;

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
	 * @param objects resources, projects, IDs or anything that can be linked to a
	 *                specific resource or set thereof.
	 * @throws IllegalArgumentException if the input cannot be linked to a (set of)
	 *                                  resource(s).
	 */
	void clearOnly(Object... objects);

	/**
	 * Remove only the definition from the catalog, not touching the resource files.
	 * 
	 * @param urn
	 * @return
	 */
	IResource removeDefinition(String urn);

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
	 * Update the currently stored resource with the version passed (using the
	 * resource's URN to locate the previous version). No versioning is handled here
	 * - simply substitute the metadata. Any versioning must be done upstream and
	 * incorporated in the passed resource.
	 * 
	 * @param resource
	 * @param a        string describing the update
	 * @return the updated resource
	 * @throws KlabResourceNotFoundException if a resource with the same URN is not
	 *                                       in the catalog.
	 */
	IResource update(IResource resource, String updateMessage);

	/**
	 * Rename a local resource's URN.
	 * 
	 * @param resource
	 * @param newUrn
	 * @param updateMessage message describing who renamed and why
	 * @return
	 */
	IResource rename(IResource resource, String newUrn, String updateMessage);

}
