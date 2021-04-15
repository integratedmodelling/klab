package org.integratedmodelling.klab.documentation;

import java.util.Map;

import org.integratedmodelling.klab.utils.MapUtils;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

/**
 * Interface to Crossref.org citation service and resolution of DOI
 * 
 * @author Ferd
 *
 */
public enum Crossref {

    INSTANCE;

    public Map<?,?> resolveDOI(String DOI) {
        if (!DOI.startsWith("http")) {
            DOI = "https://doi.org/" + DOI;
        }
         HttpResponse<JsonNode> response = Unirest.get(DOI).header("Accept", "application/vnd.citationstyles.csl+json").asJson();
        if (response.isSuccess()) {
            JsonNode data = response.getBody();
            return data.getObject().toMap();
        }
        return null;
    }

    public static void main(String[] args) {
        Map<?, ?> map = INSTANCE.resolveDOI("10.1126/science.169.3946.635");
        System.out.println(MapUtils.dump(map));
    }
    
}
