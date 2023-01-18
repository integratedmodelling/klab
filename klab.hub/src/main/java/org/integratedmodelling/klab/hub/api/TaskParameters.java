/**
 * 
 */
package org.integratedmodelling.klab.hub.api;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.auth.Role;

/**
 * @author Enrico Girotto
 * Neede to permit strategy pattern with multiple parameters
 *
 */
public abstract class TaskParameters {
	
	/**
	 * The http request neede to make operation with role.
	 * Sometimes is not needed, but we pass to create task, so we give it to the task even if is not used
	 */
	private HttpServletRequest request;
	
	
	public TaskParameters(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	public static abstract class TaskParametersWithRoleRequirement extends TaskParameters{
		
		private Role roleRequirement;
		
		public TaskParametersWithRoleRequirement(HttpServletRequest request, Role roleRequirement) {
			super(request);
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
