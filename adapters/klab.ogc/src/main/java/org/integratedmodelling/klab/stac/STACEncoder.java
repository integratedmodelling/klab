package org.integratedmodelling.klab.stac;

import java.io.OutputStream;
import java.util.Map;
import java.util.Optional;

import org.hortonmachine.gears.io.stac.HMStacCollection;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.ogc.STACAdapter;

public class STACEncoder implements IResourceEncoder {

    @Override
    public boolean isOnline(IResource resource, IMonitor monitor) {
        STACService service = STACAdapter.getService(resource.getParameters().get("catalogUrl", String.class));

        if (service == null) {
            monitor.error("Service " + resource.getParameters().get("catalogUrl", String.class)
                    + " does not exist: likely the service URL is wrong or offline");
            return false;
        }

        Optional<HMStacCollection> collection;
        try {
            collection = service.getCollectionById(resource.getParameters().get("collectionId", String.class));
        } catch (Exception e) {
            monitor.error("Collection " + resource.getParameters().get("catalogUrl", String.class)
                    + " cannot be find.");
            return false;
        }
        
        if (collection.isEmpty()) {
            monitor.error("Collection " + resource.getParameters().get("catalogUrl", String.class)
                    + " cannot be find.");
            return false;
        }

        return true;
    }

    @Override
    public IResource contextualize(IResource resource, IScale scale, IArtifact targetObservation,
            Map<String, String> urnParameters, IContextualizationScope scope) {
        // TODO Auto-generated method stub
        return resource;
    }

    @Override
    public ICodelist categorize(IResource resource, String attribute, IMonitor monitor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder,
            IContextualizationScope scope) {
        // TODO Auto-generated method stub

    }

    @Override
    public void listDetail(IResource resource, OutputStream stream, boolean verbose, IMonitor monitor) {
        // TODO Auto-generated method stub

    }

}
