package klab.commons.customProperties.auth.strategy;

import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import klab.commons.customProperties.auth.AuthType;
import kong.unirest.HttpRequest;

/**
 * Strategy interface for applying authentication in different contexts.
 * <p>
 * This interface allows for a uniform way of handling authentication
 * (e.g., Basic, Bearer, etc.) across multiple communication channels:
 * <ul>
 *     <li>{@link kong.unirest.HttpRequest} for REST calls with Unirest</li>
 *     <li>{@link java.net.URLConnection} for direct Java URL connections</li>
 *     <li>{@link java.util.Map} of parameters for GeoTools WFSDataStoreFactory</li>
 * </ul>
 * By implementing this interface, concrete authentication strategies
 * encapsulate how credentials are injected depending on the context.
 */
public interface AuthStrategy {

    /**
     * Applies authentication headers or parameters to a Unirest HTTP request.
     *
     * @param request  the Unirest request to which authentication should be applied
     * @param authType the {@link AuthType} containing the authentication details
     * @return the modified request with authentication applied
     */
    HttpRequest<?> apply(HttpRequest<?> request, AuthType authType);

    /**
     * Applies authentication headers to a standard Java URLConnection.
     *
     * @param url        the target URL being accessed
     * @param connection the {@link URLConnection} used to connect to the URL
     * @param authType   the {@link AuthType} containing the authentication details
     * @throws Exception if authentication cannot be applied or if an encoding error occurs
     */
    void apply(URL url, URLConnection connection, AuthType authType) throws Exception;
    
    /**
     * Applies authentication credentials to a map of connection parameters,
     * typically used for GeoTools DataStore factories such as
     * {@code WFSDataStoreFactory}.
     * <p>
     * Implementations may override this method to inject username, password,
     * or tokens into the parameter map.
     *
     * @param params   the parameter map to populate with authentication details
     * @param authType the {@link AuthType} containing the authentication details
     */
    void apply(Map<String, Serializable> params, AuthType authType);
}
