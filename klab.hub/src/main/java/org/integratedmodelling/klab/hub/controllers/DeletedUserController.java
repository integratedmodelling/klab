package org.integratedmodelling.klab.hub.controllers;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.integratedmodelling.klab.hub.models.DeletedUser;
import org.integratedmodelling.klab.hub.repository.DeletedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/api/system/deletedUsers")
@RolesAllowed({"ROLE_SYSTEM" })
public class DeletedUserController {
	@Autowired
	DeletedUserRepository deletedUserRepository;
	
	@GetMapping(value = "")
	ResponseEntity<?> getAllDeletedUsers() {
		List<DeletedUser> deletedUsers = deletedUserRepository.findAll();
		JSONObject allUsersJson = new JSONObject();
		allUsersJson.put("Deleted Users", deletedUsers);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(allUsersJson);
	}
}
