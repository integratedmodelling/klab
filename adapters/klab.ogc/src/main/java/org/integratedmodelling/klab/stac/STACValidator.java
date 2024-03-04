package org.integratedmodelling.klab.stac;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.provenance.IActivity.Description;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.data.resources.ResourceBuilder;
import org.integratedmodelling.klab.ogc.STACAdapter;
import org.integratedmodelling.klab.rest.CodelistReference;
import org.integratedmodelling.klab.rest.MappingReference;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;
import org.integratedmodelling.klab.utils.Pair;

import kong.unirest.JsonNode;
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
        String collectionId = userData.get("collectionId", String.class);
        STACService service = STACAdapter.getService(catalogUrl, collectionId);
        JsonNode metadata = STACUtils.requestCollectionMetadata(catalogUrl, collectionId);

        Set<String> extensions = readSTACExtensions(metadata);
        userData.put("stac_extensions", extensions);

        IGeometry geometry = service.getGeometry(userData);

        Builder builder = new ResourceBuilder(urn).withParameters(userData).withGeometry(geometry);

        String assetId = userData.get("asset", String.class);
        JSONObject assets = STACCollectionParser.readAssets(catalogUrl, collectionId);
        JSONObject asset = STACAssetMapParser.getAsset(assets, assetId);

        Type type = readRasterDataType(asset);
        // Currently, only files:values is supported. If needed, the classification extension could be used too.
        Map<String, Object> vals = STACAssetParser.getFileValues(asset);
        if (!vals.isEmpty()) {
            CodelistReference codelist = populateCodelist(assetId, vals);
            if (type == null) {
                type = codelist.getType();
            }
            builder.addCodeList(codelist);
        }

        if (type != null) {
            builder.withType(type);
        }
        readMetadata(metadata.getObject(), builder);
        return builder;
    }

    private Type readRasterDataType(JSONObject asset) {
        if (!asset.has("raster:bands")) {
            return null;
        }
        if (asset.getJSONArray("raster:bands").isEmpty()
                || !asset.getJSONArray("raster:bands").getJSONObject(0).has("data_type")) {
            // We could assume that most rasters are numeric. When in doubt, we could set the default to Number
            return null;
        }
        String type = asset.getJSONArray("raster:bands").getJSONObject(0).getString("data_type");
        // https://github.com/stac-extensions/raster?tab=readme-ov-file#data-types
        final Set<String> NUMERIC_DATA_TYPES = Set.of("int8", "int16", "int32", "int64", "uint8", "unit16", "uint32", "uint64", "float16", "float32", "float64");
        if (NUMERIC_DATA_TYPES.contains(type)) {
            return Type.NUMBER;
        }
        // The rest of possible values are either complex numbers or a generic "other"
        return null;
    }

    private CodelistReference populateCodelist(String assetId, Map<String, Object> vals) {
        CodelistReference codelist = new CodelistReference();
        codelist.setId(assetId.toUpperCase());
        codelist.setName(assetId);
        codelist.setAuthority(false);
        codelist.setVersion("0.0.1");
        MappingReference direct = new MappingReference();
        MappingReference inverse = new MappingReference();
        vals.entrySet().forEach(code -> {
            direct.getMappings().add(new Pair<>(code.getKey(), (String)code.getValue()));
            codelist.getCodeDescriptions().put(code.getKey(), (String)code.getValue());
        });
        Type type = STACUtils.inferValueType(vals.entrySet().stream().findFirst().get().getKey());
        codelist.setType(type);
        codelist.setDirectMapping(direct);
        codelist.setInverseMapping(inverse);
        return codelist;
    }

    private Set<String> readSTACExtensions(JsonNode response) {
        Set<String> extensions = new HashSet<>();
        if (!response.getObject().has("stac_extensions")) {
            return extensions;
        }

        JSONArray extensionArray = response.getObject().getJSONArray("stac_extensions");
        for (Object ext : extensionArray) {
            extensions.add(STACUtils.getExtensionName(ext.toString()));
        }

        return extensions;
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

        String license = STACUtils.readLicense(json);
        if (license != null) {
            builder.withMetadata(IMetadata.DC_RIGHTS, license);
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
        return resource == null && parameters.contains("catalogUrl") && parameters.contains("collectionId") && parameters.contains("asset");
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
