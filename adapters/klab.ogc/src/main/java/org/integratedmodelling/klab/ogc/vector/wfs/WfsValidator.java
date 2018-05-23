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
package org.integratedmodelling.klab.ogc.vector.wfs;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.FeatureSource;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.filter.text.ecql.ECQL;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.ogc.WfsAdapter;
import org.integratedmodelling.klab.ogc.vector.files.VectorValidator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

/**
 * The Class WcsValidator.
 */
public class WfsValidator extends VectorValidator {

	@Override
	public IResource.Builder validate(URL url, IParameters userData, IMonitor monitor) {

		IResource.Builder ret = Resources.INSTANCE.createResourceBuilder();
		Version version = Version.create(userData.get("wfsVersion", "1.0.0"));

		try {

			if (userData.contains("filter")) {
				try {
					ECQL.toFilter(userData.get("filter", String.class));
				} catch (CQLException e) {
					ret.addError(
							"CQL filter expression '" + userData.get("filter", String.class) + "' has syntax errors");
				}
			}

			DataStore dataStore = WfsAdapter.getDatastore(userData.get("serverUrl", String.class), version);
			String typeName = dataStore.getTypeNames()[0];
			FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(typeName);

			validateCollection(source, ret, userData, monitor);

		} catch (Throwable e) {
			ret.addError("Error validating " + e.getMessage());
		}

		return ret;
	}

	@Override
	public boolean canHandle(File resource, IParameters parameters) {
		return resource == null && parameters.contains("serviceUrl") && parameters.contains("wfsIdentifier");
	}

	@Override
	public Collection<File> getAllFilesForResource(File file) {
		throw new IllegalStateException("the WFS adapter does not handle files");
	}
}