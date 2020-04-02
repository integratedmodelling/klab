package org.integratedmodelling.klab.rest;

public class LeverAuthenticationRequest extends AuthenticationRequest{
	
	private String ip;
	private String cycle;
	
	public LeverAuthenticationRequest() {
		super();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	

}
