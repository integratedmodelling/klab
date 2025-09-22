package klab.commons.customProperties.auth.strategy;

import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Map;

import klab.commons.customProperties.auth.AuthType;
import klab.commons.customProperties.auth.BasicAuth;
import kong.unirest.HttpRequest;

/**
 * Authentication strategy for HTTP Basic Authentication.
 * <p>
 * This strategy applies authentication credentials by sending
 * a Base64-encoded {@code username:password} pair in the
 * "Authorization" header or by injecting them into connection
 * parameters.
 * <p>
 * Supported contexts:
 * <ul>
 *     <li>{@link kong.unirest.HttpRequest} → applies {@code .basicAuth()}</li>
 *     <li>{@link java.net.URLConnection} → sets {@code Authorization: Basic ...}</li>
 *     <li>{@link java.util.Map} → inserts {@code USERNAME} and {@code PASSWORD} keys</li>
 * </ul>
 */
public class BasicAuthStrategy implements AuthStrategy {

    /**
     * Applies Basic Authentication to a Unirest HTTP request.
     *
     * @param request  the Unirest request
     * @param authType the authentication details (must be {@link BasicAuth})
     * @return the request with Basic Authentication applied
     */
    @Override
    public HttpRequest< ? > apply(HttpRequest< ? > request, AuthType authType) {
        BasicAuth basic = (BasicAuth) authType;
        return request.basicAuth(basic.getUsername(), basic.getPasswordSecret());
    }

    /**
     * Applies Basic Authentication to a URLConnection by setting
     * the {@code Authorization} header with Base64-encoded credentials.
     *
     * @param url        the target URL
     * @param connection the URL connection
     * @param authType   the authentication details (must be {@link BasicAuth})
     * @throws Exception if encoding or header application fails
     */
    @Override
    public void apply(URL url, URLConnection connection, AuthType authType) throws Exception {
        BasicAuth basic = (BasicAuth) authType;
        String encoded = Base64.getEncoder().encodeToString((basic.getUsername() + ":" + basic.getPasswordSecret()).getBytes());
        connection.setRequestProperty("Authorization", "Basic " + encoded);
    }

    /**
     * Applies Basic Authentication to a GeoTools connection parameter map.
     * <p>
     * This sets:
     * <ul>
     *     <li>{@code USERNAME} → the BasicAuth username</li>
     *     <li>{@code PASSWORD} → the BasicAuth password</li>
     * </ul>
     *
     * @param params   the parameter map used by GeoTools DataStores
     * @param authType the authentication details (must be {@link BasicAuth})
     */
    @Override
    public void apply(Map<String, Serializable> params, AuthType authType) {
        BasicAuth auth = (BasicAuth) authType;
        params.put("WFSDataStoreFactory:USERNAME", auth.getUsername());
        params.put("WFSDataStoreFactory:PASSWORD", auth.getPasswordSecret());
    }
}