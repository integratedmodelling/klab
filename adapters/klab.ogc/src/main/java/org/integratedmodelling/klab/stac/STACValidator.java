package org.integratedmodelling.klab.stac;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hortonmachine.gears.io.stac.HMStacCollection;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.provenance.IActivity.Description;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.resources.ResourceBuilder;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.ogc.STACAdapter;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;

/**
 * A field to validate if the GeoJSON is compliant with the STAC specification
 * @author igc
 *
 */
public class STACValidator implements IResourceValidator {

    @Override
    public Builder validate(String urn, URL url, IParameters<String> userData, IMonitor monitor) {
        if (!canHandle(null, userData)) {
            throw new IllegalArgumentException("STAC specifications are invalid or incomplete");
        }

        STACService service = STACAdapter.getService(userData.get("catalogUrl", String.class));

        String collectionId = userData.get("collectionId", String.class);
        Optional<HMStacCollection> collection;
        try {
            collection = service.getCollectionById(collectionId);
        } catch (Exception e) {
            throw new KlabResourceNotFoundException("STAC collection " + userData.get("collectionId") + " not found on server");
        }

        if(collection.isEmpty()) {
            throw new KlabResourceNotFoundException("STAC collection " + userData.get("collectionId") + " not found on server");
        }

        IGeometry geometry = service.getGeometry(collectionId);

        return new ResourceBuilder(urn).withParameters(userData)//.withType(Type.NUMBER)
                .withGeometry(geometry).withSpatialExtent(service.getSpatialExtent());
    }

    @Override
    public IResource update(IResource resource, ResourceCRUDRequest updateData) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Operation> getAllowedOperations(IResource resource) {
        return Collections.emptyList();
    }

    @Override
    public boolean isObservationAllowed(IResource resource, Map<String, String> urnParameters, Description description) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public IResource performOperation(IResource resource, String operationName, IParameters<String> parameters,
            IResourceCatalog catalog, IMonitor monitor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean canHandle(File resource, IParameters<String> parameters) {
        return resource == null && parameters.contains("catalogUrl") && parameters.contains("collectionId");
    }

    @Override
    public Collection<File> getAllFilesForResource(File file) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<? extends String, ? extends Object> describeResource(IResource resource) {
        // TODO Auto-generated method stub
        return null;
    }

}
