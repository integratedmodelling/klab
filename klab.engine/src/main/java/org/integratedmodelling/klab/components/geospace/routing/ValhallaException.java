package org.integratedmodelling.klab.components.geospace.routing;

import org.integratedmodelling.klab.exceptions.KlabException;

public class ValhallaException extends KlabException {

	private static final long serialVersionUID = -2808727016413136720L;

	public ValhallaException(String message) {
		super(message);
	}

	public ValhallaException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValhallaException(Throwable cause) {
		super(cause);
	}

}
