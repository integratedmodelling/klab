package org.integratedmodelling.tables.adapter;

import java.util.Map;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.runtime.IComputationContext;

public class TableEncoder implements IResourceEncoder {

    @Override
    public boolean isOnline(IResource resource) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder, IComputationContext context) {
        // TODO Auto-generated method stub

    }

}
