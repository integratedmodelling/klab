package org.integratedmodelling.klab.hub.commands;

import org.integratedmodelling.klab.hub.api.ProfileResource;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.UserRepository;

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
		User user = repository.findByNameIgnoreCase(username)
				.orElseThrow(() ->  
					new BadRequestException("User does not exist"));
		ProfileResource profile = mapper.convertValue(user, ProfileResource.class);
		return profile;
	}
	

}
