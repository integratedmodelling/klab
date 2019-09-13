package org.integratedmodelling.klab.hub.service.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.hub.config.TokenClickbackConfig;
import org.integratedmodelling.klab.hub.models.KlabGroup;
import org.integratedmodelling.klab.hub.models.ProfileResource;
import org.integratedmodelling.klab.hub.models.User;
import org.integratedmodelling.klab.hub.repository.KlabGroupRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.service.KlabUserDetailsService;
import org.integratedmodelling.klab.hub.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProfileServiceImpl implements ProfileService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	KlabGroupRepository groupRepository;
	
    @Autowired
    private KlabUserDetailsService klabUserDetailsService;
	
    @Autowired
    protected ObjectMapper objectMapper;
    
    @Autowired
    TokenClickbackConfig tokenClickbackConfig;
    
    @Override
    public Map<String, ProfileResource> getAllProfiles() {
        List<User> users = userRepository.findAll();

        Map<String, ProfileResource> result = new HashMap<>();
        for (User user : users) {
            decorate(user);
            result.put(user.getUsername(), objectMapper.convertValue(user, ProfileResource.class));
        }
        return result;
    }
    
    @Override
    public void decorate(User user) {
        if (user.getServerUrl() == null) {
            user.setServerUrl(tokenClickbackConfig.getEngineUrl().toExternalForm());
        }
    }
    
    @Override
    public ProfileResource getUserProfile(String username) {
    	User user = klabUserDetailsService.loadUserByUsername(username);
    	ProfileResource profielResource = objectMapper.convertValue(user, ProfileResource.class);
    	List<KlabGroup> kGroups = new ArrayList<>();
    	for (KlabGroup group : profielResource.getGroups()) {
    		group = groupRepository.findById(group.getId());
    		kGroups.add(group);
    	}
    	profielResource.setGroups(kGroups);
    	return profielResource;
    }

}
