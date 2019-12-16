package org.integratedmodelling.klab.hub.users.services;

import org.integratedmodelling.klab.hub.payload.UpdateUsersGroups;

public interface UserGroupEntryService {

	void setUsersGroupsFromNames(UpdateUsersGroups updateRequest);

	void addUsersGroupsFromNames(UpdateUsersGroups updateRequest);

}