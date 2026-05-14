package org.integratedmodelling.klab.stac;

import java.util.Set;

import kong.unirest.json.JSONObject;

public class STACAssetMapParser {
    public static Set<String> readAssetNames(JSONObject assets) {
        return Set.of(JSONObject.getNames(assets));
    }

    public static JSONObject getAsset(JSONObject assetMap, String assetId) {
    	/*
    	 * Not failing if the assetId is not found, the reason being that in
    	 * some collections the items have different assets, and the Asset naming related conventions that MSFT
    	 * Planetary follows is not necessarily followed by evryone!
    	 */
        return assetMap.optJSONObject(assetId);
    }
}
