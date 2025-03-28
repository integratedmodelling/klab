package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.auth.IIdentity;

/**
 * Node capabilities: similar to engine but with resource list and status
 * 
 * @author ferdinando.villa
 *
 */
public class NodeCapabilities {

	private String name;
	private String version;
	private String build;
	private IdentityReference owner;
	private boolean acceptSubmission;
	private boolean acceptQueries;
	private List<AuthorityReference> authorities = new ArrayList<>();
	private List<ComponentReference> staticComponents = new ArrayList<>();
	private List<ComponentReference> dynamicComponents = new ArrayList<>();
	private Set<String> resourceUrns = new LinkedHashSet<>();
	private Set<String> resourceNamespaces = new LinkedHashSet<>();
	private Set<String> resourceCatalogs = new LinkedHashSet<>();
	private List<String> mirrors = new ArrayList<>();
	private List<ResourceAdapterReference> resourceAdapters = new ArrayList<>();
	private long refreshFrequencyMillis;
	private int loadFactor;
	private boolean online;
	private IIdentity.Type identityType;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	public IdentityReference getOwner() {
		return owner;
	}

	public void setOwner(IdentityReference owner) {
		this.owner = owner;
	}
	
	public List<AuthorityReference> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<AuthorityReference> authorities) {
		this.authorities = authorities;
	}

	public List<ComponentReference> getStaticComponents() {
		return staticComponents;
	}

	public void setStaticComponents(List<ComponentReference> staticComponents) {
		this.staticComponents = staticComponents;
	}

	public List<ComponentReference> getDynamicComponents() {
		return dynamicComponents;
	}

	public void setDynamicComponents(List<ComponentReference> dynamicComponents) {
		this.dynamicComponents = dynamicComponents;
	}

	public long getRefreshFrequencyMillis() {
		return refreshFrequencyMillis;
	}

	public void setRefreshFrequencyMillis(long refreshFrequencyMillis) {
		this.refreshFrequencyMillis = refreshFrequencyMillis;
	}

	public int getLoadFactor() {
		return loadFactor;
	}

	public void setLoadFactor(int loadFactor) {
		this.loadFactor = loadFactor;
	}

	/**
	 * Hubs may expose mirrors, which should be cached to use when a specific hub is
	 * not available. Nodes that have mirrors may periodically mirror updated
	 * resources. Engines do not normally have mirrors but they may use this field
	 * to notify that they are part of a high-availability cluster for a load
	 * balancer.
	 * 
	 * @return all mirrors. Possibly empty, never null.
	 */
	public List<String> getMirrors() {
		return mirrors;
	}

	public void setMirrors(List<String> mirrors) {
		this.mirrors = mirrors;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	@Override
	public String toString() {
		return "Capabilities [name=" + name + ", type=" + identityType + ", version=" + version + ", build=" + build + ", owner=" + owner
				+ ", authorities=" + authorities + ", staticComponents=" + staticComponents
				+ ", dynamicComponents=" + dynamicComponents + ", refreshFrequencyMillis=" + refreshFrequencyMillis
				+ ", loadFactor=" + loadFactor + "]";
	}

	public List<ResourceAdapterReference> getResourceAdapters() {
		return resourceAdapters;
	}

	public void setResourceAdapters(List<ResourceAdapterReference> resourceAdapters) {
		this.resourceAdapters = resourceAdapters;
	}

	public boolean isAcceptSubmission() {
		return acceptSubmission;
	}

	public void setAcceptSubmission(boolean acceptSubmission) {
		this.acceptSubmission = acceptSubmission;
	}

	public boolean isAcceptQueries() {
		return acceptQueries;
	}

	public void setAcceptQueries(boolean acceptQueries) {
		this.acceptQueries = acceptQueries;
	}

	public Set<String> getResourceNamespaces() {
		return resourceNamespaces;
	}

	public void setResourceNamespaces(Set<String> resourceNamespaces) {
		this.resourceNamespaces = resourceNamespaces;
	}

	public Set<String> getResourceCatalogs() {
		return resourceCatalogs;
	}

	public void setResourceCatalogs(Set<String> resourceCatalogs) {
		this.resourceCatalogs = resourceCatalogs;
	}

	public Set<String> getResourceUrns() {
		return resourceUrns;
	}

	public void setResourceUrns(Set<String> resourceUrns) {
		this.resourceUrns = resourceUrns;
	}
	
	   
    public IIdentity.Type getIdentityType() {
        return identityType == null ? IIdentity.Type.LEGACY_NOVE : identityType;
    }

    public void setIdentityType(IIdentity.Type identityType) {
        this.identityType = identityType;
    }
}
