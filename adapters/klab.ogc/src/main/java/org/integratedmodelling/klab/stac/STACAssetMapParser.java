package org.integratedmodelling.klab.stac;

import java.util.Set;

import kong.unirest.json.JSONObject;

public class STACAssetMapParser {
    public static Set<String> readAssetNames(JSONObject assets) {
        return Set.of(JSONObject.getNames(assets));
    }

    public static JSONObject getAsset(JSONObject assetMap, String assetId) {
        return assetMap.getJSONObject(assetId);
    }
}
