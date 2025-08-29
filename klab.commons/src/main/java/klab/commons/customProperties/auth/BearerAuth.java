package klab.commons.customProperties.auth;

//public class BearerAuth implements AuthType {
//    private String token;
//
//    public BearerAuth() {}
//
//    public BearerAuth(String token) {
//        this.token = token;
//    }
//
//    @Override
//    public String getType() {
//        return "bearer";
//    }
//
//    public String getToken() { return token; }
//    public void setToken(String token) { this.token = token; }
//}
/**
 * Bearer token authentication type.
 */
public class BearerAuth implements AuthType {

    private String token;

    public BearerAuth() {}

    public BearerAuth(String token) {
        this.token = token;
    }

    @Override
    public String getType() {
        return "bearer";
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
