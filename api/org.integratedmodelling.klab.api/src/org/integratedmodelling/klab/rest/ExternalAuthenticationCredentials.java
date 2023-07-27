package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExternalAuthenticationCredentials {

    /**
     * "Legend" for parameter names in the different auth methods
     */
    public static final Map<String, String[]> parameterKeys;

    static {

        parameterKeys = new HashMap<>();
        parameterKeys.put("basic", new String[]{"username", "password"});
        parameterKeys.put("oidc", new String[]{"url", "grant_type", "client_id", "client_secrets", "scope", "provider_id"});
        parameterKeys.put("s3", new String[]{"accessKey", "secretKey"});
    }

    /**
     * Credentials, depending on scheme
     * 
     * for basic: username and password
     * for oidc: Authentication URL, grant type, client ID, client secret, scope, provider
     * for s3: endpoint URL, access key, secret key
     */
    private List<String> credentials = new ArrayList<>();

    /**
     * one of basic, oidc, s3...
     */
    private String scheme = "basic";

    public List<String> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<String> credentials) {
        this.credentials = credentials;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

}
