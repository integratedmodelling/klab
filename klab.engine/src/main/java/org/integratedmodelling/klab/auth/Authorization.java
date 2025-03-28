package org.integratedmodelling.klab.auth;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.rest.ExternalAuthenticationCredentials;
import org.integratedmodelling.klab.utils.Pair;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.MultipartBody;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

/**
 * This is returned after authentication from a service using ExternalCredentials. Handles any
 * refresh automatically according to the scheme.
 * 
 * {@link #getAuthorization()} returns the token complete with token type ("Bearer", "Basic") and
 * any prefix passed to the constructor, ready to be used as an HTTP Authorization header.
 * 
 * @author Ferd
 *
 */
public class Authorization {

    private ExternalAuthenticationCredentials credentials;
    private String token;
    private long expiry = -1;
    private String prefix;
    private String tokenType;

    /**
     * Create a new authorization. {@link #isOnline()} should be called after creation.
     * 
     * @param credentials
     */
    public Authorization(ExternalAuthenticationCredentials credentials) {
        this(credentials, null);
    }

    /**
     * Create a new authorization. {@link #isOnline()} should be called after creation.
     * 
     * @param credentials
     * @param endpoint
     */
    public Authorization(ExternalAuthenticationCredentials credentials, String endpoint) {
        if (credentials == null) {
            throw new KlabIllegalArgumentException("attempted authorization with null credentials");
        }

        this.credentials = credentials;

        if ("basic".equals(credentials.getScheme())) {
            this.tokenType = "Basic";
            byte[] encodedBytes = Base64.getEncoder()
                    .encode((credentials.getCredentials().get(0) + ":" + credentials.getCredentials().get(1)).getBytes());
            this.token = new String(encodedBytes);
        } else if ("oidc".equals(credentials.getScheme())) {
            if (endpoint == null) {
                throw new KlabIllegalArgumentException("Attempted to start an OIDC authoritation workflow without an endpoint");
            }
            refreshToken();
        }
    }

    /**
     * Check if the last authentication attempt went well.
     * 
     * @return
     */
    public boolean isOnline() {
        return token != null;
    }

    private Pair<String, String> parseProvider() {
        HttpResponse<JsonNode> response = Unirest.get(credentials.getURL() + "/credentials/oidc").asJson();
        if (!response.isSuccess()) {
            throw new KlabAuthorizationException("Cannot access " + credentials.getURL() + " for OIDC authentication");
        }
        List<JSONObject> providers = response.getBody().getObject().getJSONArray("providers").toList();
        JSONObject provider = providers.stream().filter(prov -> prov.getString("id").equals(credentials.getCredentials().get(3))).findFirst()
                .orElseThrow(() -> new KlabAuthorizationException("No known provider '" + credentials.getCredentials().get(3) + "' at " + credentials.getURL()));
        List<String> scopes = provider.getJSONArray("scopes").toList();
        String scope = scopes.stream().collect(Collectors.joining(" "));
        return new Pair<>(provider.getString("issuer"), scope);
    }

    private String parseIssuer(String issuerUrl) {
        HttpResponse<JsonNode> response = Unirest.get(issuerUrl + "/.well-known/openid-configuration").asJson();
        if (!response.isSuccess()) {
            throw new KlabAuthorizationException("Cannot access " + issuerUrl + " for OIDC authentication");
        }
        return response.getBody().getObject().getString("token_endpoint");
    }

    /**
     * OIDC-style token
     */
    private void refreshToken() {
        /*
         * authenticate and get the first token. Credentials should contain:
         * 0. grant type 1. client ID 2. client secret 3. provider
         */
        Pair<String, String> issuerAndScope = parseProvider();
        String issuer = issuerAndScope.getFirst();
        String scope = issuerAndScope.getSecond();
        String tokenServiceUrl = parseIssuer(issuer);

        MultipartBody query = Unirest.post(tokenServiceUrl)
                .field("grant_type", credentials.getCredentials().get(0))
                .field("client_id", credentials.getCredentials().get(1))
                .field("client_secret", credentials.getCredentials().get(2))
                .field("scope", scope);

        if (this.token != null) {
            query = query.header("Authorization:",
                    (tokenType == null ? "" : (tokenType + " ")) + (prefix == null ? "" : prefix) + token);
        }

        this.expiry = System.currentTimeMillis();
        HttpResponse<JsonNode> result = query.asJson();
        if (result.isSuccess()) {
            JSONObject response = result.getBody().getObject();
            long duration = -1;
            if (response.has("token_type")) {
                this.tokenType = response.getString("token_type");
            }
            if (response.has("refresh_token")) {
                this.token = response.getString("refresh_token");
                this.expiry = this.expiry + response.getLong("");
                duration = response.has("refresh_token_expires_in") ? response.getLong("refresh_token_expires_in") : -1;
            } else {
                this.token = response.has("access_token") ? response.getString("access_token") : null;
            }
            
            if (token != null && duration < 0) {
                duration = response.has("expires_in") ? response.getLong("expires_in") : 0;
            }
            this.prefix = "oidc/" + credentials.getCredentials().get(3) + "/";
            this.expiry += (duration * 1000l);
        }
    }

    /**
     * The raw authorization token with no auth method or prefix. May be null if {@link #isOnline()}
     * returns false.
     * 
     * @return
     */
    public String getToken() {
        return this.token;
    }

    /**
     * Return the authorization token for the Authorization: header. Includes the auth method (e.g.
     * Basic, Bearer) and any prefix passed at construction.
     * 
     * @return
     */
    public String getAuthorization() {

        if (token == null) {
            throw new KlabIOException("Authorization failed");
        }

        if ("oidc".equals(credentials.getScheme())) {
            if (this.expiry <= System.currentTimeMillis()) {
                refreshToken();
            }
            // repeat
            if (token == null) {
                throw new KlabIOException("Authorization failed");
            }
        }

        return (tokenType == null ? "" : (tokenType + " ")) + (prefix == null ? token : (prefix + token));
    }

    public static void main(String[] args) throws InterruptedException {
        String endpoint = "https://openeo.vito.be/openeo/1.1.0";
        ExternalAuthenticationCredentials crds = Authentication.INSTANCE.getCredentials(endpoint);
        if (crds != null) {
            Authorization authorization = new Authorization(crds, endpoint);
            System.out.println(authorization.getAuthorization());
            System.out.println("Sleeping 300 seconds: don't change the channel");
            Thread.sleep(300000l);
            System.out.println(authorization.getAuthorization());
        } else {
            throw new KlabAuthorizationException("no stored credentials for https://openeo.vito.be");
        }
    }

}
