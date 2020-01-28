/**
 * 
 */
package org.integratedmodelling.klab.hub.tasks;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.users.Role;

/**
 * @author Enrico Girotto
 * Neede to permit strategy pattern with multiple parameters
 *
 */
public abstract class TaskParameters {
	
	private String requestee;
	/**
	 * The http request neede to make operation with role.
	 * Sometimes is not needed, but we pass to create task, so we give it to the task even if is not used
	 */
	private HttpServletRequest request;
	
	
	public TaskParameters(String requestee, HttpServletRequest request) {
		this.requestee = requestee;
		this.request = request;
	}

	/**
	 * @return the requestee
	 */
	public String getRequestee() {
		return requestee;
	}

	/**
	 * @param requestee the requestee to set
	 */
	public void setRequestee(String requestee) {
		this.requestee = requestee;
	}
	
	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	public static abstract class TaskParametersWithRoleRequirement extends TaskParameters{
		
		private Role roleRequirement;
		
		public TaskParametersWithRoleRequirement(String requestee, HttpServletRequest request, Role roleRequirement) {
			super(requestee, request);
			this.roleRequirement = roleRequirement;
		}

		/**
		 * @return the roleRequirement
		 */
		public Role getRoleRequirement() {
			return roleRequirement;
		}
		
	}

}
