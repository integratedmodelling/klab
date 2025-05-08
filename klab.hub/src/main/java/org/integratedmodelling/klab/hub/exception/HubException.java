package org.integratedmodelling.klab.hub.exception;

import org.integratedmodelling.klab.exceptions.KlabException;


/**
 * Custom exception class for handling errors related to the Hub.
 * This exception includes details such as the request path, a custom error message,
 * an error identifier, and the corresponding HTTP status code.
 * 
 * Extends {@link KlabException}.
 */
public class HubException extends KlabException{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * A machine-readable error identifier.
     */
    private String error;
    /**
     * The human-readable message describing the error.
     */
    private String message;
    
    
    /**
     * Constructs a new {@code HubException} with the specified path, message, error identifier,
     * and HTTP status code.
     * 
     * @param message     a detailed message describing the error
     * @param error       a machine-readable error identifier 
     */
    public HubException(String error, String message) {
        super();
        this.message = message;
        this.error = error;
        
    }
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    

}
