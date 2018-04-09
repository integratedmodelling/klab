package org.integratedmodelling.klab.raster.files;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData.State;

public class RasterEncoder implements IResourceEncoder {
	
	@Override
	public IKlabData getEncodedData(IResource resource, IGeometry geometry) {
		
		State.Builder sBuilder = KlabData.State.newBuilder();
		
		// TODO Auto-generated method stub - set the data from the map

		return KlabData.newBuilder()
				.setGeometry("S2")
				.setState(sBuilder.build())
				.build();
	}

}
