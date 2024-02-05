package org.integratedmodelling.klab.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

/**
 * A utility class to obtain the data of the DOI via multiple APIs.
 * DataCite API: https://support.datacite.org/docs/api-get-doi
 * Crossref API: https://api.crossref.org/swagger-ui/index.html
 *
 */
public class DOIReader {
    private static String DATACITE_GET_DOI = "https://api.datacite.org/dois/";
    private static String CROSSREF_GET_DOI = "https://api.crossref.org/works/";

    public static HttpResponse<JsonNode> getDOIInformationFromDatacite(String doi) {
        return Unirest.get(DATACITE_GET_DOI + doi).asJson();
    }

    public static HttpResponse<JsonNode> getDOIInformationFromCrossref(String doi) {
        return Unirest.get(CROSSREF_GET_DOI + doi).asJson();
    }

    public static Set<String> readAuthorsDatacite(String doi) {
        HttpResponse<JsonNode> response = getDOIInformationFromDatacite(doi);
        if (!response.isSuccess()) {
            return Collections.emptySet();
        }
        JsonNode json = response.getBody();
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

    public static Set<String> readAuthorsCrossref(String doi) {
        HttpResponse<JsonNode> response = getDOIInformationFromCrossref(doi);
        if (!response.isSuccess()) {
            return Collections.emptySet();
        }
        JsonNode json = response.getBody();
        JSONArray authorsArray = json.getObject().getJSONObject("message").getJSONArray("author");
        Set<String> authors = new HashSet<>();
        for (Object author : authorsArray) {
            if (!((JSONObject) author).has("given") || !((JSONObject) author).has("family")) {
                continue;
            }
            String name = ((JSONObject) author).getString("family").concat(",")
                    .concat(((JSONObject) author).getString("given"));
            authors.add(name);
        }
        return authors;
    }

    public static Set<String> readAuthors(String doi) {
        Set<String> authors = readAuthorsDatacite(doi);
        if (!authors.isEmpty()) {
            return authors;
        }
        return readAuthorsCrossref(doi);
    }
}
