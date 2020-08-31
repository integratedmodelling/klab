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
package org.integratedmodelling.klab.api.data.adapters;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The Interface IResourcePublisher.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IResourcePublisher {

	// Properties for the publish.properties settings sent along with uploads
	public static String SUGGESTED_CATALOG_PROPERTY = "im:suggested-catalog-id";
	public static String SUGGESTED_RESOURCE_ID_PROPERTY = "im:suggested-resource-id";
	public static String SUGGESTED_NAMESPACE_PROPERTY = "im:suggested-namespace-id";
	public static String RESOURCE_PERMISSIONS_PROPERTY = "im:permissions";

	/**
	 * Publish a local resource, which must have no errors, to a specified resource
	 * catalog. If no errors happen during publishing, produce a new public
	 * IResource with a valid URN, ready for storage in the public resource catalog.
	 *
	 * @param localResource the local resource
	 * @param catalog the resource catalog
	 * @param monitor       for notifications and identity retrieval
	 * @return a new resource. If errors happen, throw an exception; if the function
	 *         returns, the resource must be valid.
	 * @throws KlabException the klab exception
	 */
	public IResource publish(IResource localResource, IResourceCatalog catalog, IMonitor monitor);

}
