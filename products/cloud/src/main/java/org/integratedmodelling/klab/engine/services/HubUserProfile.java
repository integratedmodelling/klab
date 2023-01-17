package org.integratedmodelling.klab.engine.services;

import java.util.List;

import org.integratedmodelling.klab.rest.ObservableReference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author steve
 * 
 * This is what happens when you make a mistake and keep having to
 * eat it over and over again.  The Hub ProfileResource class should
 * have been moved out of the application to the api or made into something
 * much more rational.  Now it is a PITA.  This has to be here because I 
 * wanted a jackson serialization, fyi static class for inner is important,
 * but means it needs to be in services when it is called.  This is dumb.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HubUserProfile {
	private String name;
	private String email;
	private List<String> roles;
	
	private List<GroupEntry> groupEntries;
	@JsonInclude(Include.NON_NULL)
	private String jwtToken;

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
			
			@JsonProperty("name")
			private String id;
			
			private String description;
			
			private String iconUrl;
			
			private List<String> projectUrls;
			
			private List<ObservableReference> observables;
			
			private boolean worldview;
			
			private long maxUpload;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getDescription() {
				return description;
			}

			public void setDescription(String description) {
				this.description = description;
			}

			public String getIconUrl() {
				return iconUrl;
			}

			public void setIconUrl(String iconUrl) {
				this.iconUrl = iconUrl;
			}

			public List<String> getProjectUrls() {
				return projectUrls;
			}

			public void setProjectUrls(List<String> projectUrls) {
				this.projectUrls = projectUrls;
			}

			public List<ObservableReference> getObservables() {
				return observables;
			}

			public void setObservables(List<ObservableReference> observables) {
				this.observables = observables;
			}

			public boolean isWorldview() {
				return worldview;
			}

			public void setWorldview(boolean worldview) {
				this.worldview = worldview;
			}

			public long getMaxUpload() {
				return maxUpload;
			}

			public void setMaxUpload(long maxUpload) {
				this.maxUpload = maxUpload;
			}
		}
		

		public Group getGroup() {
			return group;
		}

		public void setGroup(Group group) {
			this.group = group;
		}
	}

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

}
