package org.integratedmodelling.klab.stac;

import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;

import kong.unirest.json.JSONObject;

public class STACCollectionParser {
    public static JSONObject readAssets(JSONObject collection) {
        if (collection.has("item_assets")) {
            return collection.getJSONObject("item_assets");
        }
        // TODO read the assets from an item
        throw new KlabUnimplementedException();
    }

    public static String readTitle(JSONObject collection) {
        return collection.has("title") ? collection.getString("title") : collection.getString("id");
    }
}
