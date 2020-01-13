package org.integratedmodelling.klab.hub.users.commands;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.ProfileResource;
import org.integratedmodelling.klab.hub.users.User;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GetUserProfile {
	
	private UserRepository repository;
	private String username;
	private ObjectMapper mapper;
	
	public GetUserProfile(UserRepository repository, String username, ObjectMapper objectMapper) {
		this.repository = repository;
		this.username = username;
		this.mapper = objectMapper;
	}


	public ProfileResource execute() {
		User user = repository.findByUsernameIgnoreCase(username)
				.orElseThrow(() ->  
					new BadRequestException("User does not exist"));
		ProfileResource profile = mapper.convertValue(user, ProfileResource.class);
		return profile;
	}
	

}
