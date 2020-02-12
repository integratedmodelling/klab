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
package org.integratedmodelling.klab.ogc.vector.files;

import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceEnhancer;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.ogc.integration.Geoserver;
import org.integratedmodelling.klab.ogc.integration.Postgis;

/**
 * The raster publisher will attempt WCS publishing if a WCS server is
 * connected.
 * 
 * @author ferdinando.villa
 *
 */
public class VectorPublisher implements IResourceEnhancer {

	@Override
	public IResource publish(IResource localResource, IMonitor monitor) throws KlabException {
		return enhanceResource(localResource);
	}

	@Override
	public boolean isEnhanced(IResource resource) {
		// stop any further attempt, as the result of enhancing uses a different adapter.
		return true;
	}

	@Override
	public IResource enhanceResource(IResource resource) {
		
		if (Geoserver.isEnabled()) {
			
			Resource ret = new Resource(((Resource)resource).getReference());
			// TODO add message with date and results
			ret.copyToHistory("Enhanced by vector adapter");
			
			if (Postgis.isEnabled()) {
				Postgis postgis = Postgis.create(new Urn(resource.getUrn()));
				if (postgis.isOnline()) {
					// find the main file
					// publish in postgis
					// publish in geoserver
					// fix resource adapter and parameters
					// return fixed resource
				}
			}
			
		}
		
		return resource;
	}
	

}
