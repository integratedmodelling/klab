package org.integratedmodelling.klab.stac;

import java.time.Instant;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.IntStream;

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
import kong.unirest.json.JSONArray;
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
     * Reads the asset of a STAC collection and returns them as a JSON based on Asset Key.
     * @param collection as a JSON
     * @return The asset matching the assetId as a JSONObject
     * @throws KlabResourceAccessException
     */
    public static JSONObject readAssetInformationFromCollection(String collectionUrl, JSONObject collection, String assetId) throws KlabResourceAccessException {
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

        JSONObject searchPayload = new JSONObject()
				.put("limit", 100)
				.put("bbox", new JSONArray().put(-180.0).put(-90.0).put(180.0).put(90.0))
				.put("collections", new JSONArray().put(collectionId));
        
        HttpResponse<JsonNode> response = Unirest
	            .post(searchEndpoint.get())
	            .header("Content-Type", "application/json")
	            .body(searchPayload)
	            .asJson();

        if (!response.isSuccess()) {
            throw new KlabResourceAccessException("Unable to import collection, Search failed"); //TODO set message
        }

        JSONObject searchResponse = response.getBody().getObject();
        if (searchResponse.getJSONArray("features").length() == 0) {
            throw new KlabResourceAccessException("No features were found in the collection to be imported"); // TODO set message there is no feature
        }
        
        JSONArray features = searchResponse.getJSONArray("features");

        for (int i = 0; i < features.length(); i++) {
            JSONObject feature = features.getJSONObject(i);

            JSONObject assetInfo = feature
                .getJSONObject("assets")
                .optJSONObject(assetId);

            if (assetInfo != null) {
                return assetInfo;
            }
        }

        throw new KlabResourceAccessException("No Asset with ID: " + assetId + " was found in the collection");
    }


    /*
    Retrieves the asset matching the Predicate
     */
    public static JSONObject readAssetInformationFromCollectionWithPredicate(String collectionUrl, JSONObject collection, Predicate<JSONObject> p) throws KlabResourceAccessException {
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

        JSONObject searchPayload = new JSONObject()
				.put("limit", 100)
				.put("bbox", new JSONArray().put(-180.0).put(-90.0).put(180.0).put(90.0))
				.put("collections", new JSONArray().put(collectionId));
        
        HttpResponse<JsonNode> response = Unirest
	            .post(searchEndpoint.get())
	            .header("Content-Type", "application/json")
	            .body(searchPayload)
	            .asJson();

        if (!response.isSuccess()) {
            throw new KlabResourceAccessException("Unable to import collection, Search failed"); //TODO set message
        }

        JSONObject searchResponse = response.getBody().getObject();
        if (searchResponse.getJSONArray("features").length() == 0) {
            throw new KlabResourceAccessException("No features were found in the collection to be imported"); // TODO set message there is no feature
        }
        
        JSONArray features = searchResponse.getJSONArray("features");

        JSONObject matchingFeature = IntStream.range(0, features.length())
            .mapToObj(i -> features.getJSONObject(i))
            .filter(p)
            .findFirst()
            .orElse(null);

        if (matchingFeature == null) {
            throw new KlabResourceAccessException("No Feature found containing an asset matching the predicate based on JSONSelector and JSONValidator was found in the collection");
        }
        JSONObject assets = matchingFeature.getJSONObject("assets");
        JSONObject assetInfo = assets.keySet().stream()
            .map(assets::getJSONObject)
            .filter(asset -> p.test(asset))
            .findFirst()
            .orElse(null);
        
        if (assetInfo == null) {
            throw new KlabResourceAccessException("No Asset was found in the matching Fearure"); // shouldn't happen
        }
        return assetInfo;
    }
}
