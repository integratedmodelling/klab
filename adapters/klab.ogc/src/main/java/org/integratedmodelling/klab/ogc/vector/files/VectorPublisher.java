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
import java.io.IOException;
import java.util.Properties;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.DataUtilities;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;

/**
 * The raster publisher will attempt WCS publishing if a WCS server is
 * connected.
 * 
 * @author ferdinando.villa
 *
 */
public class VectorPublisher implements IResourcePublisher {

	@Override
	public IResource publish(IResource localResource, IMonitor monitor) throws KlabException {

		IResource ret = localResource;

		/*
		 * If we have Postgis + Geoserver dedicated to this node instance, publish in
		 * them and turn the resource into a WFS one.
		 */
		if (false /* have postgis */) {
			try {

				// TODO resource shp path
				FileDataStore ds = FileDataStoreFinder.getDataStore(new File("/home/ian/Data/states/states.shp"));

				/*
				 * TODO one database per catalog? In all cases, ensure it exists.
				 */
				
				// TODO configured properties
				Properties params = new Properties();
				params.put("user", "postgres");
				params.put("passwd", "postgres");
				params.put("port", "5432");
				params.put("host", "127.0.0.1");
				params.put("database", "test");
				params.put("dbtype", "postgis");

				DataStore dataStore = DataStoreFinder.getDataStore(params);
				SimpleFeatureSource source = dataStore.getFeatureSource("tablename");
				if (source instanceof SimpleFeatureStore) {
					SimpleFeatureStore store = (SimpleFeatureStore) source;
					store.addFeatures(DataUtilities.collection(ds.getFeatureSource().getFeatures().features()));
				} else {
					throw new KlabIOException("vector publisher: cannot write to Postgis database");
				}
			} catch (IOException e) {
				throw new KlabIOException(e);
			}
		}
		
		if (false /* have Geoserver */ ) {
			
			/* 
			 * ensure database is published as a PG store
			 */
			
			/*
			 * remove the layer if there
			 */
			
			/*
			 * add as new layer
			 */
		}
		
		if (false /* published in GS */) {
			
			/*
			 * turn resource into WFS and save it
			 */
			
		}

		return ret;
	}

}
