package org.integratedmodelling.klab.stac;

import java.util.Set;

import kong.unirest.json.JSONObject;

public class STACAssetParser {
    final private static Set<String> SUPPORTED_MEDIA_TYPE = Set.of("image/tiff; application=geotiff; profile=cloud-optimized");

    public static String readTitle(JSONObject collection) {
        return collection.has("title") ? collection.getString("title") : collection.getString("id");
    }

    public static boolean isSupportedMediaType(JSONObject asset) {
        return asset.has("type") && SUPPORTED_MEDIA_TYPE.contains(asset.getString("type"));
    }
}
