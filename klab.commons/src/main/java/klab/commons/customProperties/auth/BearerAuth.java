package klab.commons.customProperties.auth;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Bearer token authentication type.
 * <p>
 * The token is configured as a Vault path in <code>tokenSecret</code>, which the Hub replaces with the token
 * before sending the groups to the engine. The old <code>token</code> field is still accepted.
 */
public class BearerAuth implements AuthType {

    @JsonAlias("token")
    private String tokenSecret;

    public BearerAuth() {}

    public BearerAuth(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    @Override
    public String getType() {
        return "bearer";
    }

    public String getTokenSecret() { return tokenSecret; }
    public void setTokenSecret(String tokenSecret) { this.tokenSecret = tokenSecret; }

    /**
     * @return the token (resolved from Vault by the Hub).
     */
    @JsonIgnore
    public String getToken() { return tokenSecret; }
}
