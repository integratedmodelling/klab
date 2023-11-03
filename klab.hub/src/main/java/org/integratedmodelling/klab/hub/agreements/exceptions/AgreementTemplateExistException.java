package org.integratedmodelling.klab.hub.agreements.exceptions;

public class AgreementTemplateExistException extends RuntimeException{
    
    /**
     * 
     */
    private static final long serialVersionUID = 5038767660084417775L;

    public AgreementTemplateExistException(String msg) {
        this(msg, null);
    }

    public AgreementTemplateExistException(String msg, Throwable t) {
        super(msg, t);
    }

}
