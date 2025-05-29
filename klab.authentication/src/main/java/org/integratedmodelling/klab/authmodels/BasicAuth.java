package org.integratedmodelling.klab.authmodels;

public class BasicAuth extends AuthType {
    private String username;
    private String passwordSecret;

    public BasicAuth() {}
    public BasicAuth(String username, String passwordSecret) {
        this.username = username;
        this.passwordSecret = passwordSecret;
    }

    public String getUsername() { return username; }
    public String getPasswordSecret() { return passwordSecret; }

    public void setUsername(String username) { this.username = username; }
    public void setPasswordSecret(String passwordSecret) { this.passwordSecret = passwordSecret; }
}
