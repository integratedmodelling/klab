package org.integratedmodelling.klab.hub.users.services;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.hub.api.GroupEntry;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.commands.UpdateUser;
import org.integratedmodelling.klab.hub.commands.UpdateUsers;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.exception.GroupDoesNotExistException;
import org.integratedmodelling.klab.hub.exception.UserDoesNotExistException;
import org.integratedmodelling.klab.hub.payload.UpdateUsersGroups;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public class UserGroupEntryServiceImpl implements UserGroupEntryService {
	
	public UserGroupEntryServiceImpl(UserRepository userRepository, MongoGroupRepository groupRepository) {
		super();
		this.userRepository = userRepository;
		this.groupRepository = groupRepository;
	}

	private UserRepository userRepository;
	private MongoGroupRepository groupRepository;
	
	@Override
	public void setUsersGroupsByNames(UpdateUsersGroups updateRequest) {
		
		Set<GroupEntry> groupEntries = createGroupEntries(updateRequest.getGroupnames(), updateRequest.getExperation());
		Set<User> users = new HashSet<>();
		
		for (String username: updateRequest.getUsernames()) {
			users.add(
				userRepository
					.findByNameIgnoreCase(username)
					.map(user -> {
						user.setGroupEntries(groupEntries);
						return user;
						})
					.orElseThrow(() ->
					new UserDoesNotExistException(username))
			);		
		}
		
		new UpdateUsers(users, userRepository).execute();
	}
	
	@Override
	public void addUsersGroupsByNames(UpdateUsersGroups updateRequest) {
		
		Set<GroupEntry> groupEntries = createGroupEntries(updateRequest.getGroupnames(), updateRequest.getExperation());
		Set<User> users = new HashSet<>();
		
		for (String username: updateRequest.getUsernames()) {
			users.add(
				userRepository
					.findByNameIgnoreCase(username)
					.map(user -> {
						user.addGroupEntries(groupEntries);
						return user;
						})
					.orElseThrow(() ->
					new UserDoesNotExistException(username))
			);		
		}
		
		new UpdateUsers(users, userRepository).execute();
	}
	
	@Override
	public void removeUsersGroupsByNames(UpdateUsersGroups updateRequest) {
		Set<GroupEntry> groupEntries = createGroupEntries(updateRequest.getGroupnames(), updateRequest.getExperation());
		Set<User> users = new HashSet<>();
		
		for (String username: updateRequest.getUsernames()) {
			users.add(
				userRepository
					.findByNameIgnoreCase(username)
					.map(user -> {
						user.removeGroupEntries(groupEntries);
						return user;
						})
					.orElseThrow(() ->
					new UserDoesNotExistException(username))
			);		
		}
		
		new UpdateUsers(users, userRepository).execute();
	}
	
	@Override
	public void addPrelimenaryUserGroups(User user, DateTime experiation) {
		Set<GroupEntry> groupEntries = createPrelimGroupEntries(experiation);
		user.addGroupEntries(groupEntries);
		new UpdateUser(user, userRepository).execute();
		
	}
	
	private Set<GroupEntry> createGroupEntries(Set<String> groupnames, DateTime experiation) {
		Set<GroupEntry> groupEntries = new HashSet<>();
		for (String groupname : groupnames) {
			groupRepository
				.findByNameIgnoreCase(groupname)
				.map(grp -> 
					groupEntries.add(
						new GroupEntry(grp, experiation))
					)
				.orElseThrow(()-> 
					new GroupDoesNotExistException(groupname));
		}
		return groupEntries;
	}
	
	private Set<GroupEntry> createPrelimGroupEntries(DateTime experiation) {
		Set<GroupEntry> groupEntries = new HashSet<>();
		groupRepository
			.findPrelimGroups()
			.forEach(grp ->
				groupEntries.add(
					new GroupEntry(grp, experiation)
				)
			);
		return groupEntries;
	}
}
