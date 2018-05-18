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
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Urns;

/**
 * The Class RasterEncoder.
 */
public class VectorEncoder implements IResourceEncoder {
	
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
		
		File base = null;
		if (Urns.INSTANCE.isLocal(resource.getUrn())) {
			base = Resources.INSTANCE.getLocalWorkspace().getRoot();
		} else {
			// TODO
		}
		
		if (base == null) {
			return false;
		}
		
		for (String s : resource.getLocalPaths()) {
			File rfile = new File(base + File.separator + s);
			if (!rfile.exists() || !rfile.canRead()) {
				return false;
			}
		}
		
		return true;
	}

}
