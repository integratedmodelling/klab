package org.integratedmodelling.klab.hub.controllers;

import java.util.Map;

import javax.annotation.security.RolesAllowed;

import org.integratedmodelling.klab.hub.models.ProfileResource;
import org.integratedmodelling.klab.hub.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
@RequestMapping(value = "api/profiles")
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
    @RequestMapping(value = "", method = RequestMethod.GET,  produces = "application/json")
	public JSONObject getAllUsers() {
    	Map<String, ProfileResource> profiles = profileService.getAllProfiles();
        JSONObject profilesJson = new JSONObject();
        profilesJson.put("Profiles", profiles);
        return profilesJson;
	};
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ProfileResource getUserByUserNameOrEmail(@PathVariable("id") String id) {
		ProfileResource user = profileService.getUserProfile(id);
		return user;
	}
}
