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
	
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(token);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RemoteUserAuthenticationRequest other = (RemoteUserAuthenticationRequest) obj;
		return Objects.equals(token, other.token);
	}

	@Override
	public String toString() {
		return "RemoteUserAuthenticationRequest [username=" + getUsername() + ", token="+getToken()+"]";
	}
}
