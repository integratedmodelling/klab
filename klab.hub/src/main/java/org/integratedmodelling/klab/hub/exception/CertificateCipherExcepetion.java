package org.integratedmodelling.klab.hub.exception;

public class CertificateCipherExcepetion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8025155915743981738L;
	
	public CertificateCipherExcepetion(String name) {
		super(String.format("Certificate for %s could not be dechiphered", name));
	}

}
