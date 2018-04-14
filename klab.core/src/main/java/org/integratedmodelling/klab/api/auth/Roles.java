package org.integratedmodelling.klab.api.auth;

/**
 * Roles tied to users, securing the API.
 * 
 * @author Ferd
 *
 */
public interface Roles {

	/**
	 * Anything available to the public. Every user (including anonymous) has this
	 * role.
	 */
	public static final String PUBLIC = "ROLE_PUBLIC";

	/**
	 * Logged in engine user. The engine owner is automatically promoted to this
	 * role, too.
	 */
	public static final String ENGINE_USER = "ROLE_USER";

	/**
	 * The owner of the certificate that the engine reads at startup. Logged in
	 * users have {@link #ENGINE_USER} but not {@code #OWNER}.
	 */
	public static final String OWNER = "ROLE_OWNER";

	/**
	 * Administrator role. Anonymous is promoted to admin if connecting from the
	 * local host. The engine owner also has this. Logged in users must be
	 * explicitly authorized as administrators.
	 */
	public static final String ADMIN = "ROLE_ADMIN";

	/**
	 * Session role is attributed when a session is established and its ID is used
	 * for authorization.
	 */
	public static final String SESSION = "ROLE_SESSION";

}
