package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

public class AuthorityQueryResponse {

    private List<AuthorityIdentity> matches = new ArrayList<>();
    private String error;

    public List<AuthorityIdentity> getMatches() {
        return matches;
    }
    public void setMatches(List<AuthorityIdentity> matches) {
        this.matches = matches;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }

}
