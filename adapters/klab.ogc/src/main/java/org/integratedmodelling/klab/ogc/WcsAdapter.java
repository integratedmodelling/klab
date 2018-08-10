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
package org.integratedmodelling.klab.ogc;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.raster.wcs.WCSService;
import org.integratedmodelling.klab.raster.wcs.WcsEncoder;
import org.integratedmodelling.klab.raster.wcs.WcsImporter;
import org.integratedmodelling.klab.raster.wcs.WcsPublisher;
import org.integratedmodelling.klab.raster.wcs.WcsValidator;

/**
 * The Class WcsAdapter.
 */
@ResourceAdapter(type = "wcs", version = Version.CURRENT, requires = { "serviceUrl", "wcsVersion", "wcsIdentifier" }, optional = {
		"namespace" })
public class WcsAdapter implements IResourceAdapter {

	/**
	 * Map all service URLs encountered to their handlers.
	 * TODO see if we want to cache this.
	 */
	static Map<String, WCSService> services = new HashMap<>();
	static Map<String, File> fileCache = new HashMap<>();
	
	/**
	 * Get the service handler for the passed service URL and version. The version is 
	 * ignored if the service handler was already there with a different one.
	 * 
	 * @param serviceUrl
	 * @param version
	 * @return a WCS service. Inspect for errors before using.
	 */
	public static WCSService getService(String serviceUrl, Version version) {
		if (services.containsKey(serviceUrl)) {
			return services.get(serviceUrl);
		}
		WCSService ret = new WCSService(serviceUrl, version);
		if (ret != null) {
			services.put(serviceUrl, ret);
		}
		return ret;
	}
	
	@Override
	public String getName() {
		return "wcs";
	}

	@Override
	public IResourceValidator getValidator() {
		return new WcsValidator();
	}

	@Override
	public IResourcePublisher getPublisher() {
		return new WcsPublisher();
	}

	@Override
	public IResourceEncoder getEncoder() {
		return new WcsEncoder();
	}

	public static File getCachedFile(String identifier, IGeometry geometry) {
		String key = identifier + "#" + geometry.toString();
		File ret = fileCache.get(key);
		if (ret != null && ret.exists()) {
			return ret;
		} 
		fileCache.remove(key);
		return null;
	}
	
	public static void setCachedFile(File file, String identifier, IGeometry geometry) {
		String key = identifier + "#" + geometry.toString();
		fileCache.put(key, file);
	}

	@Override
	public IResourceImporter getImporter() {
		return new WcsImporter();
	}
	
	@Override
	public IPrototype getResourceConfiguration() {
		return new Prototype(
				Dataflows.INSTANCE.declare(getClass().getClassLoader().getResource("ogc/prototypes/wcs.kdl"))
						.getActuators().iterator().next(),
				null);
	}
}
