package org.integratedmodelling.klab.stac;

import java.time.Instant;
import java.util.*;
import java.util.function.Predicate;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.GeometryBuilder;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;

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

    private static JSONObject findAsset(JSONObject assets, String assetId, Predicate<JSONObject> predicate) {
        if (assets == null) {
            return null;
        }

        if (assetId != null) {
            JSONObject asset = assets.optJSONObject(assetId);
            if (asset == null) {
                return null;
            }
            JSONObject result = new JSONObject();
            result.put(assetId, asset);
            return result;
        }

        if (predicate != null) {
            return assets.keySet().stream().map(key -> {
                JSONObject asset = assets.optJSONObject(key);
                if (asset == null || !predicate.test(asset)) {
                    return null;
                }
                JSONObject result = new JSONObject();
                result.put(key, asset);
                return result;
            }).filter(Objects::nonNull).findFirst().orElse(null);
        }

        return null;
    }

    private static JSONObject readAssetInformationFromCollection(String collectionUrl, JSONObject collection, String assetId,
            Predicate<JSONObject> predicate) throws KlabResourceAccessException {
        if ((assetId == null) == (predicate == null)) {
            throw new KlabResourceAccessException("Exactly one of assetId or predicate must be provided");
        }
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
                JSONObject assetInfo = findAsset(collection.optJSONObject("assets"), assetId, predicate);
                if (assetInfo != null) {
                    return assetInfo;
                }
            }
            // Try to get the assets from a link that has type `item`
            Optional<String> itemHref = STACUtils.getLinkTo(collection, "item");
            if (itemHref.isEmpty()) {
                throw new KlabResourceNotFoundException("Cannot find items at STAC collection \"" + collectionUrl + "\"");
            }
            String itemUrl = itemHref.get().startsWith(".")
                    ? collectionUrl.replace("collection.json", "") + itemHref.get().replace("./", "")
                    : itemHref.get();
            JSONObject itemData = STACUtils.requestMetadata(itemUrl, "feature");
            JSONObject assetInfo = findAsset(itemData.optJSONObject("assets"), assetId, predicate);
            if (assetInfo != null) {
                return assetInfo;
            }
            throw new KlabResourceNotFoundException("Cannot find assets at STAC collection \"" + collectionUrl + "\"");
        }

        JSONObject searchPayload = new JSONObject().put("limit", 100)
                .put("bbox", new JSONArray().put(-180.0).put(-90.0).put(180.0).put(90.0))
                .put("collections", new JSONArray().put(collectionId));

        HttpResponse<JsonNode> response = Unirest.post(searchEndpoint.get()).header("Content-Type", "application/json")
                .body(searchPayload).asJson();

        if (!response.isSuccess()) {
            throw new KlabResourceAccessException("Unable to import collection, Search failed"); 
        }

        JSONObject searchResponse = response.getBody().getObject();
        JSONArray features = searchResponse.optJSONArray("features");
        if (features == null || features.length() == 0) {
            throw new KlabResourceAccessException("No features were found in the collection to be imported");
        }

        for(int i = 0; i < features.length(); i++) {
            JSONObject feature = features.optJSONObject(i);
            if (feature == null) {
                continue;
            }

            JSONObject assetInfo = findAsset(feature.optJSONObject("assets"), assetId, predicate);
            if (assetInfo != null) {
                return assetInfo;
            }
        }
        if (assetId != null) {
            throw new KlabResourceAccessException("No asset with ID \"" + assetId + "\" was found in the collection");
        }

        throw new KlabResourceAccessException("No asset matching the predicate was found in the collection");
    }

    /**
     * Reads an asset from a STAC collection by asset key.
     *
     * @param collectionUrl URL of the STAC collection
     * @param collection collection metadata as JSON
     * @param assetId asset key to find inside each feature's assets object
     * @return a JSONObject containing one entry: assetId -> asset JSON object
     * @throws KlabResourceAccessException if the collection cannot be searched or no matching asset is found
     */
    public static JSONObject readAssetInformationFromCollection(String collectionUrl, JSONObject collection, String assetId)
            throws KlabResourceAccessException {
        return readAssetInformationFromCollection(collectionUrl, collection, assetId, null);
    }

    /**
     * Reads the first asset in a STAC collection whose asset JSON object matches the predicate.
     *
     * @param collectionUrl URL of the STAC collection
     * @param collection collection metadata as JSON
     * @param predicate predicate evaluated against each asset JSON object
     * @return a JSONObject containing one entry: assetId -> asset JSON object
     * @throws KlabResourceAccessException if the collection cannot be searched or no matching asset is found
     */
    public static JSONObject readAssetInformationFromCollection(String collectionUrl, JSONObject collection,
            Predicate<JSONObject> predicate) throws KlabResourceAccessException {
        return readAssetInformationFromCollection(collectionUrl, collection, null, predicate);
    }
}
