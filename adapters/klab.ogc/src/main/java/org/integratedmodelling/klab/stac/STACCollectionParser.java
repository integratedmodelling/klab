package org.integratedmodelling.klab.stac;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.GeometryBuilder;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
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

    private static JSONObject readAssetsFromItems(JSONObject items) {
        return items.getJSONArray("features").getJSONObject(0).getJSONObject("assets"); 
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
        JSONObject assets;

        // item_assets is a shortcut for obtaining information about the assets
        // https://github.com/stac-extensions/item-assets
        if (collection.has("item_assets")) {
            assets = STACCollectionParser.readItemAssets(collection);
        } else {
            if (STACUtils.usesRelativePath(collectionUrl)) {
                List<JSONObject> items = STACCollectionParser.readItemsFromLinks(collection);
                // We can take the first one as a model
                String linkHref = items.get(0).getString("href");
                String itemUrl = getItemUrl(collectionUrl, linkHref);
                JSONObject itemData = STACUtils.requestMetadata(itemUrl, "feature");
                return itemData.getJSONObject("assets");
            }
            HttpResponse<JsonNode> response = Unirest.get(collectionUrl + "/items").asJson();
            if (!response.isSuccess()) {
                throw new KlabResourceAccessException("Cannot read items at " + collectionUrl + "/items");
            }
            JSONObject itemsData = response.getBody().getObject();
            assets =  STACCollectionParser.readAssetsFromItems(itemsData);
        }
        return assets;
    }

    private static List<JSONObject> readItemsFromLinks(JSONObject collection) {
        List<JSONObject> links = collection.getJSONArray("links").toList();
        links.removeIf(l -> !l.getString("rel").equalsIgnoreCase("item"));
        return links;
    }

    private static String getItemUrl(String collectionUrl, String itemHref) {
        return collectionUrl.replace("collection.json", itemHref.replace("./", ""));
    }
}
