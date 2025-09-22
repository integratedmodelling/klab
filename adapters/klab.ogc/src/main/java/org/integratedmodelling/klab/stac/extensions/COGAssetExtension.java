package org.integratedmodelling.klab.stac.extensions;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.hortonmachine.gears.io.rasterreader.OmsRasterReader;
import org.geotools.coverage.grid.GridCoverage2D;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.utils.FileUtils;
import java.io.OutputStream;
import java.io.FileOutputStream;

import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/*
 * Calls the Python API to get the requested region in the COG (Cloud Optimized geoTiff)
 * Leveraging the HTTP Get Range Request based on the COG Metadata Structure
 */
public class COGAssetExtension {

    public static GridCoverage2D getCOGWindowCoverage(List<Double> bbox, String cogURL){
        GridCoverage2D gcov = null;
        JSONArray bboxArr = new JSONArray();

        List<Double> qbbox = new ArrayList<>();
        qbbox.add(bbox.get(0));
        qbbox.add(bbox.get(2));
        qbbox.add(bbox.get(1));
        qbbox.add(bbox.get(3));
        
        for (Double coord : qbbox) {
            System.out.println(coord);
            bboxArr.put(coord);
        }

        try {
        	 File coverageFile = File.createTempFile("geo", ".tiff");
             JSONObject cogRegionPostReq = new JSONObject();
             cogRegionPostReq.put("url", cogURL); // The url to the COG
             cogRegionPostReq.put("bbox", bboxArr); // The bbox
             kong.unirest.HttpResponse<File> cogRequestResponse = Unirest
        	        .post("https://stac-utils.integratedmodelling.org/cog_region_extractor")
        	            .header("Content-Type", "application/json").body(cogRegionPostReq)
        	            .connectTimeout(600000).socketTimeout(600000).asObject(r -> {
                            try (InputStream in = r.getContent(); OutputStream out = new FileOutputStream(coverageFile)) {
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
        	        
        	        if (cogRequestResponse.getStatus() != 200) {
        	            throw new KlabResourceAccessException("Error retrieving COG region: " + cogRequestResponse.getStatusText());
        	        }
        	        FileUtils.forceDeleteOnExit(coverageFile);
        	        gcov = OmsRasterReader.readRaster(coverageFile.getAbsolutePath());

        } catch (Exception e) {
            throw new KlabIllegalStateException("Error retrieving COG region: " + e.getMessage());
        }
      
        return gcov;
    }
   
    
}