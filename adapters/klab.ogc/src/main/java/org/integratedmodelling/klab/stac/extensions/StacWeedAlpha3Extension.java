package org.integratedmodelling.klab.stac.extensions;

import java.util.List;

import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabRemoteException;
import org.integratedmodelling.klab.openeo.OpenEO.Job;

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

    final static List<String> finishStatus = List.of("canceled", "finished", "error");
    private static boolean hasFinished(String status) {
        return finishStatus.contains(status.toLowerCase());
    }

    private static String extractErrorMessage(JSONObject feature) {
        String jobId = feature.getJSONObject("properties").getString("id");
        String gridId = feature.getJSONObject("properties").getString("grid_id");
        return "Failed to run job " + jobId + " at AOI 0" + gridId;
    }

    public static boolean haveAllJobsFinished(String digitalId, String scenarioId, IMonitor jobMonitor) {
        String items = getJobDbCollectionUrl(digitalId, scenarioId) + "/items";
        // Example catalog we could use for testing
        // items = "https://catalogue.weed.apex.esa.int/collections/test_bert-v100-jobdb";
        HttpResponse<JsonNode> response = Unirest.get(items).asJson();

        if (!response.isSuccess()) {
            throw new KlabRemoteException("Cannot access to JobDB " + items + ". Reason: " + response.getBody());
        }

        JSONArray features = response.getBody().getArray();
        
        boolean isAnyJobRunning = features.toList().stream().anyMatch(
                feature -> !hasFinished(((JSONObject)feature).getJSONObject("properties").getString("status")));
        
        if (isAnyJobRunning) {
            return false;
        }

        // Once every job has ended, we check for error messages
        features.toList().stream().filter(
                feature -> ((JSONObject)feature).getJSONObject("properties").getString("status").equalsIgnoreCase("error"))
                .forEach(error -> jobMonitor.error(extractErrorMessage((JSONObject) error)));

        return true;
    }
}
