package org.integratedmodelling.klab.stac;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
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
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.rest.CodelistReference;
import org.integratedmodelling.klab.rest.MappingReference;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;
import org.integratedmodelling.klab.utils.Pair;

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
        System.out.println("Starting to Validate STAC Resource...");
        String collectionUrl = userData.get("collection", String.class);
        String collectionId = userData.get("collectionId", String.class);
        JSONObject collectionData = STACUtils.requestMetadata(collectionUrl, "collection");
        if (collectionId ==  null) {
            collectionId = collectionData.getString("id");
            userData.put("collectionId", collectionId);
        }
        IGeometry geometry = STACCollectionParser.readGeometry(collectionData);
        
        Builder builder = null;
        
     // Don't pass Asset for ECDC since ECDC is fucked up!
    	if (collectionUrl.contains("ecosystem-characteristics-alpha2-1")) {
    		System.out.println("Starting to Validate ECDC STAC resource...");
    		builder = new ResourceBuilder(urn)
                    .withParameters(userData)
                    .withGeometry(geometry)
                    .withType(Type.NUMBER); // Setting type to be Number instead of Object
    	} else {
    		builder = new ResourceBuilder(urn)
                    .withParameters(userData)
                    .withGeometry(geometry)
                    .withType(Type.OBJECT);
    	}
        

        // The default URL of the resource is the collection endpoint. May be overwritten. 
        builder.withMetadata(IMetadata.DC_URL, collectionUrl);
        
        if (userData.contains("asset")) {
        	System.out.println("Found asset...");
            String assetId = userData.get("asset", String.class);
            JSONObject assets = STACCollectionParser.readAssetsFromCollection(collectionUrl, collectionData);
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
        }
        
        readMetadata(collectionData, builder);
        return builder;
    }

    private Type readRasterDataType(JSONObject asset) {
        if (!asset.has("raster:bands")) {
            return null;
        }
        if (asset.getJSONArray("raster:bands").isEmpty()
                || !asset.getJSONArray("raster:bands").getJSONObject(0).has("data_type")) {
            // We assume that most rasters are numeric. When in doubt, we set the default to Number
            return Type.NUMBER;
        }
        String type = asset.getJSONArray("raster:bands").getJSONObject(0).getString("data_type");
        // https://github.com/stac-extensions/raster?tab=readme-ov-file#data-types
        final Set<String> NUMERIC_DATA_TYPES = Set.of("int8", "int16", "int32", "int64", "uint8", "unit16", "uint32", "uint64", "float16", "float32", "float64");
        if (NUMERIC_DATA_TYPES.contains(type)) {
            return Type.NUMBER;
        }
        final Set<String> COMPLEX_DATA_TYPES = Set.of("cint16", "cint32", "cfloat32", "cfloat64");
        if (COMPLEX_DATA_TYPES.contains(type)) {
            throw new KlabUnimplementedException("STAC resource contains raster of complex numbers.");
        }
        // The only possible value left is a generic "other".
        // It could be boolean, string, higher precision numbers, or any other type.
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
    
    private CodelistReference populateCodelist(String collectionId) {
        CodelistReference codelist = new CodelistReference();
        codelist.setId(collectionId.toUpperCase());
        codelist.setName(collectionId);
        codelist.setAuthority(false);
        codelist.setVersion("0.0.1");
        MappingReference direct = new MappingReference();
        MappingReference inverse = new MappingReference();
        codelist.setDirectMapping(direct);
        codelist.setInverseMapping(inverse);
        return codelist;
    }

    private void readMetadata(final JSONObject json, Builder builder) {
        // We could check the doi only if the Scientific Notation extension is provided, but we can try anyway
        String doi = STACUtils.readDOI(json);
        if (doi != null && !doi.isBlank()) {
            builder.withMetadata(IMetadata.DC_URL, doi);
            String authors = STACUtils.readDOIAuthors(doi);
            if (authors != null) {
                builder.withMetadata(IMetadata.DC_CREATOR, authors);
            }
        }

        String description = STACUtils.readDescription(json);
        if (description != null && !description.isBlank()) {
            builder.withMetadata(IMetadata.DC_COMMENT, description);
        }

        String keywords = STACUtils.readKeywords(json);
        if (keywords != null && !keywords.isBlank()) {
            builder.withMetadata(IMetadata.IM_KEYWORDS, keywords);
        }

        String title = STACUtils.readTitle(json);
        if (title != null && !title.isBlank()) {
            builder.withMetadata(IMetadata.DC_TITLE, title);
        }

        String license = STACUtils.readLicense(json);
        if (license != null && !license.isBlank()) {
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
        return resource == null && parameters.contains("collection");
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
