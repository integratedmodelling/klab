package org.integratedmodelling.controlcenter.auth;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.integratedmodelling.controlcenter.ControlCenter;
import org.integratedmodelling.controlcenter.api.IAuthentication;

public class Authentication implements IAuthentication {

	Status status = Status.ANONYMOUS;
	String authenticationEndpoint;
	String username = "anonymous";
	String email = "";
	Date expiration;
	List<Group> groups = new ArrayList<>();
	
	private KlabCertificate certificate;

	public Authentication() {
		
		File file = ControlCenter.INSTANCE.getSettings().getCertificateFile();
		if (file.isFile() && file.canRead()) {
			this.certificate = new KlabCertificate(file);
		}
	}	
	
	@Override
	public String getAuthenticationEndpoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getExpiration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> groups() {
		return groups;
	}

	@Override
	public Status getStatus() {
		return status;
	}

}
