package klab.commons.customProperties.auth;

/**
 * Authentication with a parameter added to the urls, for the services that want the key in the url:
 *
 * <pre>
 * "https://api.provider.com": { "type": "query", "parameterName": "api_key", "valueSecret": "/secret/..." }
 * </pre>
 */
public class QueryParamAuth implements AuthType {

    private String parameterName;
    private String valueSecret;

    public QueryParamAuth() {}

    public QueryParamAuth(String parameterName, String valueSecret) {
        this.parameterName = parameterName;
        this.valueSecret = valueSecret;
    }

    @Override
    public String getType() {
        return "query";
    }

    public String getParameterName() { return parameterName; }
    public void setParameterName(String parameterName) { this.parameterName = parameterName; }

    public String getValueSecret() { return valueSecret; }
    public void setValueSecret(String valueSecret) { this.valueSecret = valueSecret; }
}
