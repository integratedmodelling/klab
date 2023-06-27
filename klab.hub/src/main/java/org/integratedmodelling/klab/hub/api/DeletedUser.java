package org.integratedmodelling.klab.hub.api;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.hub.api.User.AccountStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection="DeletedUsers")
@TypeAlias(value = "DeletedUser")
public class DeletedUser implements UserDetails {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6604658955140588560L;

	@Id
    String id;

	String username;

    String email;

    String firstName;

    String lastName;

    LocalDateTime registrationDate;
    
    LocalDateTime deletionDate;
    
    AccountStatus accountStatus = AccountStatus.deleted;

	List<GroupEntry> groups = new ArrayList<GroupEntry>();

    public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public LocalDateTime getDeletionDate() {
		return deletionDate;
	}

	public void setDeletionDate() {
		this.deletionDate = LocalDateTime.now();
	}

	public List<GroupEntry> getGroups() {
		return groups;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
    public void setGroups(Collection<GroupEntry> groups) {
        this.groups.clear();
        this.groups.addAll(groups);
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	public void setFromUser(User user) {
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.registrationDate = user.getRegistrationDate();
		this.setDeletionDate();
	}
	
}
