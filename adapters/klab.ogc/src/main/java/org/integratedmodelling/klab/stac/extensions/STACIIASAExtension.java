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
    public static FeatureSource<SimpleFeatureType, SimpleFeature> getFeatures(JSONObject collectionData) throws IOException {
        String queryFilter = "?query=True";
        String searchLink = STACUtils.getLinkTo(collectionData, "search").get();
        HttpResponse<JsonNode> response = Unirest.get(searchLink + queryFilter).asJson();
        JSONArray features = response.getBody().getObject().getJSONArray("features");
        Iterator<Object> featureIterator = features.iterator();
        List<SimpleFeature> featureList = new ArrayList<>();
        while (featureIterator.hasNext()) {
            try {
                JSONObject feature = (JSONObject) featureIterator.next();
                featureList.add(GeoJSONReader.parseFeature(feature.toString()));
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

}
