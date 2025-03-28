package org.integratedmodelling.klab.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.auth.IPartnerIdentity;
import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.communication.client.Client;
import org.integratedmodelling.klab.communication.client.Client.NodeClient;
import org.integratedmodelling.klab.rest.NodeCapabilities;
import org.integratedmodelling.klab.rest.NodeReference;
import org.integratedmodelling.klab.rest.NodeReference.Permission;
import org.integratedmodelling.klab.utils.Parameters;

public class Node implements INodeIdentity {

	private String name;
	private IPartnerIdentity parent;
	private String authenticatingHub;
	private List<String> urls = new ArrayList<>();
	private Set<Permission> permissions = new HashSet<>();
	private Set<String> resourceUrls = new HashSet<>();
	private Set<String> adapterIds = new HashSet<>();
	private Set<String> namespaceIds = new LinkedHashSet<>();
	private Set<String> catalogIds = new LinkedHashSet<>();
	private Date bootTime = new Date();
	private String token;
	private boolean online;
	private String version;
	private int retryPeriod = 15;
	private long lastCheck = System.currentTimeMillis();
	private Map<String, IAuthority.Capabilities> authorities = new HashMap<>();
	IParameters<String> globalState = Parameters.create();
	private long uptime;
	private IIdentity.Type identityType;

	@Override
	public IParameters<String> getState() {
		return globalState;
	}
	
	private Client client;

	public Node(String name, IIdentity.Type type, IPartnerIdentity owner) {
		this.name = name;
		this.identityType = type;
		this.parent = owner;
	}

	
	public Node(NodeReference node, String token) {
		Partner partner = Authentication.INSTANCE.requirePartner(node.getPartner());
		this.name = node.getId();
		this.urls.addAll(node.getUrls());
		this.parent = partner;
		this.token = token;
		this.adapterIds.addAll(node.getAdapters());
		this.catalogIds.addAll(node.getCatalogs());
		this.namespaceIds.addAll(node.getNamespaces());
		this.identityType = node.getIdentityType();
	}

	/**
	 * Force a check for online status, set the online flag and return the result.
	 * Should be executed automatically every retryPeriod minutes unless the server
	 * is offline from construction (retry period will be 0 in that case).
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
	public IMonitor getMonitor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		return token;
	}

	@Override
	public boolean is(Type type) {
		return type == getIdentityType();
	}

    @Override
    public IIdentity.Type getIdentityType() {
        return this.identityType;
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
	public Set<Permission> getPermissions() {
		return permissions;
	}

	@Override
	public Collection<String> getUrls() {
		return urls;
	}

	public void setOnline(boolean b) {
		this.online = b;
	}

	public IPartnerIdentity getParent() {
		return parent;
	}

	public void setParent(IPartnerIdentity parent) {
		this.parent = parent;
	}

	public String getAuthenticatingHub() {
		return authenticatingHub;
	}

	public void setAuthenticatingHub(String authenticatingHub) {
		this.authenticatingHub = authenticatingHub;
	}

	public int getRetryPeriod() {
		return retryPeriod;
	}

	public void setRetryPeriod(int retryPeriod) {
		this.retryPeriod = retryPeriod;
	}

	public long getLastCheck() {
		return lastCheck;
	}

	public void setLastCheck(long lastCheck) {
		this.lastCheck = lastCheck;
	}

	public Client getClient() {
		if (this.client == null) {
			// client is pre-instrumented to handle Protobuf messages
			this.client = new NodeClient(this);
			this.client.setUrl(this.urls.toArray(new String[this.urls.size()]));
		}
		return this.client;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public void setBootTime(Date bootTime) {
		this.bootTime = bootTime;
	}

	@Override
	public Set<String> getAdapters() {
		return adapterIds;
	}

	@Override
	public Set<String> getResources() {
		return resourceUrls;
	}

	@Override
	public Set<String> getNamespaceIds() {
		return namespaceIds;
	}

	public void setNamespaceIds(Set<String> namespaceIds) {
		this.namespaceIds = namespaceIds;
	}

	@Override
	public Set<String> getCatalogIds() {
		return catalogIds;
	}

	public void setCatalogIds(Set<String> catalogIds) {
		this.catalogIds = catalogIds;
	}

	public void mergeCapabilities(NodeCapabilities nodeCapabilities) {
		this.catalogIds.addAll(nodeCapabilities.getResourceCatalogs());
		this.namespaceIds.addAll(nodeCapabilities.getResourceNamespaces());
		this.resourceUrls.clear();
		this.resourceUrls.addAll(nodeCapabilities.getResourceUrns());
        this.adapterIds.addAll(nodeCapabilities.getResourceAdapters().stream()
                .map(ra -> ra.getLabel()).collect(Collectors.toSet()));
		// FIXME use a proper field
		this.uptime = nodeCapabilities.getRefreshFrequencyMillis();
		this.identityType = nodeCapabilities.getIdentityType();
	}

	@Override
	public boolean stop() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, IAuthority.Capabilities> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Map<String, IAuthority.Capabilities> authorities) {
		this.authorities = authorities;
	}

    public long getUptime() {
        return uptime;
    }

    public void setUptime(long uptime) {
        this.uptime = uptime;
    }

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
