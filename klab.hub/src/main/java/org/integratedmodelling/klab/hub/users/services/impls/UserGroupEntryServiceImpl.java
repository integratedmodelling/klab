package org.integratedmodelling.klab.hub.users.services.impls;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.payload.UpdateUsersGroups;
import org.integratedmodelling.klab.hub.repository.KlabGroupRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.GroupEntry;
import org.integratedmodelling.klab.hub.users.User;
import org.integratedmodelling.klab.hub.users.commands.UpdateUsers;
import org.integratedmodelling.klab.hub.users.services.UserGroupEntryService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public class UserGroupEntryServiceImpl implements UserGroupEntryService {
	
	private UserRepository userRepository;
	private KlabGroupRepository groupRepository;
	
	@Override
	public void setUsersGroupsFromNames(UpdateUsersGroups updateRequest) {
		
		Set<GroupEntry> groupEntries = createGroupEntries(updateRequest.getGroupnames(), updateRequest.getExperation());
		Set<User> users = new HashSet<>();
		
		for (String username: updateRequest.getUsernames()) {
			users.add(
				userRepository
					.findByUsernameIgnoreCase(username)
					.map(user -> {
						user.setGroupEntries(groupEntries);
						return user;
						})
					.orElseThrow(() ->
					new BadRequestException(String.format("%s by that username not found", username)))
			);		
		}
		
		new UpdateUsers(users, userRepository).execute();
	}
	
	@Override
	public void addUsersGroupsFromNames(UpdateUsersGroups updateRequest) {
		
		Set<GroupEntry> groupEntries = createGroupEntries(updateRequest.getGroupnames(), updateRequest.getExperation());
		Set<User> users = new HashSet<>();
		
		for (String username: updateRequest.getUsernames()) {
			users.add(
				userRepository
					.findByUsernameIgnoreCase(username)
					.map(user -> {
						user.addGroupEntries(groupEntries);
						return user;
						})
					.orElseThrow(() ->
					new BadRequestException(String.format("%s by that username not found", username)))
			);		
		}
		
		new UpdateUsers(users, userRepository).execute();
	}
	
	private Set<GroupEntry> createGroupEntries(Set<String> groupnames, DateTime experiation) {
		Set<GroupEntry> groupEntries = new HashSet<>();
		for (String groupname : groupnames) {
			groupRepository
				.findByGroupNameIgnoreCase(groupname)
				.map(grp -> 
					groupEntries.add(
						new GroupEntry(grp, experiation))
					)
				.orElseThrow(()-> 
					new BadRequestException(String.format("%s by that group name not found", groupname)));
		}
		return groupEntries;
	}
	
}
