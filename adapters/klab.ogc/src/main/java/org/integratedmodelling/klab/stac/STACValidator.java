package org.integratedmodelling.klab.stac;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.provenance.IActivity.Description;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.data.resources.ResourceBuilder;
import org.integratedmodelling.klab.ogc.STACAdapter;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

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
        String catalogUrl = userData.get("catalogUrl", String.class);
        STACService service = STACAdapter.getService(catalogUrl);

        String collectionId = userData.get("collectionId", String.class);
        HttpResponse<JsonNode> metadata = Unirest.get(catalogUrl + "/collections/" + collectionId).asJson();
        List<STACExtension> extensions = new ArrayList<>();
        JSONArray extensionArray = metadata.getBody() != null
                ? extensionArray = metadata.getBody().getObject().getJSONArray("stac_extensions")
                : new JSONArray();
        for (Object ext : extensionArray) {
            String name = STACExtension.getExtensionName(ext.toString());
            try {
                STACExtension extension = STACExtension.valueOfLabel(name);
                if(extension != null) {
                    extensions.add(extension);
                }
            } catch (Exception e) {
                monitor.warn("STAC extension " + ext + "unknown. Ignored.");
            }
        }
        if (!extensions.stream().anyMatch(STACExtension::isSupported)) {
            monitor.warn("This collection does not contain a supported extension");
        }
        userData.put("stac_extensions", extensions.stream().map(STACExtension::getName));

        IGeometry geometry = service.getGeometry(userData);

        Builder builder = new ResourceBuilder(urn).withParameters(userData)
                .withGeometry(geometry).withSpatialExtent(service.getSpatialExtent());

        readMetadata(metadata.getBody().getObject(), builder);
        return builder;
    }

    private void readMetadata(final JSONObject json, Builder builder) {
        // We could check the doi only if the Scientific Notation extension is provided, but we can try anyway
        String doi = STACUtils.readDOI(json);
        if (doi != null) {
            builder.withMetadata(IMetadata.DC_URL, doi);
            String authors = STACUtils.readDOIAuthors(doi);
            if (authors != null) {
                builder.withMetadata(IMetadata.DC_CREATOR, authors);
            }
        }

        String description = STACUtils.readDescription(json);
        if (description != null) {
            builder.withMetadata(IMetadata.DC_COMMENT, description);
        }

        String keywords = STACUtils.readKeywords(json);
        if (keywords != null) {
            builder.withMetadata(IMetadata.IM_KEYWORDS, keywords);
        }

        String title = STACUtils.readTitle(json);
        if (title != null) {
            builder.withMetadata(IMetadata.DC_TITLE, title);
        }
    }

    @Override
    public IResource update(IResource resource, ResourceCRUDRequest updateData) {
        ((Resource) resource).update(updateData);
        return resource;
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
