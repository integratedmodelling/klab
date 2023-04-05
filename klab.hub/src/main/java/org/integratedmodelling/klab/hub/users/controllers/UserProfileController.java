package org.integratedmodelling.klab.hub.users.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.api.JwtToken;
import org.integratedmodelling.klab.hub.api.ProfileResource;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;
import org.integratedmodelling.klab.hub.controllers.PaginationUtils;
import org.integratedmodelling.klab.hub.payload.UpdateUserRequest;
import org.integratedmodelling.klab.hub.users.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProfileController {
	
	private UserProfileService userService;
	
	private static final JwtToken JWT_TOKEN_FACTORY = new JwtToken();
	
	@Autowired
	UserProfileController(UserProfileService userService) {
		this.userService = userService;
	}
	
//	@GetMapping(API.HUB.USER_BASE)
//	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
//	public ResponseEntity<?> getAllUserProfiles() {
//		JSONObject profiles = new JSONObject().appendField("profiles", userService.getAllUserProfiles());
//		return new ResponseEntity<>(profiles,HttpStatus.OK);
//	}
	
    @GetMapping(value = API.HUB.USER_BASE, produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
    public ResponseEntity< ? > getAllUsers(
            @RequestParam(value = API.HUB.PARAMETERS.HAS_GROUP, required = false) List<String> groupsParam,
            @RequestParam(value = API.HUB.PARAMETERS.HAS_ROLES, required = false) List<String> rolesParam,
            @RequestParam(value = API.HUB.PARAMETERS.USER_SET_ACCOUNT_STATUS, required = false) List<String> accountStatusParam,
            @RequestParam(value = API.HUB.PARAMETERS.PAGE, required = false) Optional<Integer> page,
            @RequestParam(value = API.HUB.PARAMETERS.RECORDS, required = false) Optional<Integer> records
            ) {
        boolean hasPaginationParameters = PaginationUtils.hasValidPaginationParameters(page, records);
        boolean hasGroupParameters = groupsParam != null && !groupsParam.isEmpty();
        boolean hasRoleParameters = rolesParam != null && !rolesParam.isEmpty();
        boolean hasAccountStatusParameters = accountStatusParam != null && !accountStatusParam.isEmpty();

        List<Criteria> criteria = new ArrayList<Criteria>();
        if (hasPaginationParameters) {
            // TODO for now, use a hardcoded pagination
            // query.with(PageRequest.of(page.get(), records.get()));
        }
        if (hasGroupParameters) {
            // FIX org.springframework.data.mapping.MappingException: Invalid path reference agreements.agreement.groupEntries.group.name! Associations can only be pointed to directly or via their id property!
            // criterias.add(Criteria.where("agreements.agreement.groupEntries.group.name").in(group));
        }
        if (hasRoleParameters) {
            List<Role> roles = rolesParam.stream().map(r -> Role.valueOf("ROLE_" + r)).toList();
            criteria.add(Criteria.where("roles").in(roles));
        }
        if (hasAccountStatusParameters) {
            List<AccountStatus> accountStatus = accountStatusParam.stream().map(ac -> AccountStatus.valueOf(ac)).toList();
            criteria.add(Criteria.where("accountStatus").in(accountStatus));
        }

        List<User> users = userService.getAllUsersByCriteria(criteria);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

	@GetMapping(API.HUB.USER_BASE_ID)
	@PreAuthorize("authentication.getPrincipal() == #id or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> getUserProfile(@PathVariable String id) {
		ProfileResource profile = userService.getUserProfile(id);
		return new ResponseEntity<>(profile,HttpStatus.ACCEPTED);
	}
	
	@GetMapping(API.HUB.CURRENT_PROFILE)
	// TODO this is call from single user, not need PreAuthorize
	// @PreAuthorize("authentication.getPrincipal() == #username or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	//correct the auth should be caught on the token filter side.
	public ResponseEntity<?> getCurrentUserProfile(@RequestParam(required = false) boolean remote) {
		ProfileResource profile = userService.getCurrentUserProfile(remote);
		if (remote) {
            profile.setJwtToken(JWT_TOKEN_FACTORY.createEngineJwtToken(profile));
        }
		return new ResponseEntity<>(profile,HttpStatus.ACCEPTED);
	}

	@PutMapping(API.HUB.USER_BASE_ID)
	@PreAuthorize("authentication.getPrincipal() == #id" )
	public ResponseEntity<?> updateUserProfile(@PathVariable String id, @RequestBody UpdateUserRequest updateRequest) {
		ProfileResource profile = userService.updateUserByProfile(updateRequest.getProfile());
		return new ResponseEntity<>(profile,HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value= API.HUB.USER_BASE_ID, params = "remote-login")
	@PreAuthorize("authentication.getPrincipal() == #id")
	public ResponseEntity<?> getFullUserProfile(@PathVariable String id) {
		ProfileResource profile = userService.getRawUserProfile(id);
		return new ResponseEntity<>(profile,HttpStatus.ACCEPTED);
	}
	
}
