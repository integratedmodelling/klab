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
import java.util.Date;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourceEnhancer;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.ogc.VectorAdapter;
import org.integratedmodelling.klab.ogc.WfsAdapter;
import org.integratedmodelling.klab.ogc.integration.Geoserver;
import org.integratedmodelling.klab.ogc.integration.Postgis;
import org.integratedmodelling.klab.rest.ResourceReference;

/**
 * The raster publisher will attempt WCS publishing if a WCS server is
 * connected.
 * 
 * @author ferdinando.villa
 *
 */
public class VectorPublisher implements IResourceEnhancer {

	@Override
	public IResource publish(IResource localResource, IResourceCatalog catalog, IMonitor monitor) throws KlabException {
		return enhanceResource(localResource, catalog);
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
	public IResource enhanceResource(IResource resource, IResourceCatalog catalog) {

		Resource ret = (Resource) resource;

		if (Geoserver.isEnabled()) {

			Logging.INSTANCE.info("geoserver instance enabled: attempting ingestion of " + resource.getUrn());

			Geoserver geoserver = Geoserver.create();
			Urn urn = new Urn(resource.getUrn());

			if (Postgis.isEnabled()) {
				Postgis postgis = Postgis.create(urn);
				if (postgis.isOnline()) {

					Logging.INSTANCE.info("postgis enabled: attempting ingestion of " + resource.getUrn());

					File file = ((VectorAdapter) Resources.INSTANCE.getResourceAdapter(VectorAdapter.ID))
							.getMainFile(resource);

					String table = postgis.publish(file, urn);
					if (table != null) {

						Logging.INSTANCE.info("PostGIS ingestion of " + resource.getUrn() + " successful");
						Logging.INSTANCE.info("Geoserver enabled: attempting ingestion of " + resource.getUrn());

						String id = geoserver.publishPostgisVector(postgis, urn.getNamespace(), table);

						if (id != null) {

							Logging.INSTANCE.info("Geoserver ingestion of " + resource.getUrn() + " successful");

							ResourceReference descriptor = ((Resource) resource).getReference();
							descriptor.setAdapterType(WfsAdapter.ID);
							descriptor.getLocalPaths().clear();
							descriptor.getParameters().put("serviceUrl", geoserver.getServiceUrl());
							descriptor.getParameters().put("wfsVersion", "1.0.0");
							descriptor.getParameters().put("wfsIdentifier", id);

							Resource res = new Resource(descriptor);
							ret = (Resource) catalog.update(res,
									"Published to PostGIS/Geoserver by vector adapter on " + new Date());
						} else {
							Logging.INSTANCE.error("Geoserver ingestion of " + resource.getUrn() + " failed");
						}
					}
				}
			} else {
				Logging.INSTANCE.error("PostGIS ingestion of " + resource.getUrn() + " failed");
			}
		} else {
			Logging.INSTANCE.warn("Geoserver ingestion of " + resource.getUrn() + " failed");
		}

		return ret;
	}

}
