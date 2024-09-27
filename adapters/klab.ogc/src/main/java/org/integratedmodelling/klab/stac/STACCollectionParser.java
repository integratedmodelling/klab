package org.integratedmodelling.klab.stac;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.GeometryBuilder;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;

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

    private static JSONObject readItemAssets(JSONObject collection) {
        return collection.getJSONObject("item_assets");
    }

    public static IGeometry readGeometry(JSONObject collection) {
        GeometryBuilder gBuilder = Geometry.builder();
        DateTimeFormatter filterTimestampFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

        JSONObject extent = collection.getJSONObject("extent");
        List<String> interval = extent.getJSONObject("temporal").getJSONArray("interval").getJSONArray(0).toList();

        if (interval.get(0) != null) {
            LocalDateTime start = LocalDateTime.parse(interval.get(0), filterTimestampFormatter);
            gBuilder.time().start(start.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli());
        }
        if (interval.size() > 1 && interval.get(1) != null) {
            LocalDateTime start = LocalDateTime.parse(interval.get(1), filterTimestampFormatter);
            gBuilder.time().start(start.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli());
        }

        List<Double> bbox = extent.getJSONObject("spatial").getJSONArray("bbox").getJSONArray(0).toList();

        gBuilder.space().boundingBox(bbox.get(0), bbox.get(1), bbox.get(2), bbox.get(3));
        return gBuilder.build();
    }

    /**
     * Reads the assets of a STAC collection and returns them as a JSON.
     * @param collection as a JSON
     * @return The asset list as a JSON
     * @throws KlabResourceAccessException
     */
    public static JSONObject readAssetsFromCollection(String collectionUrl, JSONObject collection) throws KlabResourceAccessException {
        String catalogUrl = STACUtils.getCatalogUrl(collection);
        JSONObject catalogData = STACUtils.requestMetadata(catalogUrl, "catalog");

        boolean hasSearchOption = STACUtils.containsLinkTo(catalogData, "search");
        // Static catalogs should have their assets on the Collection
        if (!hasSearchOption) {
            // Check the assets
            if (!collection.has("asset")) {
                throw new KlabResourceNotFoundException("Static STAC collection \"" + collectionUrl + "\" has no assets");
            }
            return collection.getJSONObject("asset");
        }

        // item_assets is a shortcut for obtaining information about the assets
        // https://github.com/stac-extensions/item-assets
        if (collection.has("item_assets")) {
            return STACCollectionParser.readItemAssets(collection);
        }

        Optional<String> searchEndpoint = STACUtils.getLinkTo(catalogData, "search");
        if (searchEndpoint.isEmpty()) {
            throw new KlabResourceAccessException("Cannot search in the STAC collection " + collectionUrl);
        }

        // TODO allow POST and GET. Move the query to another place. 
        String body = "{collections:[\"" + searchEndpoint.get() + "\"], \"limit\": 1}";
        HttpResponse<JsonNode> response = Unirest.post(collectionUrl).body(body).asJson();

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
