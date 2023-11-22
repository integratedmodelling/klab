package org.integratedmodelling.klab.stac;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import kong.unirest.json.JSONArray;
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

    /**
     * Check if the asset has file:values data and extract the value mapping.
     * https://github.com/stac-extensions/file#asset--link-object-fields
     * @param asset as JSON
     * @return A map where each value has its own summary.
     */
    public static Map<String, String> getFileValues(JSONObject asset) {
        if (!asset.has("file:values")) {
            return Map.of();
        }

        Map<String, String> ret = new HashMap<>();
        asset.getJSONArray("file:values").forEach(e -> {
            JSONObject entry = (JSONObject) e;
            JSONArray values = entry.getJSONArray("values");
            String summary = entry.getString("summary");

            values.forEach(value -> ret.put(value.toString(), summary));
        });
        return ret;
    }
}
