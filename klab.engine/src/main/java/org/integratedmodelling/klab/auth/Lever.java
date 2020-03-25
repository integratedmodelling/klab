package org.integratedmodelling.klab.auth;

import java.util.Collection;
import java.util.Date;

import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.ILeverIdentity;
import org.integratedmodelling.klab.api.auth.IPartnerIdentity;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.runtime.rest.IClient;

public class Lever implements ILeverIdentity{
	
	private String name;
	private boolean online;
	private IPartnerIdentity parent;
	private String authenticatingHub;
	private String token;
	private Date bootTime = new Date();

	public Lever(String name, IPartnerIdentity rootIdentity) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Date getBootTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getUrls() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOnline() {
		return online;
	}

	@Override
	public IClient getClient() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMonitor getMonitor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIdentity getParentIdentity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean is(Type type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T extends IIdentity> T getParentIdentity(Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setOnline(boolean b) {
		this.online = b;
	}

}
