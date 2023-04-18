package org.integratedmodelling.klab.hub.users.services;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.api.ProfileResource;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.commands.UpdateUser;
import org.integratedmodelling.klab.hub.exception.UserByEmailDoesNotExistException;
import org.integratedmodelling.klab.hub.exception.UserDoesNotExistException;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.controllers.criteria.UserProfileCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserProfileServiceImpl implements UserProfileService {
	
    @Autowired
    MongoTemplate mongoTemplate;

	private UserRepository userRepository;
	
	private ObjectMapper objectMapper;
	
	public UserProfileServiceImpl(UserRepository userRepository, ObjectMapper objectMapper) {
		super();
		this.userRepository = userRepository;
		this.objectMapper = objectMapper;
	}

	@Override
	public ProfileResource updateUserByProfile(ProfileResource profile) {
		User user = userRepository.findByNameIgnoreCase(profile.getUsername())
				.filter(u -> u.getUsername().equals(profile.getUsername()))
				.orElseThrow(() ->  
					new KlabException("Current User context does match updated profile username"));
		user.updateFromProfileResource(profile);
		User updatedUser = new UpdateUser(user, userRepository).execute();
		ProfileResource updatedProfile = objectMapper.convertValue(updatedUser, ProfileResource.class);
		return updatedProfile.getSafeProfile();
	}
	
	@Override
	public ProfileResource getUserSafeProfile(User user) {
		if (user == null) {
			throw new UserDoesNotExistException();
		}
		ProfileResource profile = objectMapper.convertValue(user, ProfileResource.class);
		return profile.getSafeProfile();
	}

	@Override
	public ProfileResource getUserProfile(String username) {
		User user = userRepository.findByNameIgnoreCase(username)
				.orElseThrow(() ->  
					new UserDoesNotExistException());
		return getUserSafeProfile(user);
	}

	@Override
	public ProfileResource getCurrentUserProfile(boolean remote) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByNameIgnoreCase(username)
				.orElseThrow(() ->  
					new UserDoesNotExistException());
		ProfileResource profile = objectMapper.convertValue(user, ProfileResource.class);
		if (remote) {
		    return profile;
		}
		return profile.getSafeProfile();
	}

	@Override
	public ProfileResource getRawUserProfile(String username) {
		User user = userRepository.findByNameIgnoreCase(username)
				.orElseThrow(() ->  
					new UserDoesNotExistException());
		ProfileResource profile = objectMapper.convertValue(user, ProfileResource.class);
		return profile;
	}

	@Override
	public ProfileResource getUserProfileByEmail(String email) {
		User user = userRepository.findByEmailIgnoreCase(email)
				.orElseThrow(() ->  
					new UserByEmailDoesNotExistException(email));
		return getUserSafeProfile(user);
	}

    private List<ProfileResource> getAllUserProfiles() {
        return userRepository.findAll().stream().map(
                user -> objectMapper.convertValue(user, ProfileResource.class).getSafeProfile()).toList();
    }

    private List<ProfileResource> getAllUserProfilesPaginated(Pageable pagination) {
        return userRepository.findAll(pagination).stream().map(
                user -> objectMapper.convertValue(user, ProfileResource.class).getSafeProfile()).toList();
    }

    @Override
    public List<ProfileResource> getAllUsersByCriteria(UserProfileCriteria criteria) {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<Criteria>();
        if(criteria.pagination.isPresent()) {
            query.with(criteria.pagination.get());
        }
        if (!criteria.groupsCriteria.isEmpty()) {
            Query groupsQuery = new Query();
            groupsQuery.addCriteria(Criteria.where("name").in(criteria.groupsCriteria));
            List<MongoGroup> mg = mongoTemplate.find(groupsQuery, MongoGroup.class);
            criteriaList.add(Criteria.where("groupEntries.group.$id").in(mg.stream().map(g -> new ObjectId(g.getId())).toList()));
        }
        if (!criteria.rolesCriteria.isEmpty()) {
            criteriaList.add(Criteria.where("roles").in(criteria.rolesCriteria));
        }
        if (!criteria.accountStatusCriteria.isEmpty()) {
            criteriaList.add(Criteria.where("accountStatus").in(criteria.accountStatusCriteria));
        }

        if (criteriaList.isEmpty()) {
            return criteria.pagination.isPresent() ?
                    getAllUserProfilesPaginated(criteria.pagination.get()) : getAllUserProfiles();
        }
        query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
        return mongoTemplate.find(query, User.class).stream().map(u -> getUserSafeProfile(u)).toList();
    }

}
