package klab.commons.customProperties.auth;

//public class BasicAuth implements AuthType {
//    private String username;
//    private String passwordSecret;
//
//    public BasicAuth() {}
//
//    public BasicAuth(String username, String passwordSecret) {
//        this.username = username;
//        this.passwordSecret = passwordSecret;
//    }
//
//    @Override
//    public String getType() {
//        return "basic";
//    }
//
//    public String getUsername() { return username; }
//    public void setUsername(String username) { this.username = username; }
//
//    public String getPasswordSecret() { return passwordSecret; }
//    public void setPasswordSecret(String passwordSecret) { this.passwordSecret = passwordSecret; }
//}
/**
 * Basic username/password authentication type.
 */
public class BasicAuth implements AuthType {

    private String username;
    private String passwordSecret;

    public BasicAuth() {}

    public BasicAuth(String username, String passwordSecret) {
        this.username = username;
        this.passwordSecret = passwordSecret;
    }

    @Override
    public String getType() {
        return "basic";
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordSecret() { return passwordSecret; }
    public void setPasswordSecret(String passwordSecret) { this.passwordSecret = passwordSecret; }
}

