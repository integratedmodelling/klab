package klab.commons.customProperties.auth.strategy;

import java.net.URL;
import java.net.URLConnection;

import klab.commons.customProperties.auth.AuthType;
import klab.commons.customProperties.auth.BearerAuth;
import kong.unirest.HttpRequest;

/**
 * Strategy for applying Bearer Token Authentication.
 */
public class BearerAuthStrategy implements AuthStrategy {

    @Override
    public HttpRequest<?> apply(HttpRequest<?> request, AuthType authType) {
        BearerAuth bearer = (BearerAuth) authType;
        return request.header("Authorization", "Bearer " + bearer.getToken());
    }

    @Override
    public void apply(URL url, URLConnection connection, AuthType authType) throws Exception {
        BearerAuth bearer = (BearerAuth) authType;
        connection.setRequestProperty("Authorization", "Bearer " + bearer.getToken());
    }
}
