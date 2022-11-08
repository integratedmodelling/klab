package org.integratedmodelling.opencpu.temp;

public class OCPUException extends Exception {
    public OCPUException(String message) {
        super(message);
    }

    public OCPUException(String message, Throwable cause) {
        super(message, cause);
    }

    public OCPUException(Throwable cause) {
        super(cause);
    }

    protected OCPUException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
