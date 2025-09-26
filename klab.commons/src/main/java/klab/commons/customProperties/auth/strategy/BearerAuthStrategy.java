package klab.commons.customProperties.auth.strategy;

import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import klab.commons.customProperties.auth.AuthType;
import klab.commons.customProperties.auth.BearerAuth;
import kong.unirest.HttpRequest;

/**
 * Authentication strategy for Bearer token authentication.
 * <p>
 * This strategy applies authentication by injecting an
 * {@code Authorization: Bearer <token>} header into the request.
 * It is commonly used for OAuth2 or API token-based systems.
 * <p>
 * Supported contexts:
 * <ul>
 *     <li>{@link kong.unirest.HttpRequest} → adds {@code Authorization: Bearer ...}</li>
 *     <li>{@link java.net.URLConnection} → sets the same header</li>
 *     <li>{@link java.util.Map} → inserts a {@code BEARER_TOKEN} key for compatibility</li>
 * </ul>
 */
public class BearerAuthStrategy implements AuthStrategy {

    /**
     * Applies Bearer token authentication to a Unirest HTTP request.
     *
     * @param request  the Unirest request
     * @param authType the authentication details (must be {@link BearerAuth})
     * @return the request with Bearer token authentication applied
     */
    @Override
    public HttpRequest<?> apply(HttpRequest<?> request, AuthType authType) {
        BearerAuth bearer = (BearerAuth) authType;
        return request.header("Authorization", "Bearer " + bearer.getToken());
    }

    /**
     * Applies Bearer token authentication to a URLConnection by setting
     * the {@code Authorization} header with the Bearer token.
     *
     * @param url        the target URL
     * @param connection the URL connection
     * @param authType   the authentication details (must be {@link BearerAuth})
     * @throws Exception if the header cannot be applied
     */
    @Override
    public void apply(URL url, URLConnection connection, AuthType authType) throws Exception {
        BearerAuth bearer = (BearerAuth) authType;
        connection.setRequestProperty("Authorization", "Bearer " + bearer.getToken());
    }
    
    /**
     * Applies Bearer token authentication to a GeoTools connection parameter map.
     * Not exists
     */
    @Override
    public void apply(Map<String, Serializable> params, AuthType authType) {
    }
}
