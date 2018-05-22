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

import org.geotools.data.FeatureSource;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.ogc.vector.files.VectorEncoder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

/**
 * The WFS encoder simply redefines the getFeatureSource method within a vector encoder.
 */
public class WfsEncoder extends VectorEncoder {
		
	@Override
	protected FeatureSource<SimpleFeatureType, SimpleFeature> getFeatureSource(IResource resource,
			IGeometry geometry) {
		// TODO Auto-generated method stub
		return super.getFeatureSource(resource, geometry);
	}

	@Override
	public boolean isOnline(IResource resource) {
		// TODO Auto-generated method stub
		return false;
	}

}
