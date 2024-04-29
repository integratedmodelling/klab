package org.integratedmodelling.klab.hub.users.services;

import java.time.LocalDateTime;
import java.util.List;

import org.integratedmodelling.klab.hub.groups.dto.MongoGroup;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.hub.users.payload.UpdateUsersGroups;
import org.springframework.stereotype.Service;

@Service
public interface UserGroupEntryService {

	void setUsersGroupsByNames(UpdateUsersGroups updateRequest);

	void addUsersGroupsByNames(UpdateUsersGroups updateRequest);

	void removeUsersGroupsByNames(UpdateUsersGroups updateUserGroups);

	void addComplimentaryUserGroups(User user, LocalDateTime experiation);
	
	void deleteGroupFromUsers(String groupName);

	void removeGroupFromUsers(MongoGroup group);
	
	abstract List<String> getUsersWithGroup(String group);

}