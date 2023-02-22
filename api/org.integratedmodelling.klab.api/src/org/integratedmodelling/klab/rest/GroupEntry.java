/**
 * 
 */
package org.integratedmodelling.klab.rest;

import java.time.LocalDate;
import java.util.Objects;

/**
 * User / Group assignment
 * @author Enrico Girotto
 *
 */
public class GroupEntry {

	private Group group;
	private LocalDate start;
	private LocalDate expiration;
	
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
	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	/**
	 * Date of expiration
	 * @return
	 */
	public LocalDate getExpiration() {
		return expiration;
	}

	public void setExpiration(LocalDate expiration) {
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
        return Objects.equals(expiration, other.expiration) && Objects.equals(group, other.group)
                && Objects.equals(start, other.start);
    }

}
