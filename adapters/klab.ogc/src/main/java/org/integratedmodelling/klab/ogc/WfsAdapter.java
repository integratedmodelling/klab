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

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.wfs.WFSDataStore;
import org.geotools.data.wfs.WFSDataStoreFactory;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.ogc.vector.wfs.WfsEncoder;
import org.integratedmodelling.klab.ogc.vector.wfs.WfsPublisher;
import org.integratedmodelling.klab.ogc.vector.wfs.WfsValidator;

/**
 * The Class WfsAdapter.
 */
@ResourceAdapter(
		type = "wfs", version = Version.CURRENT, 
		requires = { "serviceUrl", "wfsIdentifier" }, 
		optional = {
				// TODO check out http://docs.geotools.org/latest/userguide/library/data/wfs-ng.html
				// TODO find a way to provide documentation for all these options
				"wfsVersion", "bufferSize", "serverType", "timeoutSeconds", 
				"filter", "computeShape"
		})
public class WfsAdapter implements IResourceAdapter {

	static Map<String, WFSDataStore> dataStores = new HashMap<>();

	@Override
	public String getName() {
		return "wfs";
	}

	@Override
	public IResourceValidator getValidator() {
		return new WfsValidator();
	}

	@Override
	public IResourcePublisher getPublisher() {
		return new WfsPublisher();
	}

	@Override
	public IResourceEncoder getEncoder() {
		return new WfsEncoder();
	}

	public static WFSDataStore getDatastore(String serverUrl, Version version) {

		WFSDataStore ret = dataStores.get(serverUrl);

		if (ret == null) {
			String getCapabilities = serverUrl + "?SERVICE=wfs&REQUEST=getCapabilities&version=" + version;
			WFSDataStoreFactory dsf = new WFSDataStoreFactory();
			try {
				
				Map<String, Serializable> connectionParameters = new HashMap<>();
				connectionParameters.put("WFSDataStoreFactory:GET_CAPABILITIES_URL", getCapabilities);
				
				/*
				 * TODO all other parameters
				 */
				
				
				ret = dsf.createDataStore(connectionParameters);
				dataStores.put(serverUrl, ret);

			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
		return ret;
	}

	@Override
	public IResourceImporter getImporter() {
		// TODO Auto-generated method stub
		return null;
	}
}