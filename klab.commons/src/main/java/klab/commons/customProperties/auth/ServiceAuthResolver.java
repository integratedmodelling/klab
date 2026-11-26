package klab.commons.customProperties.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.rest.CustomPropertyRest;
import org.integratedmodelling.klab.rest.Group;

import klab.commons.customProperties.CustomPropertyKey;

/**
 * Finds the authentication of the services (WCS, WFS, STAC...) in the custom properties of the groups of a user.
 * <p>
 * The authentications are maps of url prefixes to {@link AuthType}, in the {@link CustomPropertyKey#URL_AUTH_KEYS}
 * property and in the legacy {@link CustomPropertyKey#GEOSERVER_KEYS}, {@link CustomPropertyKey#S3_KEYS} and
 * {@link CustomPropertyKey#AZURE_KEYS} ones. An entry applies to the urls that start with its key, at a
 * boundary of the path: <code>s3://bucket</code> applies to <code>s3://bucket/a/b.tif</code> but not to
 * <code>s3://bucket2/a.tif</code>. The longest key that applies is used.
 */
public class ServiceAuthResolver {

    /**
     * The properties read, the first ones taking precedence.
     */
    public static final CustomPropertyKey[] KEYS = {CustomPropertyKey.URL_AUTH_KEYS, CustomPropertyKey.GEOSERVER_KEYS,
            CustomPropertyKey.S3_KEYS, CustomPropertyKey.AZURE_KEYS};

    private ServiceAuthResolver() {
    }

    /**
     * All the authentication entries of the groups, the longest keys first.
     *
     * @param groups the groups of the user, can be null.
     * @return the url prefixes and their authentication.
     */
    public static Map<String, AuthType> entries(Collection<? extends Group> groups) {
        Map<String, AuthType> all = new LinkedHashMap<>();
        if (groups == null) {
            return all;
        }
        for (CustomPropertyKey key : KEYS) {
            for (Group group : groups) {
                if (group == null || group.getCustomPropertyMap() == null) {
                    continue;
                }
                CustomPropertyRest property = group.getCustomPropertyMap().get(key.getKey());
                if (property == null || !(property.getValueObject() instanceof Map<?, ?>)) {
                    continue;
                }
                for (Map.Entry<?, ?> entry : ((Map<?, ?>) property.getValueObject()).entrySet()) {
                    if (entry.getKey() instanceof String && entry.getValue() instanceof AuthType) {
                        String prefix = normalize((String) entry.getKey());
                        if (!prefix.isEmpty()) {
                            all.putIfAbsent(prefix, (AuthType) entry.getValue());
                        }
                    }
                }
            }
        }
        List<Map.Entry<String, AuthType>> sorted = new ArrayList<>(all.entrySet());
        sorted.sort(Comparator.comparingInt((Map.Entry<String, AuthType> e) -> e.getKey().length()).reversed());
        Map<String, AuthType> ret = new LinkedHashMap<>();
        for (Map.Entry<String, AuthType> entry : sorted) {
            ret.put(entry.getKey(), entry.getValue());
        }
        return ret;
    }

    /**
     * Find the authentication of a url.
     *
     * @param url the url.
     * @param groups the groups of the user, can be null.
     * @return the entry with the longest key that applies to the url, or null.
     */
    public static Map.Entry<String, AuthType> find(String url, Collection<? extends Group> groups) {
        if (url == null) {
            return null;
        }
        for (Map.Entry<String, AuthType> entry : entries(groups).entrySet()) {
            if (appliesTo(entry.getKey(), url)) {
                return entry;
            }
        }
        return null;
    }

    /**
     * @param prefix the key of an entry.
     * @param url a url.
     * @return <code>true</code> if the url starts with the prefix at a boundary of the path.
     */
    public static boolean appliesTo(String prefix, String url) {
        prefix = normalize(prefix);
        if (prefix.isEmpty() || url == null || !url.regionMatches(true, 0, prefix, 0, prefix.length())) {
            return false;
        }
        if (url.length() == prefix.length() || prefix.endsWith("/")) {
            return true;
        }
        char next = url.charAt(prefix.length());
        return next == '/' || next == '?' || next == '#';
    }

    private static String normalize(String prefix) {
        return prefix == null ? "" : prefix.trim();
    }
}
