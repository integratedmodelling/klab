package org.integratedmodelling.klab.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.auth.IPartnerIdentity;
import org.integratedmodelling.klab.api.auth.IServerIdentity;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.runtime.rest.IClient;
import org.integratedmodelling.klab.communication.client.Client;
import org.integratedmodelling.klab.rest.HubReference;
import org.integratedmodelling.klab.utils.Parameters;

public class Hub implements IServerIdentity {

    String name;
    IPartnerIdentity parent;
    List<String> urls = new ArrayList<>();
    Date bootTime = new Date();
    boolean online;
    int retryPeriod = 15;

    private long lastCheck = System.currentTimeMillis();

    private Client client;
	IParameters<String> globalState = Parameters.create();

	@Override
	public IParameters<String> getState() {
		return globalState;
	}
	
    public Hub(String name, IPartnerIdentity owner) {
        this.name = name;
        this.parent = owner;
    }

    public Hub(HubReference hub) {
		Partner partner = Authentication.INSTANCE.requirePartner(hub.getPartner());
		this.name = hub.getId();
		this.urls.addAll(hub.getUrls());
		this.parent = partner;
    }

    /**
     * Force a check for online status, set the online flag and 
     * return the result. Should be executed automatically every
     * retryPeriod minutes unless the server is offline from construction (retry
     * period will be 0 in that case).
     * 
     * @return true if online
     */
    public boolean ping() {
        this.online = false;
        for (String url : urls) {
            if ((this.online = client.ping(url))) {
                break;
            }
        }
        this.lastCheck = System.currentTimeMillis();
        return this.online;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Date getBootTime() {
        return bootTime;
    }

    @Override
    public Client getClient() {
		if (this.client == null) {
			this.client = Client.create().onBehalfOf(this);
			this.client.setUrl(this.urls.toArray(new String[this.urls.size()]));
		}
		return this.client;
    }
    
    @Override
    public IMonitor getMonitor() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public IIdentity.Type getIdentityType() {
        return IIdentity.Type.IM_PARTNER;
    }
    
    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean is(Type type) {
        return type == getIdentityType();
    }

    @Override
    public <T extends IIdentity> T getParentIdentity(Class<T> type) {
        return IIdentity.findParent(this, type);
    }

    @Override
    public IPartnerIdentity getParentIdentity() {
        return parent;
    }

    @Override
    public boolean isOnline() {
        if ((System.currentTimeMillis() - lastCheck) > retryPeriod * (1000 * 60)) {
            return ping();
        }
        return this.online;
    }

    @Override
    public Collection<String> getUrls() {
        return urls;
    }

    public void setOnline(boolean b) {
        this.online = b;
    }

	@Override
	public String load(IBehavior behavior, IContextualizationScope scope) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean stop(String behaviorId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stop() {
		// TODO Auto-generated method stub
		return false;
	}
}
