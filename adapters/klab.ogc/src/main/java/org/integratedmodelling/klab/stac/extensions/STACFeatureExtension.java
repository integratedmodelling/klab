package org.integratedmodelling.klab.stac.extensions;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.geotools.api.data.FeatureSource;
import org.geotools.data.geojson.GeoJSONReader;
import org.geotools.data.memory.MemoryDataStore;
import org.integratedmodelling.klab.stac.STACUtils;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.hortonmachine.gears.io.stac.HMStacItem;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.stac.STACUtils;
import java.time.format.DateTimeFormatter;
import java.time.*;

public class STACFeatureExtension {
	public static FeatureSource<SimpleFeatureType, SimpleFeature> getFeatures(JSONObject catalogData, String collectionId, List<Double> bbox, ITimeInstant start, ITimeInstant end) throws Exception {
		
		System.out.println("Getting features from STAC!");
		String searchEndpoint = STACUtils.getLinkTo(catalogData, "search")
		        .orElseThrow(() -> new Exception("Search Link not found for the Catalog"));
		
		List<SimpleFeature> featureList = new ArrayList<>();
		System.out.println(searchEndpoint);

		JSONArray bboxArray = new JSONArray();
		for (Double v : bbox) {
		    bboxArray.put(v);
		}
		

		JSONObject searchPayload = new JSONObject()
				.put("limit", 100)
				.put("bbox", bboxArray)
				.put("collections", new JSONArray().put(collectionId));

		while (searchEndpoint != null) {

		    HttpResponse<JsonNode> response = Unirest
		            .post(searchEndpoint)
		            .header("Content-Type", "application/json")
		            .body(searchPayload)
		            .asJson();

		    JSONObject body = response.getBody().getObject();
		    System.out.println(body);
		
		    JSONArray features = body.getJSONArray("features");
		    System.out.println(features.length());
		    
		    Iterator<Object> featureIterator = features.iterator();

		    while (featureIterator.hasNext()) {
		        try {
		            JSONObject feature = (JSONObject) featureIterator.next();
		            SimpleFeature feat = GeoJSONReader.parseFeature(feature.toString());
		            HMStacItem item = HMStacItem.fromSimpleFeature(feat);
		            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		            
		            if (item.getStartTimestamp() != null && item.getEndTimestamp() != null) {
		            	long itemStart = LocalDateTime
	                            .parse(item.getStartTimestamp(), formatter)
	                            .atZone(ZoneOffset.UTC)
	                            .toInstant()
	                            .toEpochMilli();

	                    long itemEnd = LocalDateTime
	                            .parse(item.getEndTimestamp(), formatter)
	                            .atZone(ZoneOffset.UTC)
	                            .toInstant()
	                            .toEpochMilli();
	                    if (start.getMilliseconds() >= itemStart && end.getMilliseconds() <= itemEnd) {
	                    	featureList.add(feat);
	                    }
		            } else {
		            	 featureList.add(feat);
		            }
		           

		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		    searchEndpoint = null;
		    if (body.has("links")) {
		        JSONArray links = body.getJSONArray("links");

		        for (Object obj : links) {
		            JSONObject link = (JSONObject) obj;

		            if ("next".equalsIgnoreCase(link.optString("rel"))) {
		            	String searchEndpointNew = link.getString("href");
		            	if (searchEndpointNew.equals(searchEndpoint)) {
		            		searchEndpoint = null;
		            	}
		                break;
		            }
		        }
		    }
		}
		System.out.println(featureList.size());
        SimpleFeatureType type = featureList.get(0).getType();
        MemoryDataStore dataStore = new org.geotools.data.memory.MemoryDataStore(type);
        dataStore.addFeatures(featureList);
        return dataStore.getFeatureSource(type.getTypeName());
    }
}