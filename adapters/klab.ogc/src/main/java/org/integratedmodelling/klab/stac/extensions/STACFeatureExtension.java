package org.integratedmodelling.klab.stac.extensions;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.geotools.api.data.FeatureSource;
import org.geotools.data.geojson.GeoJSONReader;
import org.geotools.data.memory.MemoryDataStore;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.integratedmodelling.klab.stac.STACUtils;
import org.locationtech.jts.geom.Geometry;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.api.referencing.operation.MathTransform;
import org.hortonmachine.gears.io.stac.HMStacItem;
import org.hortonmachine.gears.utils.crs.HMCrsRegistry;
import org.hortonmachine.gears.utils.crs.HMCrsTransformer;

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
import org.hortonmachine.gears.io.stac.HMStacAsset;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.api.feature.type.AttributeDescriptor;
import org.geotools.api.feature.type.GeometryDescriptor;
import org.locationtech.jts.geom.util.GeometryFixer;


public class STACFeatureExtension {
	
	
	public static FeatureSource<SimpleFeatureType, SimpleFeature> getFeatures(JSONObject assetNode) throws Exception {
		List<SimpleFeature> featureList = new ArrayList<>();
		
		String href =assetNode.get("href").toString();
	    HttpResponse<JsonNode> response = Unirest
	            .get(href)
	            .asJson();

	    JSONObject body = response.getBody().getObject();
	    JSONArray features = body.getJSONArray("features");
	    Iterator<Object> featureIterator = features.iterator();

	    while (featureIterator.hasNext()) {
	        JSONObject feature = (JSONObject) featureIterator.next();
	        SimpleFeature feat = GeoJSONReader.parseFeature(feature.toString());
	        featureList.add(feat);
	    }
		

		if (featureList.isEmpty()) {
		    throw new Exception("No features found");
		}

		SimpleFeatureType type = featureList.get(0).getFeatureType();
		MemoryDataStore dataStore = new MemoryDataStore(type);
		dataStore.addFeatures(featureList);
		return dataStore.getFeatureSource(type.getTypeName());
	}
	
	public static FeatureSource<SimpleFeatureType, SimpleFeature> getFeatures(List<HMStacAsset> assets) throws Exception {
		List<SimpleFeature> featureList = new ArrayList<>();
		CoordinateReferenceSystem targetCRS = HMCrsRegistry.INSTANCE.getCrs("4326");
		SimpleFeatureType type4326 = null;
    	
		for (HMStacAsset asset : assets) {
			String href = asset.getAssetNode().get("href").asText();
		    HttpResponse<JsonNode> response = Unirest
		            .get(href)
		            .asJson();

		    JSONObject body = response.getBody().getObject();
		    JSONArray features = body.getJSONArray("features");
		    Iterator<Object> featureIterator = features.iterator();
		    

		    while (featureIterator.hasNext()) {
		        JSONObject feature = (JSONObject) featureIterator.next();
		        SimpleFeature feat = GeoJSONReader.parseFeature(feature.toString());
		        featureList.add(feat);
//		        if (!asset.getEpsg().equals(4326)) {
//		        	SimpleFeatureType type = feat.getFeatureType();
//		        	if (type4326 == null) {
//		        		SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
//		        		builder.setName(type.getTypeName());
//		        		builder.setCRS(targetCRS);
//
//		        		for (var ad : type.getAttributeDescriptors()) {
//		        		    if (ad instanceof GeometryDescriptor) {
//		        		        builder.add(
//		        		            ad.getLocalName(),
//		        		            ad.getType().getBinding(),
//		        		            targetCRS
//		        		        );
//		        		    } else {
//		        		    	 builder.add(
//		        		    	            ad.getLocalName(),
//		        		    	            ad.getType().getBinding()
//		        		    	        );
//		        		    }
//		        		}
//		        		
//		        		type4326 = builder.buildFeatureType();
//		        	}
//		        
//		        	Geometry vectorGeom = (Geometry) feat.getDefaultGeometry();
//		        	MathTransform transform = CRS.findMathTransform(HMCrsRegistry.INSTANCE.getCrs(
//		        			String.valueOf(asset.getEpsg()), true), targetCRS, true);
//		        	Geometry geom4326 = JTS.transform(vectorGeom, transform);
//		        	geom4326 = GeometryFixer.fix(geom4326);
//		        	geom4326 = geom4326.buffer(0);
//		      
//		        	SimpleFeatureBuilder fb =
//			                new SimpleFeatureBuilder(type4326);
//		        	for (var ad : type.getAttributeDescriptors()) {
//			            String name = ad.getLocalName();
//			            if (ad instanceof GeometryDescriptor) {
//			                fb.set(name, geom4326);
//			            } else {
//			                fb.set(name, feat.getAttribute(name));
//			            }
//		        	}
//		        	SimpleFeature updatedFeature = fb.buildFeature(feat.getID());
//		        	updatedFeature.setDefaultGeometry(geom4326);
//			        featureList.add(updatedFeature);
//		        } else {
//		        	featureList.add(feat);
//		        }
		    }
		}
		
		if (featureList.isEmpty()) {
		    throw new Exception("No features found");
		}

		MemoryDataStore dataStore = new MemoryDataStore(featureList.get(0).getType());
		dataStore.addFeatures(featureList);
		return  dataStore.getFeatureSource(featureList.get(0).getType().getTypeName());
	}
	
	
	public static FeatureSource<SimpleFeatureType, SimpleFeature> getFeatures(JSONObject catalogData, String collectionId, List<Double> bbox, ITimeInstant start, ITimeInstant end) throws Exception {
		
		String searchEndpoint = STACUtils.getLinkTo(catalogData, "search")
		        .orElseThrow(() -> new Exception("Search Link not found for the Catalog"));
		
		List<SimpleFeature> featureList = new ArrayList<>();
		JSONArray bboxArray = new JSONArray();
		for (Double v : bbox) {
		    bboxArray.put(v);
		}
		

		JSONObject searchPayload = new JSONObject()
				.put("limit", 1000)
				.put("bbox", bboxArray)
				.put("collections", new JSONArray().put(collectionId));

		while (searchEndpoint != null) {

		    HttpResponse<JsonNode> response = Unirest
		            .post(searchEndpoint)
		            .header("Content-Type", "application/json")
		            .body(searchPayload)
		            .asJson();

		    JSONObject body = response.getBody().getObject();
		    JSONArray features = body.getJSONArray("features");
		    
		    Iterator<Object> featureIterator = features.iterator();

		    while (featureIterator.hasNext()) {
		        try {
		            JSONObject feature = (JSONObject) featureIterator.next();
		            SimpleFeature feat = GeoJSONReader.parseFeature(feature.toString());
		            HMStacItem item = HMStacItem.fromSimpleFeature(feat);
		            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		            
		            if (item.getStartTimestamp() == null || item.getEndTimestamp() == null) { // Assume best case scenario
		            	featureList.add(feat);
		            	continue;
		            } 
		            
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
		if (featureList.isEmpty()) {
			throw new Exception("No features found for the given parameters");
		}
		
        SimpleFeatureType type = featureList.get(0).getType();
        MemoryDataStore dataStore = new MemoryDataStore(type);
        dataStore.addFeatures(featureList);
        return dataStore.getFeatureSource(type.getTypeName());
    }
}