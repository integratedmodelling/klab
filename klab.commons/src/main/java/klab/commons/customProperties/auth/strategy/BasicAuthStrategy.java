package klab.commons.customProperties.auth.strategy;

import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import klab.commons.customProperties.auth.AuthType;
import klab.commons.customProperties.auth.BasicAuth;
import kong.unirest.HttpRequest;

/**
 * Strategy for applying Basic Authentication.
 */
public class BasicAuthStrategy implements AuthStrategy {

    @Override
    public HttpRequest<?> apply(HttpRequest<?> request, AuthType authType) {
        BasicAuth basic = (BasicAuth) authType;
        return request.basicAuth(basic.getUsername(), basic.getPasswordSecret());
    }

    @Override
    public void apply(URL url, URLConnection connection, AuthType authType) throws Exception {
        BasicAuth basic = (BasicAuth) authType;
        String encoded = Base64.getEncoder()
                .encodeToString((basic.getUsername() + ":" + basic.getPasswordSecret()).getBytes());
        connection.setRequestProperty("Authorization", "Basic " + encoded);
    }
}