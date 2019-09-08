package org.integratedmodelling.klab.components.geospace.processing.osm;

import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

@UrnAdapter(type = "osm", version = Version.CURRENT)
public class OSMUrnAdapter implements IUrnAdapter {

	@Override
	public String getName() {
		return "osm";
	}

	@Override
	public boolean isOnline(Urn urn) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void getEncodedData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {
		// TODO Auto-generated method stub
		
	}

}
