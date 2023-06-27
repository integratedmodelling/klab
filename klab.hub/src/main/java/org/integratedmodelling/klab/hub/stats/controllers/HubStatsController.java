package org.integratedmodelling.klab.hub.stats.controllers;

import java.util.List;

import org.integratedmodelling.klab.api.API;
//import org.integratedmodelling.klab.hub.api.JwtToken;
import org.integratedmodelling.klab.hub.stats.services.HubStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HubStatsController {
	
	@Autowired
	private HubStatsService statsService;
	
	//private static final JwtToken JWT_TOKEN_FACTORY = new JwtToken();
	
	@Autowired
	HubStatsController(HubStatsService statsService) {
		this.statsService = statsService;
	}
	
	
	@GetMapping(API.HUB.USER_STATS)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> getUsersByRegistrationDate(@RequestParam(defaultValue = "month", required = false) String groupBy) {
		List<GroupUsersByDate> groupedUsers = statsService.registeredUsers(groupBy);
		
		/* get rid of entries where registrationDate is null */
		groupedUsers.removeIf(o -> o.getDateString() == "NaN");
		
		return new ResponseEntity<>(groupedUsers,HttpStatus.OK);
	}
	
}
