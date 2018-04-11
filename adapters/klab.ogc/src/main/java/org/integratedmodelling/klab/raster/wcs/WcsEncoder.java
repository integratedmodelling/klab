package org.integratedmodelling.klab.raster.wcs;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData.State;

public class WcsEncoder implements IResourceEncoder {
	
	@Override
	public IKlabData getEncodedData(IResource resource, IGeometry geometry, IMonitor monitor) {
		
		State.Builder sBuilder = KlabData.State.newBuilder();
		
		// TODO Auto-generated method stub - set the data from the map

		return KlabData.newBuilder()
				.setGeometry("S2")
				.setState(sBuilder.build())
				.build();
	}

}
