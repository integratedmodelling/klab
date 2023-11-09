package org.integratedmodelling.klab.stac;

import java.util.Set;

import kong.unirest.json.JSONObject;

public class STACAssetParser {
    // https://github.com/radiantearth/stac-spec/blob/master/best-practices.md#common-media-types-in-stac
    final private static Set<String> SUPPORTED_MEDIA_TYPE = Set.of("image/tiff;application=geotiff;profile=cloud-optimized","image/vnd.stac.geotiff;profile=cloud-optimized");

    public static String readTitle(JSONObject collection) {
        return collection.has("title") ? collection.getString("title") : collection.getString("id");
    }

    public static boolean isSupportedMediaType(JSONObject asset) {
        if (!asset.has("type")) {
            return false;
        }
        return SUPPORTED_MEDIA_TYPE.contains(asset.getString("type").replace(" ", "").toLowerCase());
    }
}
