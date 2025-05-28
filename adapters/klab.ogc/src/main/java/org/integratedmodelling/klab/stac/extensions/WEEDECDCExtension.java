package org.integratedmodelling.klab.stac.extensions;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.hortonmachine.gears.io.rasterreader.OmsRasterReader;
import org.geotools.coverage.grid.GridCoverage2D;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.utils.FileUtils;
import java.io.OutputStream;
import java.io.FileOutputStream;

import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.net.URI;
import java.net.URISyntaxException;



/* Reading stuff from ECDC STAC
 * And getting shit done
 */

public class WEEDECDCExtension {
	
	public static GridCoverage2D getECDCCoverage(List<Double> bbox, IGeometry geometry, Object band) {
		System.out.println("Get Features call for WEED ECDC, Fetching Band" + band);
		String searchURL = "https://catalogue.weed.apex.esa.int/search";
	    JSONObject body = new JSONObject();
	    JSONArray bboxArr = new JSONArray();
	    
	    List<Double> qbbox = new ArrayList<>();
	    qbbox.add(bbox.get(0));
	    qbbox.add(bbox.get(2));
	    qbbox.add(bbox.get(1));
	    qbbox.add(bbox.get(3));
	    
	    for (Double coord:qbbox) {
	    	System.out.println(coord);
	    	bboxArr.put(coord);
	    }
	    
	    System.out.println("Getting band "+ band + " from ECDC STAC");
	    body.put("limit", 20);
	    body.put("collections", new JSONArray().put("ecosystem-characteristics-alpha2-1"));
	    body.put("filter-lang", "cql2-json");
	    body.put("bbox", bboxArr);

	    int timeout = 50000;
	    kong.unirest.HttpResponse<JsonNode> response = Unirest.post(searchURL).body(body).header("Content-Type", "application/json")
	            .connectTimeout(timeout).asJson();
	    
	    if (!response.isSuccess()) {
	        throw new KlabIllegalStateException("ERROR AT READING MODELID");
	    }
	    
	    JSONArray features = response.getBody().getObject().getJSONArray("features");
	    System.out.println("Found Items: " + features.length());
        JSONObject feature = features.getJSONObject(0); // assuming only one object in this case; if more then we are ignoring :()

        for (String key : feature.keySet()) {
            System.out.println("Key: " + key);
        }

        String assetHref = "";
        
        JSONObject asset = feature.getJSONObject("assets");
        for (String key : asset.keySet()) { 
        	try {
        		assetHref = asset.getJSONObject(key).getString("href");	
        		break;
        	} catch (Exception e) {
        		
        	}
        }
        
        System.out.println("Found Asset Href: "+ assetHref + " getting stuff from S3..");
        
        String[] bucketAndObject = assetHref.split("://")[1].split("/", 2);
        
        System.out.println(bucketAndObject[0]);
        System.out.println(bucketAndObject[1]);
        
        String assetURL = getECDCAssetURL(bucketAndObject[0], bucketAndObject[1]);
        GridCoverage2D gcov = null;
		try {
			
			File coverageFile = File.createTempFile("geo", ".tiff");
			System.out.println("Starting to make a call to STAC utils API..");
		
			
			JSONObject postPayload = new JSONObject();
			postPayload.put("url", assetURL); // The Public URL pointing to the COG
			postPayload.put("bandId", band); // The BandID in String
			 
//			if (band instanceof String) {
//				postPayload.put("bandId", band.toString());
//			} else if(band instanceof Number) {
//				postPayload.put("bandInd",  ((Number)band).intValue() + 1);
//			}
			
			kong.unirest.HttpResponse<File> stacResponse = Unirest.post("http://127.0.0.1:8000/retrieve_band")
                    .header("Content-Type", "application/json")
                    .body(postPayload)
                    .asObject(r -> {
                        try (InputStream in = r.getContent();
                             OutputStream out = new FileOutputStream(coverageFile)) {
                            byte[] buffer = new byte[8192];
                            int bytesRead;
                            while ((bytesRead = in.read(buffer)) != -1) {
                                out.write(buffer, 0, bytesRead);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException("Error writing response to file", e);
                        }
                        return coverageFile;
                    });
			
			if(stacResponse.getStatus() != 200) {
                System.out.println("Error: " + stacResponse.getStatusText());
            }
			FileUtils.forceDeleteOnExit(coverageFile);
			gcov = OmsRasterReader.readRaster(coverageFile.getAbsolutePath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return gcov;
	}
	

	/*
	 * Hardcoding stuff for now for ECDC STAC in WAW4..
	 * TODO: Update the S3 creds system in .klab/creds like OpenEO
	 * Here, the Endpoint URL would be: s3.waw4-1.cloudferro.com
	 */
	private static String getECDCAssetURL(String s3Bucket, String s3Path) {
		URI uri = null;
		try {
			uri = new URI("https", "s3.waw4-1.cloudferro.com", "/swift/v1/"+s3Bucket+"/"+s3Path, null);
			return "/vsicurl/" + uri.toASCIIString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // URI handles percent-encoding
		return uri.toASCIIString();
	}
}

