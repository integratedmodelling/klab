package klab.commons.customProperties.auth;

/**
 * Authentication with a header sent with every request, as the api keys:
 *
 * <pre>
 * "https://api.provider.com": { "type": "header", "headerName": "X-API-Key", "valueSecret": "/secret/..." }
 * </pre>
 */
public class HeaderAuth implements AuthType {

    private String headerName;
    private String valueSecret;

    public HeaderAuth() {}

    public HeaderAuth(String headerName, String valueSecret) {
        this.headerName = headerName;
        this.valueSecret = valueSecret;
    }

    @Override
    public String getType() {
        return "header";
    }

    public String getHeaderName() { return headerName; }
    public void setHeaderName(String headerName) { this.headerName = headerName; }

    public String getValueSecret() { return valueSecret; }
    public void setValueSecret(String valueSecret) { this.valueSecret = valueSecret; }
}
