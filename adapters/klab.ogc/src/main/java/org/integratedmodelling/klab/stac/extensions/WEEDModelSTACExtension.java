package org.integratedmodelling.klab.stac.extensions;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.data.geojson.GeoJSONReader;
import org.hortonmachine.gears.io.stac.HMStacManager;
import org.hortonmachine.gears.libs.monitor.LogProgressMonitor;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class WEEDModelSTACExtension {
	
	String MODEL_STAC_API = "https://catalogue.weed.apex.esa.int/collections/model-STAC";
	
	public static List<String> GetONNXModelIDs(List<Double> bbox, IMonitor monitor, IObservable targetSemantics) {
		
		
		monitor.debug("Making a Search Query to the Model STAC");
		try {
	       
			JSONObject searchPayload = new JSONObject()
				.put("limit", 1000)
				.put("bbox", new ArrayList<>(List.of(
					    bbox.get(0),
					    bbox.get(2),
					    bbox.get(1),
					    bbox.get(3)
					)))
						
				.put("collections", new JSONArray().put("model-STAC"));

		    HttpResponse<JsonNode> response = Unirest
		            .post("https://catalogue.weed.apex.esa.int/search")
		            .header("Content-Type", "application/json")
		            .body(searchPayload)
		            .asJson();

		    JSONObject body = response.getBody().getObject();
		    JSONArray features = body.getJSONArray("features");
		    
		    List<Map.Entry<String, Float>> pairs = new ArrayList<>();
		    
		    float highestModelVersion = Float.MIN_VALUE;
		    
		    Iterator<Object> featureIterator = features.iterator();
		    while (featureIterator.hasNext()) {
		    	JSONObject feature = (JSONObject) featureIterator.next();
	            SimpleFeature feat = GeoJSONReader.parseFeature(feature.toString());
	            if (targetSemantics.toString().toLowerCase().contains("eunis")) {
	            	if (feat.getAttribute("topology") != null && 
	            			feat.getAttribute("topology").toString().toLowerCase().startsWith("eunis")) { // it should've been "typology" instead of "topology"
	            		pairs.add(Map.entry(feat.getID(), ((Number) feat.getAttribute("model_version")).floatValue()));
	            		highestModelVersion = Math.max(highestModelVersion, ((Number) feat.getAttribute("model_version")).floatValue());
	            	}
	            }
	            
	            if (targetSemantics.toString().toLowerCase().contains("iucn")) {
	            	if (feat.getAttribute("topology") != null && 
	            			feat.getAttribute("topology").toString().toLowerCase().startsWith("iucn")) { // it should've been "typology" instead of "topology"
	            		pairs.add(Map.entry(feat.getID(), ((Number) feat.getAttribute("model_version")).floatValue()));
	            		highestModelVersion = Math.max(highestModelVersion, ((Number) feat.getAttribute("model_version")).floatValue());
	            	}
	            }
		    }
		    
		    var filterModelVersion = highestModelVersion;
		    pairs = pairs.stream()
		    	    .filter(item -> item.getValue().equals(filterModelVersion))
		    	    .toList();
		    
		    return pairs.stream()
		    	    .map(Map.Entry::getKey)
		    	    .toList();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
