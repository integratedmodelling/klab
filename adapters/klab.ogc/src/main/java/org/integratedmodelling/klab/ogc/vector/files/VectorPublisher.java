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

import java.io.File;

import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceEnhancer;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.ogc.VectorAdapter;
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
		/*
		 * a vector resource is not enhanced; the enhanced resource is WFS which cannot
		 * be further enhanced. This means "keep trying to push to GS/Postgis if so
		 * configured".
		 */
		return false;
	}

	@Override
	public IResource enhanceResource(IResource resource) {

		Resource ret = (Resource) resource;

		if (Geoserver.isEnabled()) {

			Geoserver geoserver = Geoserver.create();
			Urn urn = new Urn(resource.getUrn());

			if (Postgis.isEnabled()) {
				Postgis postgis = Postgis.create(urn);
				if (postgis.isOnline()) {

					File file = ((VectorAdapter) Resources.INSTANCE.getResourceAdapter(VectorAdapter.ID))
							.getMainFile(resource);

					String table = postgis.publish(file, urn);
					if (table != null && geoserver.publishPostgisVector(postgis, urn.getNamespace(), table)) {

						ret = new Resource(((Resource) resource).getReference());
						// fix resource adapter and parameters			
						// TODO add message with date and results
						ret.copyToHistory("Enhanced by vector adapter");
						// return fixed resource
//						ret.setAdapter(VectorAdapter.ID);
					}
				}
			}

		}

		return ret;
	}

}
