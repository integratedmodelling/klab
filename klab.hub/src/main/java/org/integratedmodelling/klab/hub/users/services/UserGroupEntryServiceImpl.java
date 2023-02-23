package org.integratedmodelling.klab.hub.users.services;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.api.GroupEntry;
import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.commands.GetMongoGroupByName;
import org.integratedmodelling.klab.hub.commands.UpdateUser;
import org.integratedmodelling.klab.hub.commands.UpdateUsers;
import org.integratedmodelling.klab.hub.exception.UserDoesNotExistException;
import org.integratedmodelling.klab.hub.payload.UpdateUsersGroups;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
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
		
		Set<GroupEntry> groupEntries = createGroupEntries(updateRequest.getGroupNames(), updateRequest.getExpiration());
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
		
		Set<GroupEntry> groupEntries = createGroupEntries(updateRequest.getGroupNames(), updateRequest.getExpiration());
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
		Set<GroupEntry> groupEntries = createGroupEntries(updateRequest.getGroupNames(), updateRequest.getExpiration());
		Set<User> users = new HashSet<>();
		
		for (String username: updateRequest.getUsernames()) {
			userRepository
				.findByNameIgnoreCase(username)
				.ifPresent(user -> {
					user.removeGroupEntries(groupEntries);
					users.add(user);
				});		
		}
		
		new UpdateUsers(users, userRepository).execute();
	}
	
	@Override
	public void addComplimentaryUserGroups(User user, LocalDateTime experiation) {
		Set<GroupEntry> groupEntries = createComplimentaryGroupEntries(experiation);
		user.addGroupEntries(groupEntries);
		new UpdateUser(user, userRepository).execute();
		
	}
	
	private Set<GroupEntry> createGroupEntries(Set<String> groupnames, LocalDateTime expiration) {
		Set<GroupEntry> groupEntries = new HashSet<>();
		for (String groupname : groupnames) {
			groupRepository
				.findByNameIgnoreCase(groupname)
				.ifPresent(grp -> {
					GroupEntry entry = new GroupEntry(grp, expiration);
					groupEntries.add(entry);
				});
		}
		return groupEntries;
	}
	
	private Set<GroupEntry> createComplimentaryGroupEntries(LocalDateTime expiration) {
		Set<GroupEntry> groupEntries = new HashSet<>();
		groupRepository
			.findComplimentaryGroups()
			.forEach(grp ->
				groupEntries.add(
					new GroupEntry(grp, expiration)
				)
			);
		return groupEntries;
	}

	@Override
	public void deleteGroupFromUsers(String groupName) {
		Set<String> username = new HashSet<>();
		Set<String> groupname = new HashSet<>();
		userRepository.findAll().forEach(user -> username.add(user.getUsername()));
		groupname.add(groupName);
		//on the remove function the expiration is not used, but called for create, group entry.
		UpdateUsersGroups updateRequest = new UpdateUsersGroups(username, groupname, LocalDateTime.now());
		removeUsersGroupsByNames(updateRequest);
	}

	@Override
	public void removeGroupFromUsers(MongoGroup group) {
		
		Set<String> groupNames = new HashSet<>();
		Set<String> usernames = new HashSet<>();
		
		userRepository.findAll().forEach(u -> usernames.add(u.getUsername()));
		groupNames.add(group.getName());
		
		UpdateUsersGroups request = new UpdateUsersGroups(usernames, groupNames, LocalDateTime.now());		
		removeUsersGroupsByNames(request);
	}
	
	@Override
	public List<String> getUsersWithGroup(String group) {
		MongoGroup lookup = new GetMongoGroupByName(group, groupRepository).execute();
		ObjectId id = new ObjectId(lookup.getId());
		return userRepository.getUsersByGroupEntriesWithGroupId(id).stream()
			.map(User::getName)
			.collect(Collectors.toList());
	}
}
