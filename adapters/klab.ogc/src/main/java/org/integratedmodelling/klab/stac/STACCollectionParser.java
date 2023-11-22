package org.integratedmodelling.klab.stac;

import kong.unirest.json.JSONObject;

public class STACCollectionParser {
    public static JSONObject readItemAssets(JSONObject collection) {
        return collection.getJSONObject("item_assets");
    }

    public static String readTitle(JSONObject collection) {
        return collection.has("title") ? collection.getString("title") : collection.getString("id");
    }

    public static JSONObject readAssets(JSONObject items) {
        return items.getJSONArray("features").getJSONObject(0).getJSONObject("assets"); 
    }
}
