package org.integratedmodelling.klab.hub.users.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.agreements.services.AgreementService;
import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.api.AgreementEntry;
import org.integratedmodelling.klab.hub.api.GroupEntry;
import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.commands.GetMongoGroupByName;
import org.integratedmodelling.klab.hub.commands.UpdateAgreement;
import org.integratedmodelling.klab.hub.commands.UpdateUser;
import org.integratedmodelling.klab.hub.commands.UpdateUsers;
import org.integratedmodelling.klab.hub.exception.GroupDoesNotExistException;
import org.integratedmodelling.klab.hub.exception.UserDoesNotExistException;
import org.integratedmodelling.klab.hub.payload.UpdateUsersGroups;
import org.integratedmodelling.klab.hub.repository.AgreementRepository;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.utils.DateConversionUtils;
import org.springframework.stereotype.Service;

@Service
public class UserGroupEntryServiceImpl implements UserGroupEntryService {
	
	public UserGroupEntryServiceImpl(UserRepository userRepository, MongoGroupRepository groupRepository, AgreementService agreementService, AgreementRepository agreementRepository) {
		super();
		this.userRepository = userRepository;
		this.groupRepository = groupRepository;
		this.agreementService = agreementService;
		this.agreementRepository = agreementRepository;
	}

	private UserRepository userRepository;
	private MongoGroupRepository groupRepository;
	private AgreementService agreementService;
	private AgreementRepository agreementRepository;
	
	@Override
	public void setUsersGroupsByNames(UpdateUsersGroups updateRequest) {
		
		Set<GroupEntry> groupEntries = createGroupEntries(updateRequest.getGroupNames(), updateRequest.getExpiration());
		Set<User> users = new HashSet<>();
		
		for (String username: updateRequest.getUsernames()) {
			users.add(
				userRepository
					.findByNameIgnoreCase(username)
					.map(user -> {
						user.getAgreements().stream().findFirst().get().getAgreement().setGroupEntries(groupEntries);
						return user;
						})
					.orElseThrow(() ->
					new UserDoesNotExistException(username))
			);		
		}		
		
		new UpdateUsers(users, userRepository).execute();
	}

    private Optional<Date> calculateGroupExpirationTime(String username, String groupName) {
        User user = userRepository.findByNameIgnoreCase(username)
                .orElseThrow(() -> new UserDoesNotExistException(username));

        List<String> dependedOnGroupNames = groupRepository.findByNameIgnoreCase(groupName)
                .orElseThrow(() -> new GroupDoesNotExistException(groupName))
                .getDependsOn();

        if(dependedOnGroupNames.isEmpty()) {
            return Optional.empty();
        }

        List<MongoGroup> dependedOnGroups = groupRepository.findByNameIn(dependedOnGroupNames);
        Map<String, Date> groupMaxDate = new HashMap<>();
        for (String group : dependedOnGroupNames) {
            Collection<Agreement> agreementsWithGroup = user.getAgreements().stream()
                    .map(AgreementEntry::getAgreement)
                    .filter(ag -> ag.isValid() && ag.getGroupEntries().contains(group))
                    .collect(Collectors.toUnmodifiableSet());

            if (agreementsWithGroup.isEmpty()) {
                continue;
            }

            boolean isNonExpiringGroup = agreementsWithGroup.stream()
                    .anyMatch(a -> !a.isExpirable());
            if (isNonExpiringGroup) {
                continue;
            }

            Date maxFoundExpiration = agreementsWithGroup.stream()
                    .map(Agreement::getExpirationDate)
                    .max(Date::compareTo).get();

            long defaultExpirationDateOfGroup = dependedOnGroups.stream()
                    .filter(mg -> mg.getName().equalsIgnoreCase(groupName))
                    .findFirst().get()
                    .getDefaultExpirationTime();
            if (defaultExpirationDateOfGroup != 0) {
                Date defaultExpiration = new Date(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + defaultExpirationDateOfGroup);
                maxFoundExpiration = maxFoundExpiration.before(defaultExpiration) 
                        ? maxFoundExpiration
                        : defaultExpiration;
            }
            groupMaxDate.put(groupName, maxFoundExpiration);
        }

        if (groupMaxDate.isEmpty()) {
            return Optional.empty();
        }
        return groupMaxDate.values().stream().min(Date::compareTo);
    }

    private void setExpirationTime(String username, GroupEntry group) {
        Optional<Date> expirationDate = calculateGroupExpirationTime(username, group.getGroupName());
        if (expirationDate.isPresent()) {
            group.setExpiration(DateConversionUtils.dateToLocalDateTime(expirationDate.get()));
        }
    }

	@Override
	public void addUsersGroupsByNames(UpdateUsersGroups updateRequest) {
		Set<GroupEntry> groupEntries = createGroupEntries(updateRequest.getGroupNames(), updateRequest.getExpiration());
		Set<User> users = new HashSet<>();
		Set<Agreement> agreements = new HashSet<>();
		
		for (String username: updateRequest.getUsernames()) {
			users.add(
				userRepository
					.findByNameIgnoreCase(username)
					.map(user -> {
					    List<GroupEntry> groupsToAdd = new ArrayList<GroupEntry>();
					    groupsToAdd.addAll(groupEntries);
					    groupsToAdd.stream().forEach(g -> setExpirationTime(username, g));

						user.getAgreements().stream().findFirst().get().getAgreement().addGroupEntries(groupEntries);
						agreements.add(user.getAgreements().stream().findFirst().get().getAgreement());
						return user;
						})
					.orElseThrow(() -> new UserDoesNotExistException(username))
			);		
		}
		
		new UpdateAgreement(agreements, agreementRepository).execute();
		//new UpdateUsers(users, userRepository).execute();
	}

	@Override
	public void removeUsersGroupsByNames(UpdateUsersGroups updateRequest) {
		Set<GroupEntry> groupEntries = createGroupEntries(updateRequest.getGroupNames(), updateRequest.getExpiration());
		Set<User> users = new HashSet<>();
		Set<Agreement> agreements = new HashSet<>();
		
		for (String username: updateRequest.getUsernames()) {
			userRepository
				.findByNameIgnoreCase(username)
				.ifPresent(user -> {
					user.getAgreements().stream().findFirst().get().getAgreement().removeGroupEntries(groupEntries);
					agreements.add(user.getAgreements().stream().findFirst().get().getAgreement());
					users.add(user);
				});		
		}
		
		new UpdateAgreement(agreements, agreementRepository).execute();
		//new UpdateUsers(users, userRepository).execute();
	}
	
    @Override
    public void addComplimentaryUserGroups(User user, LocalDateTime experiation) {
        Set<GroupEntry> groupEntries = createComplimentaryGroupEntries(experiation);
        user.getAgreements().stream().findFirst().get().getAgreement().addGroupEntries(groupEntries);
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
