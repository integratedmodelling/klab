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
package org.integratedmodelling.klab.raster.wcs;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator.Operation;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.resources.ResourceBuilder;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.ogc.WcsAdapter;
import org.integratedmodelling.klab.raster.wcs.WCSService.WCSLayer;

/**
 * The Class WcsValidator.
 */
public class WcsValidator implements IResourceValidator {

	@Override
	public Builder validate(URL url, IParameters<String> userData, IMonitor monitor) {

		if (!canHandle(null, userData)) {
			throw new IllegalArgumentException("WCS specifications are invalid or incomplete");
		}

		int band = userData.get("band", 0);

		WCSService service = WcsAdapter.getService(userData.get("serviceUrl", String.class),
				Version.create(userData.get("wcsVersion", String.class)));

		String layerId = userData.get("wcsIdentifier", String.class);
		if (service.TRANSLATE_DOUBLEUNDERSCORE_TO_NAMESPACE_SEPARATOR) {
			layerId = layerId.replaceAll(":", "__");
		}

		WCSLayer layer = service.getLayer(layerId);
		if (layer == null) {
			throw new KlabResourceNotFoundException(
					"WCS layer " + userData.get("wcsIdentifier") + " not found on server");
		}

		/*
		 * Substitute user identifier with official one from layer, validating the layer
		 * at the same time.
		 */
		String identifier = layer.getIdentifier();

		if (layer.isError()) {
			throw new KlabResourceNotFoundException(
					"WCS layer " + userData.get("wcsIdentifier") + " is available but has errors");
		}

		userData.put("wcsIdentifier", identifier);
		if (!layer.getNodata(band).isEmpty()) {
			userData.put("nodata", layer.getNodata(band).iterator().next());
		}
		IGeometry geometry = layer.getGeometry();

		return new ResourceBuilder()
				.withParameters(userData)
				.withType(Type.NUMBER)
				.withGeometry(geometry)
				.withSpatialExtent(layer.getSpatialExtent());
	}

	@Override
	public boolean canHandle(File resource, IParameters<String> parameters) {
		return resource == null && parameters.contains("wcsVersion") && parameters.contains("serviceUrl")
				&& parameters.contains("wcsIdentifier");
	}

	@Override
	public Collection<File> getAllFilesForResource(File file) {
		throw new IllegalStateException("the WCS adapter does not handle files");
	}
	
    @Override
    public List<Operation> getAllowedOperations(IResource resource) {
        List<Operation> ret = new ArrayList<>();
        return ret;
    }

    @Override
    public IResource performOperation(IResource resource, String operationName, IMonitor monitor) {
        throw new KlabUnimplementedException("resource operations unimplemented");
    }

}
