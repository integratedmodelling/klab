package org.integratedmodelling.klab.rest;

/**
 * A bean that describes a local resource merely from the point of view of the
 * projects it's defined in. To inquire about the resource contents, use
 * {@link ResourceReference}.
 * 
 * @author ferdinando.villa
 *
 */
public class LocalResourceReference {
	
	// the official local URN
	private String urn;
	// alternative URN usable only within the project of definition (i.e., file name)
	private String localUrn;
	// true if tested and online
	private boolean online;
	// number of usages in project
	private int useCount;
	
	public LocalResourceReference() {
		// TODO Auto-generated constructor stub
	}

	public String getUrn() {
		return urn;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	public String getLocalUrn() {
		return localUrn;
	}

	public void setLocalUrn(String localUrn) {
		this.localUrn = localUrn;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public int getUseCount() {
		return useCount;
	}

	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}

	
}
