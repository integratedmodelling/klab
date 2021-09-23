package org.integratedmodelling.klab.rest;

public class AuthorityQueryRequest {

    private String authorityId;
    private String authorityCatalog;
    private String queryString;

    public AuthorityQueryRequest() {

    }

    public AuthorityQueryRequest(String authorityId, String authorityCatalog, String queryString) {
        this.authorityId = authorityId;
        this.authorityCatalog = authorityCatalog;
        this.queryString = queryString;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthorityCatalog() {
        return authorityCatalog;
    }

    public void setAuthorityCatalog(String authorityCatalog) {
        this.authorityCatalog = authorityCatalog;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

}
