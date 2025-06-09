package org.integratedmodelling.klab.stac.extensions;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class StacWeedAlpha3Extension {

    static String getResultCollectionUrl(String digitalId, String scenarioId) {
        return "https://catalogue.weed.apex.esa.int/collections/" + digitalId + "-" + scenarioId;
    }

    public static String getJobDbCollectionUrl(String digitalId, String scenarioId) {
        return "https://catalogue.weed.apex.esa.int/collections/" + digitalId + "-" + scenarioId;
    }

    public static boolean haveAllJobsFinished(String digitalId, String scenarioId) {
        String items = getJobDbCollectionUrl(digitalId, scenarioId) + "/items";
        items = "https://catalogue.weed.apex.esa.int/collections/test_bert-v100-jobdb";
        HttpResponse<JsonNode> response = Unirest.get(items).asJson(); // https://catalogue.weed.apex.esa.int/collections/test_bert-v100-jobdb

        if (!response.isSuccess()) {
            return false;
        }

        JSONArray features = response.getBody().getArray();
        boolean isError = features.toList().stream().anyMatch(
                feature -> ((JSONObject)feature).getJSONObject("properties").getString("status").equals("finished"));
        
        if (isError) {
            return false;
        }

        return true;
    }
}
