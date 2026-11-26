package org.integratedmodelling.klab.stac;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.geotools.data.geojson.GeoJSONReader;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.utils.DOIReader;
import org.geotools.api.feature.simple.SimpleFeature;

import com.fasterxml.jackson.core.JsonParseException;

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
        List<String> keywords = json.getJSONArray("keywords").toList();
        return keywords.isEmpty() ? null : keywords.stream().collect(Collectors.joining(","));
    }

    final private static Set<String> DOI_KEYS_IN_STAC_JSON = Set.of("sci:doi", "assets.sci:doi", "summaries.sci:doi",
            "properties.sci:doi", "item_assets.sci:doi");
    public static String readDOI(JSONObject json) {
        Optional<String> doi = DOI_KEYS_IN_STAC_JSON.stream().filter(key -> json.has(key)).map(key -> json.getString(key))
                .findFirst();
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

    /**
     * Validates of the artifact contains a link to an element of type ref
     * @param data of a collection, catalog or item
     * @param rel
     * @return true if rel exists
     */
    public static boolean containsLinkTo(JSONObject data, String rel) {
        return data.getJSONArray("links").toList().stream()
                .anyMatch(link -> ((JSONObject) link).getString("rel").equalsIgnoreCase(rel));
    }

    public static Optional<String> getLinkTo(JSONObject data, String rel) {
        return data.getJSONArray("links").toList().stream()
                .filter(link -> ((JSONObject) link).getString("rel").equalsIgnoreCase(rel))
                .map(link -> ((JSONObject) link).getString("href")).findFirst();
    }

    /**
     * Read a STAC document, with the authentication of the user if needed (see {@link STACAccess}).
     *
     * @param collectionUrl the address, also s3://.
     * @param type the expected type (collection, catalog, feature).
     * @return the document.
     */
    public static JSONObject requestMetadata(String collectionUrl, String type) {
        JSONObject data;
        try {
            data = STACAccess.getJson(collectionUrl);
        } catch (KlabResourceAccessException e) {
            throw new KlabResourceAccessException("Cannot access the " + type + " at " + collectionUrl + ": " + e.getMessage());
        } catch (Exception e) {
            throw new KlabResourceAccessException("Data at " + collectionUrl + " is not a valid STAC " + type);
        }
        if (!data.has("type") || !data.getString("type").equalsIgnoreCase(type)) {
            throw new KlabResourceAccessException("Data at " + collectionUrl + " is not a valid STAC " + type);
        }
        return data;
    }

    public static String readLicense(JSONObject collection) {
        if (!collection.has("links")) {
            return null;
        }
        JSONArray links = collection.getJSONArray("links");
        for(int i = 0; i < links.length(); i++) {
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

    /**
     * Reads the collection data and extracts the link pointing to the root element (the catalog).
     * @param collectionData
     * @return url of the catalog
     */
    public static String getCatalogUrl(String collectionUrl, String collectionId, JSONObject collectionData) {
        // The URL of the catalog is the root
        if (!collectionData.has("links")) {
            throw new KlabResourceAccessException(
                    "STAC collection is missing links. It is not fully complaiant and cannot be accessed by the adapter.");
        }
        JSONArray links = collectionData.getJSONArray("links");
        Optional<JSONObject> rootLink = links.toList().stream()
                .filter(link -> ((JSONObject) link).getString("rel").equalsIgnoreCase("root")).findFirst();
        if (rootLink.isEmpty()) {
            throw new KlabResourceAccessException("STAC collection is missing a relationship to the root catalog");
        }
        String href = rootLink.get().getString("href");
        return getUrlOfItem(collectionUrl, collectionId, href);
    }

    /**
     * The address of the collection of a resource: the <code>collection</code> parameter or, for the resources
     * imported with the old schema (<code>catalogUrl</code> and <code>collectionId</code>, search APIs only), the
     * collection endpoint of the catalog.
     *
     * @param parameters the parameters of the resource.
     * @return the address, null if the parameters have neither schema.
     */
    public static String getCollectionUrl(IParameters<String> parameters) {
        String collectionUrl = parameters.get("collection", String.class);
        if (collectionUrl != null) {
            return collectionUrl;
        }
        String catalogUrl = parameters.get("catalogUrl", String.class);
        String collectionId = parameters.get("collectionId", String.class);
        if (catalogUrl == null || collectionId == null) {
            return null;
        }
        while (catalogUrl.endsWith("/")) {
            catalogUrl = catalogUrl.substring(0, catalogUrl.length() - 1);
        }
        return catalogUrl + "/collections/" + collectionId;
    }

    /**
     * Resolve a link of a collection against the address of the collection document.
     *
     * @param collectionUrl the address of the collection document.
     * @param collectionId unused, kept for compatibility.
     * @param href the link, absolute or relative (./, ../, plain name).
     * @return the absolute address.
     */
    public static String getUrlOfItem(String collectionUrl, String collectionId, String href) {
        return resolveHref(collectionUrl, href);
    }

    /**
     * Resolve a link against the address of the document holding it, as by the STAC spec (RFC 3986).
     *
     * @param baseUrl the address of the document.
     * @param href the link, absolute or relative.
     * @return the absolute address; the href unchanged if it cannot be resolved.
     */
    public static String resolveHref(String baseUrl, String href) {
        if (href == null || baseUrl == null) {
            return href;
        }
        try {
            URI hrefUri = new URI(href);
            if (hrefUri.isAbsolute()) {
                return href;
            }
            return new URI(baseUrl).resolve(hrefUri).toString();
        } catch (URISyntaxException e) {
            return href;
        }
    }

    public static SimpleFeature getItemAsFeature(String itemUrl) throws JsonParseException, IOException {
        return GeoJSONReader.parseFeature(STACAccess.readString(STACAccess.get(), itemUrl));
    }
}
