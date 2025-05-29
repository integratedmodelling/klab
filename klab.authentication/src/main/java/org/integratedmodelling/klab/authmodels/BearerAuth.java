package org.integratedmodelling.klab.authmodels;

public class BearerAuth extends AuthType {
    private String token;

    public BearerAuth() {}
    public BearerAuth(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
