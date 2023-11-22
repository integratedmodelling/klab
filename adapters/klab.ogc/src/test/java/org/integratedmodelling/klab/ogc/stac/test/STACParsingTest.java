package org.integratedmodelling.klab.ogc.stac.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import java.util.Map;

import org.integratedmodelling.klab.stac.STACAssetParser;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import kong.unirest.json.JSONObject;

public class STACParsingTest {
    @Test
    public void parseFileValues() throws JsonMappingException, JsonProcessingException {
        String assetJSON = "{'type':'image/tiff; application=geotiff; profile=cloud-optimized','roles':['data'],'title':'Global land cover data',"
                + "'file:values':[{'values':[0],'summary':'No Data'},{'values':[1],'summary':'Water'},{'values':[2, 3],'summary':'Trees'},{'values':[4],'summary':'Flooded vegetation'},{'values':[5],'summary':'Crops'}]}";
        JSONObject node = new JSONObject(assetJSON);

        Map<String, String> result = STACAssetParser.getFileValues(node);

        assertThat(result.keySet(), containsInAnyOrder("0", "1", "2", "3", "4", "5"));
        assertThat(result.values(), containsInAnyOrder("No Data", "Water", "Trees", "Trees", "Flooded vegetation", "Crops"));
    }

    @Test
    public void parseWithNoFileValues() {
        String assetJSON = "{'type':'image/tiff; application=geotiff; profile=cloud-optimized','roles':['data'],'title':'Global land cover data'}";
        JSONObject node = new JSONObject(assetJSON);

        Map<String, String> result = STACAssetParser.getFileValues(node);

        assertThat(result.values(), is(empty()));
    }

    @Test
    public void parseClassificationClassAtRasterBands() {
        String assetJSON = "{'href':'example.tif','type':'image/tiff; profile=geotiff','roles':['data'],"
                + "'raster:bands':[{'classification:classes':[{'value':0,'name':'no_data','description':'No data'},{'value':1,'name':'clouds','description':'Clouds'},{'value':2,'name':'cloud_shadows','description':'Clouds Shadows'}]}]}";
        JSONObject node = new JSONObject(assetJSON);
        JSONObject band = node.getJSONArray("raster:bands").getJSONObject(0);
        Map<String, String> result = STACAssetParser.getClassificationValues(band);

        assertThat(result.keySet(), containsInAnyOrder("0", "1", "2"));
        assertThat(result.values(), containsInAnyOrder("no_data", "clouds", "cloud_shadows"));
    }

    @Test
    @Disabled
    public void parseClassificationBitFieldAtRasterBands() {
        String assetJSON = "{'href':'example.tif','type':'image/tiff; profile=geotiff','roles':['data'],'raster:bands':{['classification:bitfields'{[\n"
                + "  {'name':'fill','description':'Corresponding pixels in L1 image bands are fill','offset':0,'length':1,'classes':[{'name':'not_fill','description':'L1 image band pixels are not fill','value':0},{'name':'fill','description':'L1 image band pixels are fill','value':1}]},\n"
                + "  {'name':'dilated','description':'Dilated cloud','offset':1,'length':1,'classes':[{'name':'not_dilated','description':'Cloud is not dilated or no cloud','value':0},{'name':'dilated','description':'Cloud dilation','value':1}]},\n"
                + "  {'name':'cirrus','description':'Cirrus mask','offset':2,'length':1,'classes':[{'name':'not_cirrus','description':'No confidence level set or low confidence cirrus','value':0},{'name':'cirrus','description':'High confidence cirrus','value':1}]},\n"
                + "  {'name':'cloud','description':'Cloud mask','offset':3,'length':1,'classes':[{'name':'not_cloud','description':'Cloud confidence is not high','value':0},{'name':'cloud','description':'High confidence cloud','value':1}]},\n"
                + "]}]}";
        JSONObject node = new JSONObject(assetJSON);

        Map<String, String> result = STACAssetParser.getClassificationValues(node);

        // TODO analyze how to create the key for bit fields
    }
}
