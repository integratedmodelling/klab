package org.integratedmodelling.klab.auth;

import java.util.Base64;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.rest.ExternalAuthenticationCredentials;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.MultipartBody;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

/**
 * This is returned after authentication from a service using ExternalCredentials. Handles any
 * refresh automatically according to the scheme.
 * 
 * @author Ferd
 *
 */
public class Authorization {

    private ExternalAuthenticationCredentials credentials;
    private String token;
    private long expiry = -1;
    private String prefix;

    public Authorization(ExternalAuthenticationCredentials credentials, String prefix) {
        this(credentials);
        this.prefix = prefix;
    }

    public Authorization(ExternalAuthenticationCredentials credentials) {
        
        this.credentials = credentials;

        if ("basic".equals(credentials.getScheme())) {
            byte[] encodedBytes = Base64.getEncoder()
                    .encode((credentials.getCredentials().get(0) + ":" + credentials.getCredentials().get(1)).getBytes());
            this.token = "Basic " + new String(encodedBytes);
        } else if ("oauth2".equals(credentials.getScheme())) {
            refreshToken();
        }
    }

    private void refreshToken() {
        /*
         * authenticate and get the first token. Credentials should contain: 0. Auth endpoint 1.
         * grant type 2. client ID 3. client secret 4. scope
         */
        MultipartBody query = Unirest.post(credentials.getCredentials().get(0))
                .field("grant_type", credentials.getCredentials().get(1)).field("client_id", credentials.getCredentials().get(2))
                .field("client_secret", credentials.getCredentials().get(3)).field("scope", credentials.getCredentials().get(4));

        if (this.token != null) {
            query = query.header("Authorization:", token);
        }

        this.expiry = System.currentTimeMillis();
        HttpResponse<JsonNode> result = query.asJson();
        if (result.isSuccess()) {
            JSONObject response = result.getBody().getObject();
            long duration = -1;
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
            this.expiry += (duration * 1000l);
        }
    }

    /**
     * Return the authorization token for the Authorization: header.
     * 
     * @return
     */
    public String getAuthorization() {

        if (token == null) {
            throw new KlabIOException("Authorization failed");
        }

        if ("oauth2".equals(credentials.getScheme())) {
            if (this.expiry <= System.currentTimeMillis()) {
                refreshToken();
            }
            return prefix == null ? token : (prefix + token);
        }

        // repeat
        if (token == null) {
            throw new KlabIOException("Authorization failed");
        }

        return token;
    }

    public static void main(String[] args) {
        ExternalAuthenticationCredentials crds = Authentication.INSTANCE.getCredentials("https://openeo.vito.be");
        if (crds != null) {
            Authorization authorization = new Authorization(crds, "oidc/terrascope/");
            System.out.println(authorization.getAuthorization());
        }
    }

}
