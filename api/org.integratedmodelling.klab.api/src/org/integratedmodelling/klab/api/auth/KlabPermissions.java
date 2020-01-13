package org.integratedmodelling.klab.api.auth;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Permissions in k.LAB are either "*" for public and/or a list of
 * comma-separated groups (uppercase) and/or usernames (lowercase). An empty
 * permission string means "owner only" (and possibly admin, left to
 * implementations). Prefixing either with a ! denies the permission for the
 * user or group (supposedly to narrow a previous more general one: e.g.
 * *,!BADGUYS).
 * <p>
 * This class parses a permission string and has methods to establish
 * authorization given a username and a set of groups.
 */
public class KlabPermissions {

	private boolean isPublic;
	private Set<String> allowedGroups = new HashSet<>();
	private Set<String> excludedGroups = new HashSet<>();
	private Set<String> allowedUsers = new HashSet<>();
	private Set<String> excludedUsers = new HashSet<>();

	private String encoding;

	private KlabPermissions(String s) {
		if (s != null && !s.isEmpty()) {
			String[] ss = s.split(",");
			for (String token : ss) {
				token = token.trim();
				if ("*".equals(token)) {
					this.isPublic = true;
				} else {
					if (!token.equals(token.toUpperCase())) {
						// lowercase
						if (token.startsWith("!")) {
							this.excludedUsers.add(token.substring(1));
						} else {
							this.allowedUsers.add(token);
						}
					} else {
						if (token.startsWith("!")) {
							this.excludedGroups.add(token.substring(1));
						} else {
							this.allowedGroups.add(token);
						}
					}
				}
			}
		}
		this.encoding = s;
	}

	/**
	 * Create an empty permission object (to add to if wished). Its toString()
	 * method will produce the permission string. Note that empty permissions don't
	 * prevent access to the owner and (possibly) a root administrator.
	 * 
	 * @return
	 */
	public static KlabPermissions empty() {
		return new KlabPermissions(null);
	}

	/**
	 * Create a permission object from a string.
	 * 
	 * @param permissions
	 * @return
	 */
	public static KlabPermissions create(String permissions) {
		return new KlabPermissions(permissions);
	}

	public boolean isAuthorized(String username, Collection<String> groups) {
		boolean authorized = isPublic;
		if (!authorized) {
			authorized = allowedUsers.contains(username);
		}
		if (!authorized) {
			for (String s : groups) {
				if (allowedGroups.contains(s)) {
					authorized = true;
					break;
				}
			}
		}

		boolean prevented = false;
		if (authorized) {
			// check if prevented
			prevented = excludedUsers.contains(username);
			if (!prevented) {
				for (String s : groups) {
					if (excludedGroups.contains(s)) {
						prevented = true;
						break;
					}
				}
			}
		}

		return authorized && !prevented;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Set<String> getAllowedGroups() {
		return allowedGroups;
	}

	public Set<String> getExcludedGroups() {
		return excludedGroups;
	}

	public Set<String> getAllowedUsers() {
		return allowedUsers;
	}

	public Set<String> getExcludedUsers() {
		return excludedUsers;
	}

	public String toString() {
		return encoding == null ? encode() : encoding;
	}

	private String encode() {
		StringBuffer buffer = new StringBuffer(256);
		if (isPublic)
			buffer.append("*");
		for (String group : allowedGroups) {
			buffer.append((buffer.length() == 0 ? "" : ",") + group);
		}
		for (String user : allowedUsers) {
			buffer.append((buffer.length() == 0 ? "" : ",") + user);
		}
		for (String group : excludedGroups) {
			buffer.append((buffer.length() == 0 ? "" : ",") + "!" + group);
		}
		for (String user : excludedUsers) {
			buffer.append((buffer.length() == 0 ? "" : ",") + "!" + user);
		}
		this.encoding = buffer.toString();
		return this.encoding;
	}

}
