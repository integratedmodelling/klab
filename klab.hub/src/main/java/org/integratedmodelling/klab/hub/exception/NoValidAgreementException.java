package org.integratedmodelling.klab.hub.exception;

public class NoValidAgreementException extends RuntimeException {

    private static final long serialVersionUID = 874139306862298732L;

    public NoValidAgreementException(String user) {
        super(String.format("User %s has no valid agreement. Please, review the ", user));
    }

}
