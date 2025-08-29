package klab.commons.customProperties.auth.strategy;

import java.net.URL;
import java.net.URLConnection;

import klab.commons.customProperties.auth.AuthType;
import kong.unirest.HttpRequest;

/**
 * Strategy interface for applying authentication.
 * Can be implemented for BasicAuth, BearerAuth, etc.
 */
public interface AuthStrategy {

    /**
     * Applies authentication to a Unirest HTTP request.
     *
     * @param request the Unirest request
     * @param authType the AuthType to apply
     * @return the request with authentication applied
     */
    HttpRequest<?> apply(HttpRequest<?> request, AuthType authType);

    /**
     * Applies authentication to a URLConnection.
     *
     * @param url the URL being accessed
     * @param connection the URLConnection
     * @param authType the AuthType to apply
     * @throws Exception if applying auth fails
     */
    void apply(URL url, URLConnection connection, AuthType authType) throws Exception;
}
