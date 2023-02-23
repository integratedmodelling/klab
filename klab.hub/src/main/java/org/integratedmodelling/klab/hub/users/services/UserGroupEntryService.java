package org.integratedmodelling.klab.hub.users.services;

import java.time.LocalDateTime;
import java.util.List;

import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.payload.UpdateUsersGroups;
import org.springframework.stereotype.Service;

@Service
public interface UserGroupEntryService {

	void setUsersGroupsByNames(UpdateUsersGroups updateRequest);

	void addUsersGroupsByNames(UpdateUsersGroups updateRequest);

	void removeUsersGroupsByNames(UpdateUsersGroups updateUserGroups);

	void addPrelimenaryUserGroups(User user, LocalDateTime experiation);
	
	void deleteGroupFromUsers(String groupName);

	void removeGroupFromUsers(MongoGroup group);
	
	abstract List<String> getUsersWithGroup(String group);

}