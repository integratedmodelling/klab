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

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

/**
 * The Interface IResourceEncoder.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IResourceEncoder {

	/**
	 * Check if the resource can be accessed. This should ensure the ability of
	 * calling {@link #getEncodedData(IResource, IGeometry, IMonitor)} without
	 * spending too much time.
	 * 
	 * @param resource
	 * @return true if resource can be used at the moment of this call.
	 */
	boolean isOnline(IResource resource);

	/**
	 * Get the encoded data for the resource.
	 *
	 * @param resource
	 *            a {@link org.integratedmodelling.klab.api.data.IResource}. It
	 *            should have been recently inspected with
	 *            {@link #isOnline(IResource)}.
	 * @param geometry
	 *            the {@link org.integratedmodelling.klab.api.data.IGeometry} of
	 *            reference for the query. The resolution process should guarantee
	 *            that the intersection with the resource's geometry is not empty.
	 * @param monitor
	 *            for notifications and identity retrieval
	 * @return a {@link org.integratedmodelling.klab.api.data.adapters.IKlabData}
	 *         object.
	 */
	IKlabData getEncodedData(IResource resource, IGeometry geometry, IMonitor monitor);
}
