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

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.runtime.IComputationContext;

/**
 * The Class WcsEncoder.
 */
public class WcsEncoder implements IResourceEncoder {
	
	@Override
	public void getEncodedData(IResource resource, IGeometry geometry, Builder builder, IComputationContext context) {
		
//		State.Builder sBuilder = KlabData.State.newBuilder();
//		
//		// TODO Auto-generated method stub - set the data from the map
//
//		return KlabData.newBuilder()
//				.setGeometry("S2")
//				.setState(sBuilder.build())
//				.build();
	}

	@Override
	public boolean isOnline(IResource resource) {
		// TODO Auto-generated method stub
		return false;
	}

}
