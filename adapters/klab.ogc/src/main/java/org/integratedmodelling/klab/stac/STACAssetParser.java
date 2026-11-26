package org.integratedmodelling.klab.stac;

import java.util.HashMap;
import java.util.Map;

import org.hortonmachine.gears.io.stac.HMStacAsset;
import org.hortonmachine.gears.io.stac.assets.IHMStacAssetHandler;
import org.hortonmachine.gears.io.stac.assets.IHMStacAssetRasterHandler;
import org.hortonmachine.gears.io.stac.assets.handlers.GeojsonHandler;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import tools.jackson.databind.json.JsonMapper;

public class STACAssetParser {

    private static final JsonMapper JSON = JsonMapper.builder().build();

    /**
     * Check if an asset can be read by k.LAB, see {@link #isSupported(HMStacAsset)}.
     *
     * @param asset as JSON
     * @return true if the asset is supported.
     */
    public static boolean isSupportedMediaType(JSONObject asset) {
        return isSupported(toHMStacAsset("asset", asset));
    }

    /**
     * Check if an asset can be read by k.LAB: the HortonMachine handler of the asset is a raster one (GeoTIFF,
     * ASCII grid), read by HMStacCollection.readRasterBandOnRegion, or the GeoJSON one, read by
     * STACFeatureExtension. The assets of the other handlers (shapefile, geopackage, netcdf, csv, styles) or
     * without handler (metadata, previews...) are not supported.
     *
     * @param asset the asset.
     * @return true if the asset is supported.
     */
    public static boolean isSupported(HMStacAsset asset) {
        IHMStacAssetHandler handler = asset == null ? null : asset.getHandler();
        return handler instanceof IHMStacAssetRasterHandler || handler instanceof GeojsonHandler;
    }

    /**
     * @param id the asset id.
     * @param asset the asset as JSON.
     * @return the HortonMachine asset, with its handler, null if the JSON cannot be read.
     */
    public static HMStacAsset toHMStacAsset(String id, JSONObject asset) {
        try {
            return new HMStacAsset(id, JSON.readTree(asset.toString()));
        } catch (Exception e) {
            return null;
        }
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
