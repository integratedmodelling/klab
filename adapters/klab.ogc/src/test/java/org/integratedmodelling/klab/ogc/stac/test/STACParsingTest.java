package org.integratedmodelling.klab.ogc.stac.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import java.util.Map;

import org.integratedmodelling.klab.stac.STACAssetParser;
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
}
