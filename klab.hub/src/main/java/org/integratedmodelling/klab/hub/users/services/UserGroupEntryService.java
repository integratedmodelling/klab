package org.integratedmodelling.klab.hub.users.services;

import org.integratedmodelling.klab.hub.payload.UpdateUsersGroups;
import org.springframework.stereotype.Service;

@Service
public interface UserGroupEntryService {

	void setUsersGroupsByNames(UpdateUsersGroups updateRequest);

	void addUsersGroupsByNames(UpdateUsersGroups updateRequest);

	void removeUsersGroupsByNames(UpdateUsersGroups updateUserGroups);

}