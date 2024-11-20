/**
 * 
 */
package org.integratedmodelling.klab.rest;

import java.util.Objects;

/**
 * @author Enrico Girotto
 *
 */
public class RemoteUserAuthenticationRequest extends UserAuthenticationRequest {

    private String token;

	@Override
	public String toString() {
		return "RemoteUserAuthenticationRequest [username=" + getUsername() + ", token="+getToken()+"]";
	}
}
