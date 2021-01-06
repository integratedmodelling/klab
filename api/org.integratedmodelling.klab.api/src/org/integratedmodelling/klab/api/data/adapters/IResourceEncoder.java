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

import java.util.Map;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
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
	 * calling
	 * {@link #getEncodedData(IResource, IGeometry, org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder, IContextualizationScope)}
	 * without spending too much time. Full information should be provided through
	 * the monitor: this will be called also on-demand to individually trace
	 * resource issues.
	 * 
	 * @param resource
	 * @param monitor  use to report any situation with the needed level of detail.
	 * 
	 * @return true if resource can be used at the moment of this call.
	 */
	boolean isOnline(IResource resource, IMonitor monitor);

	/**
	 * Ensure the resource is ready for contextualizing the target observation in
	 * the passed scale and scope. Called at each getResourceData, which will be
	 * called once per time extent. If needed, a copy of the resource may be
	 * returned, tuned to the passed context information. If not needed, returning
	 * the unmodified resource is the default answer. In no instance should the
	 * original resource be modified.
	 * 
	 * @param resource
	 * @param scale
	 * @param targetObservation
	 * @param scope
	 * @return
	 */
	IResource contextualize(IResource resource, IScale scale, IArtifact targetObservation,
			Map<String, String> urnParameters, IContextualizationScope scope);

	/**
	 * Build the resource data corresponding to the passed resource in the passed
	 * geometry. The data are created using a builder passed by the runtime.
	 *
	 * @param resource      a
	 *                      {@link org.integratedmodelling.klab.api.data.IResource}.
	 *                      It should have been recently inspected with
	 *                      {@link #isOnline(IResource)} so it can be assumed that
	 *                      it is correct and active.
	 * @param urnParameters any parameters passed in the URN reference to the
	 *                      resource, using the URN fragment. A single parameter
	 *                      without key has the key 'value'.
	 * @param geometry      the
	 *                      {@link org.integratedmodelling.klab.api.data.IGeometry}
	 *                      of reference for the query. The resolution process
	 *                      should guarantee that the intersection with the
	 *                      resource's geometry is not empty.
	 * @param builder       a suitable builder to use to build the dataset
	 * @param context       the context of computation
	 */
	void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry,
			IKlabData.Builder builder, IContextualizationScope context);
}
