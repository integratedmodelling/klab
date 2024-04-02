package org.integratedmodelling.klab.hub.agreements.exceptions;

public class AgreementTemplateDoesNotExistException extends RuntimeException{
    
    /**
     * 
     */
    private static final long serialVersionUID = 5038767660084417775L;

    public AgreementTemplateDoesNotExistException(String msg) {
        this(msg, null);
    }

    public AgreementTemplateDoesNotExistException(String msg, Throwable t) {
        super(msg, t);
    }

}
