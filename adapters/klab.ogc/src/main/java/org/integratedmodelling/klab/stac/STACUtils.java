package org.integratedmodelling.klab.stac;

import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.utils.DOIReader;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class STACUtils {

    public static String readDescription(JSONObject json) {
        return json.getString("description");
    }

    public static String readKeywords(JSONObject json) {
        if (!json.has("keywords")) {
            return null;
        }
        JSONArray keywords = json.getJSONArray("keywords");
        return keywords.isEmpty() ? null : keywords.toString().replace("\"", "");
    }

    final private static Set<String> DOI_KEYS_IN_STAC_JSON = Set.of("sci:doi", "assets.sci:doi", "summaries.sci:doi", "properties.sci:doi", "item_assets.sci:doi");
    public static String readDOI(JSONObject json) {
        Optional<String> doi = DOI_KEYS_IN_STAC_JSON.stream().filter(key -> json.has(key)).map(key -> json.getString(key)).findFirst();
        return doi.isPresent() ? doi.get() : null;
    }

    public static String readTitle(JSONObject json) {
        return json.getString("title");
    }

    public static String readDOIAuthors(String doi) {
        Set<String> authorsSet = DOIReader.readAuthors(doi);
        StringBuilder authors = new StringBuilder();
        authorsSet.forEach(a -> authors.append(a).append("\n"));
        return authors.toString().trim();
    }

    public static String[] extractCatalogAndCollection(String collectionURI) {
        return collectionURI.split("/collections/");
    }

    public static String getExtensionName(String identifier) {
        return StringUtils.substringBetween(identifier, "https://stac-extensions.github.io/", "/v");
    }

    public static Version getExtensionVersion(String identifier) {
        return Version.create(StringUtils.substringBetween(identifier, "/v", "/schema.json"));
    }

    public static JsonNode requestCollectionMetadata(String catalogUrl, String collectionId) {
        HttpResponse<JsonNode> response = Unirest.get(catalogUrl + "/collections/" + collectionId).asJson();
        if (!response.isSuccess() || response.getBody() == null) {
            throw new KlabResourceAccessException("Cannot access the collection at " + catalogUrl + "/collections/" + collectionId);
        }
        return response.getBody();
    }

    public static JsonNode requestItemMetadata(String catalogUrl, String collectionId, String item) {
        HttpResponse<JsonNode> response = Unirest.get(catalogUrl + "/collections/" + collectionId).asJson();
        if (!response.isSuccess() || response.getBody() == null) {
            throw new KlabResourceAccessException("Cannot access the item at " + catalogUrl + "/collections/" + collectionId + "/items/" + item);
        }
        return response.getBody();
    }

    public static String readLicense(JSONObject collection) {
        if (!collection.has("links")) {
            return null;
        }
        JSONArray links = collection.getJSONArray("links");
        for (int i = 0; i < links.length(); i++) {
            JSONObject link = links.getJSONObject(i);
            if (!link.has("rel") || !link.getString("rel").equals("license")) {
                continue;
            }
            // A link to the license is preferred
            if (link.has("href")) {
                return link.getString("href");
            }
            if (link.has("title")) {
                link.getString("title");
            }
        }
        return null;
    }

    public static Type inferValueType(String key) {
        if (StringUtils.isNumeric(key)) {
            return Type.NUMBER;
        } else if ("true".equalsIgnoreCase(key) || "false".equalsIgnoreCase(key)) {
            return Type.BOOLEAN;
        }
        // As we are reading a JSON, text is our safest default option
        return Type.TEXT;
    }

}
