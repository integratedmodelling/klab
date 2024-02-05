package org.integratedmodelling.klab.hub.exception;

public class NoValidAgreementException extends RuntimeException {

    private static final long serialVersionUID = 874139306862298732L;

    public NoValidAgreementException(String user) {
        super(String.format("User %s has no valid agreement. Please, review the ", user));
    }
    
    public NoValidAgreementException(String user, String message) {
        super(String.format("User %1$s has no valid agreement: %2$s", user, message));
    }

}
