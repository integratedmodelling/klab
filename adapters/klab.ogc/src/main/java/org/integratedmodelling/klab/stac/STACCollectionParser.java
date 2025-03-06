package org.integratedmodelling.klab.stac;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.GeometryBuilder;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;

import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema.Items;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class STACCollectionParser {
    public static String readTitle(JSONObject collection) {
        return collection.has("title") ? collection.getString("title") : collection.getString("id");
    }

    public static String readCollectionId(JSONObject collection) {
        return collection.getString("id");
    }

    /**
     * Obtains the geometry from the collection data.
     * Currently, only available for dynamic collections.
     * @param parameters
     * @return geometry
     */
    public static IGeometry readGeometry(JSONObject collection) {
        GeometryBuilder gBuilder = Geometry.builder();
        
        JSONObject extent = collection.getJSONObject("extent");
        List bbox = extent.getJSONObject("spatial").getJSONArray("bbox").getJSONArray(0).toList();
        gBuilder.space().boundingBox(Double.valueOf(bbox.get(0).toString()), Double.valueOf(bbox.get(1).toString()),
                Double.valueOf(bbox.get(2).toString()), Double.valueOf(bbox.get(3).toString()));

        List interval = extent.getJSONObject("temporal").getJSONArray("interval").getJSONArray(0).toList();
        if (interval.get(0) != null) {
            gBuilder.time().start(Instant.parse(interval.get(0).toString()).toEpochMilli());
        }
        if (interval.size() > 1 && interval.get(1) != null) {
            gBuilder.time().end(Instant.parse(interval.get(1).toString()).toEpochMilli());
        }

        // TODO find non-ad-hoc cases
        if (collection.getString("id").equals("slovak_SK_v5_reference-points_EUNIS2012")) {
            return gBuilder.build().withProjection(Projection.DEFAULT_PROJECTION_CODE).withTimeType("logical");
        }
        return gBuilder.build().withProjection(Projection.DEFAULT_PROJECTION_CODE).withTimeType("grid");
    }

    /**
     * Reads the assets of a STAC collection and returns them as a JSON.
     * @param collection as a JSON
     * @return The asset list as a JSON
     * @throws KlabResourceAccessException
     */
    public static JSONObject readAssetsFromCollection(String collectionUrl, JSONObject collection) throws KlabResourceAccessException {
        String collectionId = collection.getString("id");
        String catalogUrl = STACUtils.getCatalogUrl(collectionUrl, collectionId, collection);
        JSONObject catalogData = STACUtils.requestMetadata(catalogUrl, "catalog");

        Optional<String> searchEndpoint = STACUtils.containsLinkTo(catalogData, "search") 
                ? STACUtils.getLinkTo(catalogData, "search")
                : STACUtils.getLinkTo(collection, "search");

        // Static catalogs should have their assets on the Collection
        if (searchEndpoint.isEmpty()) {
            // Check the assets
            if (collection.has("assets")) {
                return collection.getJSONObject("assets");
            }
            // Try to get the assets from a link that has type `item`
            Optional<String> itemHref = STACUtils.getLinkTo(collection, "item");
            if (itemHref.isEmpty()) {
                throw new KlabResourceNotFoundException("Cannot find items at STAC collection \"" + collectionUrl + "\"");
            }
            String itemUrl = itemHref.get().startsWith(".")
                    ? collectionUrl.replace("collection.json", "") + itemHref.get().replace("./", "")
                    : itemHref.get();
            // TODO get assets from the item
            JSONObject itemData = STACUtils.requestMetadata(itemUrl, "feature");
            if (itemData.has("assets")) {
                return itemData.getJSONObject("assets");
            }
            throw new KlabResourceNotFoundException("Cannot find assets at STAC collection \"" + collectionUrl + "\"");
        }

        // TODO Move the query to another place. 
        String parameters = "?collections=" + collectionId + "&limit=1";
        HttpResponse<JsonNode> response = Unirest.get(searchEndpoint.get() + parameters).asJson();

        if (!response.isSuccess()) {
            throw new KlabResourceAccessException(); //TODO set message
        }

        JSONObject searchResponse = response.getBody().getObject();
        if (searchResponse.getJSONArray("features").length() == 0) {
            throw new KlabResourceAccessException(); // TODO set message there is no feature
        }

        return searchResponse.getJSONArray("features").getJSONObject(0).getJSONObject("assets");
    }
}
