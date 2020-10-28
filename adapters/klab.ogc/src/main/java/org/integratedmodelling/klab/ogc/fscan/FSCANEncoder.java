package org.integratedmodelling.klab.ogc.fscan;

import java.util.Map;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.ogc.integration.Postgis;

public class FSCANEncoder implements IResourceEncoder {

	private Postgis postgis = null;
	
	@Override
	public boolean isOnline(IResource resource, IMonitor monitor) {
		if (Postgis.isEnabled()) {
			this.postgis = Postgis.create();
			return this.postgis.isOnline();
		}
		return false;
	}

	@Override
	public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry,
			Builder builder, IContextualizationScope context) {
		// TODO Auto-generated method stub
		
	}

}
