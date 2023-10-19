package org.integratedmodelling.klab.stac;

import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.utils.DOIReader;
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

}
