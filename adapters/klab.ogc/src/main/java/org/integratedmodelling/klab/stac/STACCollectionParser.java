package org.integratedmodelling.klab.stac;

import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;

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
                // TODO read relative path
                throw new KlabUnsupportedFeatureException();
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
}
