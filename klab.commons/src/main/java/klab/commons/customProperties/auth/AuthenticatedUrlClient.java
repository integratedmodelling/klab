package klab.commons.customProperties.auth;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.rest.CustomPropertyRest;
import org.integratedmodelling.klab.rest.Group;

import klab.commons.customProperties.CustomPropertyKey;
import klab.commons.customProperties.auth.strategy.AuthStrategy;
import klab.commons.customProperties.auth.strategy.BasicAuthStrategy;
import klab.commons.customProperties.auth.strategy.BearerAuthStrategy;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

/**
 * Utility class for fetching URLs with automatic authentication.
 */
public class AuthenticatedUrlClient {

    private static final BasicAuthStrategy BASIC_STRATEGY = new BasicAuthStrategy();
    private static final BearerAuthStrategy BEARER_STRATEGY = new BearerAuthStrategy();

    private AuthenticatedUrlClient() {}

    /**
     * Selects the correct strategy based on AuthType.
     */
    private static AuthStrategy getStrategy(AuthType auth) {
        if (auth instanceof BasicAuth) return BASIC_STRATEGY;
        if (auth instanceof BearerAuth) return BEARER_STRATEGY;
        return null;
    }

    /**
     * Fetches a URL using Unirest and applies authentication automatically if present.
     *
     * @param url the URL to fetch
     * @param groups the groups containing authentication info
     * @param authKey the CustomPropertyKey containing auth data
     * @return the HTTP response
     * @throws Exception if the request fails
     */
    public static HttpResponse<String> fetchWithUnirest(String serviceUrl, String url, Set<Group> groups, CustomPropertyKey authKey) throws Exception {
        AuthType auth = findAuthForUrl(serviceUrl, groups, authKey);
        if (auth != null) {
            AuthStrategy strategy = getStrategy(auth);
            return strategy.apply(Unirest.get(url), auth).asString();
        } else {
            return Unirest.get(url).asString();
        }
    }

    /**
     * Opens an InputStream to a URL with authentication if present.
     *
     * @param url the URL to open
     * @param groups the groups containing authentication info
     * @param authKey the CustomPropertyKey containing auth data
     * @return InputStream from the URL
     * @throws Exception if opening the stream fails
     */
    public static InputStream openStream(String serviceUrl, URL url, Set<Group> groups, CustomPropertyKey authKey) throws Exception {
        AuthType auth = findAuthForUrl(serviceUrl, groups, authKey);
        URLConnection connection = url.openConnection();
        if (auth != null) {
            AuthStrategy strategy = getStrategy(auth);
            strategy.apply(url, connection, auth);
        }
        return connection.getInputStream();
    }

    /**
     * Searches through groups to find the authentication object for a given URL and key.
     *
     * @param url the URL to search for
     * @param groups the groups containing custom properties
     * @param authKey the key of the custom property
     * @return AuthType for the URL if found, otherwise null
     */
    private static AuthType findAuthForUrl(String url, Set<Group> groups, CustomPropertyKey authKey) {
        for (Group group : groups) {
            CustomPropertyRest property = group.customPropertyMap.get(authKey.getKey());
            if (property != null) {
                Object valueObject = property.getValueObject();
                if (valueObject instanceof Map<?, ?>) {
                    Map<String, AuthType> urlAuthMap = (Map<String, AuthType>) valueObject;
                    if (urlAuthMap.containsKey(url)) {
                        return urlAuthMap.get(url);
                    }
                }
            }
        }
        return null;
    }
}

