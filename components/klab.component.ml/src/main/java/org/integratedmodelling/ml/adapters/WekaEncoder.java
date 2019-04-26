package org.integratedmodelling.ml.adapters;

import java.util.Map;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.runtime.IComputationContext;

public class WekaEncoder implements IResourceEncoder {

	@Override
	public boolean isOnline(IResource resource) {
		return !resource.hasErrors();
	}

	@Override
	public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry,
			Builder builder, IComputationContext context) {
		// TODO Auto-generated method stub
		
	}

}
