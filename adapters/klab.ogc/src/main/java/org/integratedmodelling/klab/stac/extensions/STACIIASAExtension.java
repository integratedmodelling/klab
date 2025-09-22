package org.integratedmodelling.klab.stac.extensions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.geotools.data.FeatureSource;
import org.geotools.data.geojson.GeoJSONReader;
import org.geotools.data.memory.MemoryDataStore;
import org.integratedmodelling.klab.stac.STACUtils;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class STACIIASAExtension {
    public static FeatureSource<SimpleFeatureType, SimpleFeature> getFeatures(JSONObject collectionData, List<Double> bbox) throws IOException {
        String queryFilter = "?query=True&bbox="+bbox.get(0) + "," + bbox.get(1) + "," + bbox.get(2) + "," + bbox.get(3);
        String searchLink = STACUtils.getLinkTo(collectionData, "search").get();
        HttpResponse<JsonNode> response = Unirest.get(searchLink + queryFilter).asJson();
        JSONArray features = response.getBody().getObject().getJSONArray("features");
        Iterator<Object> featureIterator = features.iterator();
        List<SimpleFeature> featureList = new ArrayList<>();
        while (featureIterator.hasNext()) {
            try {
                JSONObject feature = (JSONObject) featureIterator.next();
                String labelName = null;
                List<String> labels = getBestLabelValue(feature.getJSONObject("properties"));
                Iterator<Object> classes = collectionData.getJSONArray("label:classes").iterator();
                while(classes.hasNext()) {
                    JSONObject clazz = (JSONObject) classes.next();
                    for (String label : labels) {
                        if (clazz.getString("value").equalsIgnoreCase(label)) {
                            labelName = clazz.getString("name");
                            break;
                        }
                    }
                }
                //String value = (String) collectionData.getJSONArray("label:classes").toList().stream().filter(l -> ((JSONObject)l).getString("value").equalsIgnoreCase(label)).findFirst().get();
                feature.getJSONObject("properties").put("eunis", labelName);
                SimpleFeature feat = GeoJSONReader.parseFeature(feature.toString());
                featureList.add(feat);
            } catch (Exception e) {
                // Ignore unparseable features (they should not happen)
                continue;
            }
        }
        SimpleFeatureType type = featureList.get(0).getType();
        MemoryDataStore dataStore = new org.geotools.data.memory.MemoryDataStore(type);
        dataStore.addFeatures(featureList);
        return dataStore.getFeatureSource(type.getTypeName());
    }

    private static List<String> getBestLabelValue(JSONObject properties) {
        List<String> labels = new ArrayList<String>();
        if (properties.has("eunis2012_L3")) {
            labels.add(properties.getString("eunis2012_L3"));
        }
        if (properties.has("eunis2012_L2")) {
            labels.add(properties.getString("eunis2012_L2"));
        }
        if (properties.has("eunis2012_L1")) {
            labels.add(properties.getString("eunis2012_L1"));
        }
        if (properties.has("eunis2021_L3")) {
            labels.add(properties.getString("eunis2021_L3"));
        }
        if (properties.has("eunis2021_L2")) {
            labels.add(properties.getString("eunis2021_L2"));
        }
        if (properties.has("eunis2021_L1")) {
            labels.add(properties.getString("eunis2021_L1"));
        }

        return labels;
    }


}
