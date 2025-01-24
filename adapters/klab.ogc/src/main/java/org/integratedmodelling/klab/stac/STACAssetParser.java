package org.integratedmodelling.klab.stac;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class STACAssetParser {
    // https://github.com/radiantearth/stac-spec/blob/master/best-practices.md#common-media-types-in-stac
    final private static Set<String> SUPPORTED_MEDIA_TYPE = Set.of("image/tiff;application=geotiff", "image/vnd.stac.geotiff",
            "image/tiff;application=geotiff;profile=cloud-optimized", "image/vnd.stac.geotiff;profile=cloud-optimized",
            "image/vnd.stac.geotiff;cloud-optimized=true", "application/geo+json");

    /** 
     * Check if the MIME value is supported.
     * @param asset as JSON
     * @return true if the media type is supported.
     */
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
    public static Map<String, Object> getFileValues(JSONObject asset) {
        if (!asset.has("file:values")) {
            return Map.of();
        }

        Map<String, Object> ret = new HashMap<>();
        asset.getJSONArray("file:values").forEach(e -> {
            JSONObject entry = (JSONObject) e;
            JSONArray values = entry.getJSONArray("values");
            Object summary = entry.get("summary");

            values.forEach(value -> ret.put(value.toString(), summary));
        });
        return ret;
    }

    /**
     * NOTE: the classification extension is still in the pilot phase and may be subject to change.
     * https://github.com/stac-extensions/classification#class-object
     * This extension can be used at the asset objects, raster:bands and item_assets.
     * @param asset or raster:bands as a JSON
     * @return A map where each value has its own value.
     */
    public static Map<Integer, String> getClassificationClasses(JSONObject json) {
        if (!json.has("classification:classes")) {
            return Map.of();
        }

        Map<Integer, String> ret = new HashMap<>();
        json.getJSONArray("classification:classes").forEach(c -> {
            JSONObject entry = (JSONObject) c;
            int value = entry.getInt("value");
            String name = entry.getString("name");
            ret.put(value, name);
        });
        return ret;
    }
}
