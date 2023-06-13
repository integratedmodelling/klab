package org.integratedmodelling.klab;

import java.util.HashSet;
import java.util.Set;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

/**
 * A utility class to obtain the data of the DOI via the DataCite API.
 * https://support.datacite.org/docs/api-get-doi
 *
 */
public class DOIReader {
    private static String DATACITE_GET_DOI = "https://api.datacite.org/dois/";

    public static HttpResponse<JsonNode> getDOIInformation(String doi) {
        return Unirest.get(DATACITE_GET_DOI + doi).asJson();
    }

    public static Set<String> readAuthors(JsonNode json) {
        JSONArray authorsArray = json.getObject().getJSONObject("data").getJSONObject("attributes").getJSONArray("creators");
        Set<String> authors = new HashSet<>();
        for (Object author : authorsArray) {
            if (!((JSONObject) author).has("name")) {
                continue;
            }
            authors.add(((JSONObject) author).getString("name"));
        }
        return authors;
    }
}
