package org.integratedmodelling.klab.stac;

import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class STACCollectionParser {
    public static String readTitle(JSONObject collection) {
        return collection.has("title") ? collection.getString("title") : collection.getString("id");
    }

    private static JSONObject readItemAssets(JSONObject collection) {
        return collection.getJSONObject("item_assets");
    }

    private static JSONObject readAssets(JSONObject items) {
        return items.getJSONArray("features").getJSONObject(0).getJSONObject("assets"); 
    }

    /**
     * Reads the assets of a STAC collection and returns them as a JSON.
     * @param catalogUrl endpoint of the catalog
     * @param collectionId id of the collection
     * @return The asset list as a JSON
     */
    public static JSONObject readAssets(String catalogUrl, String collectionId) {
        JSONObject assets;
        JSONObject collectionData = Unirest.get(catalogUrl + "/collections/" + collectionId)
                .asJson().getBody().getObject();

        // item_assets is a shortcut for obtaining information about the assets
        // https://github.com/stac-extensions/item-assets
        if (collectionData.has("item_assets")) {
            assets = STACCollectionParser.readItemAssets(collectionData);
        } else {
            JSONObject itemsData = Unirest.get(catalogUrl + "/collections/" + collectionId + "/items")
                    .asJson().getBody().getObject();
            assets =  STACCollectionParser.readAssets(itemsData);
        }
        return assets;
    }

}
