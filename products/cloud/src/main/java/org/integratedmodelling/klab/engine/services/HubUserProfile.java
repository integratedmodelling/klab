package org.integratedmodelling.klab.engine.services;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HubUserProfile {
	private String name;
	private String email;
	private List<String> roles;
	private List<GroupEntry> groupEntries;

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GroupEntry> getGroupEntries() {
		return groupEntries;
	}

	public void setGroupEntries(List<GroupEntry> groupEntries) {
		this.groupEntries = groupEntries;
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class GroupEntry {

		private Group group;
		
		@JsonIgnoreProperties(ignoreUnknown = true)
		static class Group {
			private String name;

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}
		}

		public Group getGroup() {
			return group;
		}

		public void setGroup(Group group) {
			this.group = group;
		}
	}

}
