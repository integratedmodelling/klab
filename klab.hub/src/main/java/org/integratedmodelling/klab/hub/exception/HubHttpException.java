package org.integratedmodelling.klab.hub.exception;

import org.springframework.http.HttpStatus;


/**
 * Subclass of HubException specifically for HTTP-related errors.
 * Inherits from {@link HubException}.
 */
public class HubHttpException extends HubException{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * The HTTP status code associated with the error.
     */
    private HttpStatus httpStatus;

    /**
     * Constructs a new {@code HubHttpException} with the specified path, message,
     * error identifier, and HTTP status code.
     *
     * @param message     a detailed message describing the error
     * @param error       a machine-readable error identifier
     * @param httpStatus  the HTTP status code to return with the error
     */
    
    public HubHttpException(String error, String message, HttpStatus httpStatus) {
        super(error, message); 
        this.httpStatus = httpStatus;;
    }
    
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
