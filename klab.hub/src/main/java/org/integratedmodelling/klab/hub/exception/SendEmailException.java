package org.integratedmodelling.klab.hub.exception;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
public class SendEmailException extends KlabException{

	private static final long serialVersionUID = -1756428237742871841L;

	public SendEmailException(String msg) {
        super(msg);
    }
    
    public SendEmailException(String msg, Throwable e) {
        super(msg, e);
    }
}
