package org.integratedmodelling.klab.components.geospace.routing;

public class ValhallaException extends Exception{


	public ValhallaException(String message) {
        super(message);
    }

    public ValhallaException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValhallaException(Throwable cause) {
        super(cause);
    }

    protected ValhallaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
