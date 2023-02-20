package org.integratedmodelling.klab.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.ILeverIdentity;
import org.integratedmodelling.klab.api.auth.IPartnerIdentity;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.runtime.rest.IClient;
import org.integratedmodelling.klab.utils.Parameters;

public class Lever implements ILeverIdentity{
	
	private String name;
	private boolean online;
	private IPartnerIdentity parent;
	private String authenticatingHub;
	private List<String> urls = new ArrayList<>();
	private Date bootTime = new Date();
	IParameters<String> globalState = Parameters.create();

	@Override
	public IParameters<String> getState() {
		return globalState;
	}
	
	public Lever(String name, IPartnerIdentity rootIdentity) {
		this.name = name;
		this.parent = rootIdentity;
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
		return urls;
	}
	
	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	@Override
	public boolean isOnline() {
		return online;
	}
	
    @Override
    public IIdentity.Type getIdentityType() {
        return IIdentity.Type.IM_PARTNER;
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
	public IPartnerIdentity getParentIdentity() {
		return parent;
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

	@Override
	public boolean stop() {
		// TODO Auto-generated method stub
		return false;
	}

}
