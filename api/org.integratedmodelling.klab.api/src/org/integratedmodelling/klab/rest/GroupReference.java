/**
 * 
 */
package org.integratedmodelling.klab.rest;

import java.util.Objects;

/**
 * Group wrapper for user consumption
 * @author Enrico Girotto
 *
 */
public class GroupReference {
	
	/**
	 * Group id
	 */
	private String id;
	/**
	 * Group description
	 */
	private String description;
	/**
	 * The icon url
	 */
	private String iconUrl;
	
	public GroupReference() {
		// nothing to do
	}
	
	/**
	 * @param id
	 * @param description
	 * @param iconUrl
	 */
	public GroupReference(String id, String description, String iconUrl) {
		super();
		this.id = id;
		this.description = description;
		this.iconUrl = iconUrl;
	}
	
	public GroupReference(Group group) {
		this(Objects.requireNonNull(group, "Group cannot be null").getName(), group.getDescription(), group.getIconUrl());
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the iconUrl
	 */
	public String getIconUrl() {
		return iconUrl;
	}
	/**
	 * @param iconUrl the iconUrl to set
	 */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
	

}
