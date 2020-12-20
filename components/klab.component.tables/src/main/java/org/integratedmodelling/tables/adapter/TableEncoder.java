package org.integratedmodelling.tables.adapter;

import java.util.Map;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class TableEncoder implements IResourceEncoder {

    public TableEncoder(String subtype) {
		// TODO Auto-generated constructor stub
	}

	@Override
    public boolean isOnline(IResource resource, IMonitor monitor) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder, IContextualizationScope context) {
        // TODO Auto-generated method stub

    }

}
