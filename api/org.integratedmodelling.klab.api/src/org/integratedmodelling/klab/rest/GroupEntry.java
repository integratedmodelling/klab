/**
 * 
 */
package org.integratedmodelling.klab.rest;

import java.util.Objects;

/**
 * User / Group assignment
 * @author Enrico Girotto
 *
 */
public class GroupEntry {

	private Group group;
	private long start;
	private long expiration;
	
	public GroupEntry() {}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * Date of assignment
	 * @return
	 */
	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	/**
	 * Date of expiration
	 * @return
	 */
	public long getExpiration() {
		return expiration;
	}

	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}

	@Override
	public int hashCode() {
		return Objects.hash(expiration, group, start);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupEntry other = (GroupEntry) obj;
		return expiration == other.expiration && Objects.equals(group, other.group) && start == other.start;
	}

	@Override
	public String toString() {
		return "GroupEntry [group=" + group + ", start=" + start + ", expiration=" + expiration + "]";
	};
	
	
	
}
