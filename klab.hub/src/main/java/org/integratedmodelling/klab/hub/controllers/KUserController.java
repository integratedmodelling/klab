package org.integratedmodelling.klab.hub.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.integratedmodelling.klab.hub.manager.KlabUserManager;
import org.integratedmodelling.klab.hub.manager.TaskManager;
import org.integratedmodelling.klab.hub.manager.TokenManager;
import org.integratedmodelling.klab.hub.payload.LogoutResponse;
import org.integratedmodelling.klab.hub.payload.PasswordChangeRequest;
import org.integratedmodelling.klab.hub.payload.UpdateUserRequest;
import org.integratedmodelling.klab.hub.payload.UpdateUsersGroups;
import org.integratedmodelling.klab.hub.service.LicenseService;
import org.integratedmodelling.klab.hub.tokens.ChangePasswordClickbackToken;
import org.integratedmodelling.klab.hub.tokens.ClickbackAction;
import org.integratedmodelling.klab.hub.tokens.ClickbackToken;
import org.integratedmodelling.klab.hub.users.ProfileResource;
import org.integratedmodelling.klab.hub.users.Role;
import org.integratedmodelling.klab.hub.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/api/users")
public class KUserController {
	
	@Autowired
	TaskManager taskManager;

	@Autowired
	TokenManager tokenManager;
	
	@Autowired
	LicenseService licenseManager;
	
	@Autowired
	KlabUserManager klabUserManager;

	@GetMapping(value= "/{id}", produces = "application/json", params="activate")
	public ResponseEntity<?> activateResponse(@PathVariable("id") String userId, @RequestParam("activate") String tokenString) throws URISyntaxException {
		ClickbackToken newUserToken = (ClickbackToken) tokenManager
				.handleVerificationToken(userId, tokenString);
		ProfileResource profile = klabUserManager.getLoggedInUserProfile();
		JSONObject clickback = new JSONObject();	
		if(newUserToken.getClickbackAction().equals(ClickbackAction.newUser)) {
			HttpHeaders headers = new HttpHeaders();
			clickback.appendField("clickback", newUserToken.getTokenString());
			clickback.appendField("profile", profile.getSafeProfile());
			return new ResponseEntity<JSONObject>(clickback, headers, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<JSONObject>(clickback,HttpStatus.CREATED);
		}	
	}
	
	@GetMapping(value= "/{id}", produces = "application/json", params= {"groups","addGroups"})
	public ResponseEntity<?> activateResponse(
			@PathVariable("id") String userId,
			@RequestParam("groups") String tokenString,
			@RequestParam("addGroups") Set<String> groups)  {
		tokenManager.handleGroupsClickbackToken(tokenString, groups);
		ProfileResource profile = klabUserManager.getLoggedInUserProfile().getSafeProfile();
		return new ResponseEntity<>(profile,HttpStatus.CREATED);
	}
	
	@GetMapping(value= "/{id}", produces = "application/json", params= {"roles"})
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> rolesResponse(
			@PathVariable("id") String userId,
			@RequestParam("roles") Collection<Role> roles ) {
		ProfileResource profile = klabUserManager.updateUserRoles(userId, roles);
		return new ResponseEntity<>(profile,HttpStatus.CREATED);
	}
	
	@PostMapping(value= "/{id}", produces = "application/json", params="requestGroups")
	@PreAuthorize("authentication.getPrincipal() == #username")
	public ResponseEntity<?> requestGroupsResponse(
			@PathVariable("id") String username,
			@RequestParam("requestGroups") List<String> groupNames,
			HttpServletRequest request) {
		taskManager.userRequestGroupsTask(username, groupNames, request);
		return new ResponseEntity<>("Sent email to system adminstrator requesting additional groups",HttpStatus.CREATED);
	}
	
	@PostMapping(value="/{id}", produces = "application/json", params="lostPassword")
	public ResponseEntity<?> lostPasswordResponse(@PathVariable("id") String username) {
		tokenManager.sendLostPasswordToken(username);
		return new ResponseEntity<>("Sent email to user " + username + " address",HttpStatus.OK);
	}
	
	@PostMapping(value = "/{id}", produces = "application/json", params="requestNewPassword")
	@PreAuthorize("authentication.getPrincipal() == #username")
	public ResponseEntity<?> requestNewPasswordResponse(@PathVariable("id") String username) {
		ChangePasswordClickbackToken token = tokenManager.createNewPasswordClickbackToken(username);
		JSONObject clickback = new JSONObject();
		clickback.appendField("user", username);
		clickback.appendField("clickback", token.getTokenString());
		return new ResponseEntity<>(clickback, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/{id}", produces = "application/json", params="setPassword")
	public ResponseEntity<?> handlePasswordChange(@PathVariable("id") String username, @RequestParam("setPassword") String token,
			@RequestBody PasswordChangeRequest passwordRequest) throws IOException, URISyntaxException {
		tokenManager.handleChangePasswordToken(username, token, passwordRequest.newPassword);
		return new ResponseEntity<String>("Set new password",HttpStatus.CREATED);
	}
	
	@PostMapping("/{id}")
	@PreAuthorize("authentication.getPrincipal() == #username" )
	public ResponseEntity<?> updateUserProfile(@PathVariable("id") String username, @RequestBody UpdateUserRequest updateRequest) {
		klabUserManager.updateUserProfile(updateRequest.getProfile());
		ProfileResource profile = klabUserManager.getLoggedInUserProfile().getSafeProfile();
		return new ResponseEntity<>(profile,HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	@PreAuthorize("authentication.getPrincipal() == #username or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ProfileResource getUserById(@PathVariable("id") String username) {
		return klabUserManager.getUserProfile(username).getSafeProfile();
	}
	
	@DeleteMapping(value= "/{id}", produces = "application/json")
	@PreAuthorize("authentication.getPrincipal() == #username or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> deleteUser(@PathVariable("id") String username) {
		klabUserManager.deleteUser(username);
    	JSONObject resp = new JSONObject();
    	resp.appendField("User", username);
    	resp.appendField("Message", String.format("The %s has been deleted", username));
    	return ResponseEntity
  			  .status(HttpStatus.OK)
  			  .body(resp);
	}
	
	@PostMapping(value ="/{id}", produces="application/json", params="logout")
	@PreAuthorize("authentication.getPrincipal() == #username")
	public ResponseEntity<?> logout(@PathVariable("id") String username,
			@RequestHeader("Authentication") String token) {
		LogoutResponse resp = tokenManager.logout(username, token);
		return resp.getResponse();
	}
	
	@GetMapping(value= "/{id}", params = "certificate")
	@PreAuthorize("authentication.getPrincipal() == #username")
	public void generateCertFile(@PathVariable("id") String username, HttpServletResponse response) throws IOException {
		User user = klabUserManager.getLoggedInUser();
		byte[] certFileContent = licenseManager.generateCert(user);
		String certFileString = String.format("attachment; filename=%s", licenseManager.get_ENGINE_CERT_FILE_NAME());
		response.setHeader("Content-disposition", certFileString);
		response.setContentType("text/plain;charset=utf-8");
		response.setContentLength(certFileContent.length);
		IOUtils.copy(new ByteArrayInputStream(certFileContent), response.getOutputStream());
		response.flushBuffer();
		IOUtils.closeQuietly(response.getOutputStream());
	}
	
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	@GetMapping(value = "", produces = "application/json")
	public ResponseEntity<?> getAllUsers() {
		List<User> users = klabUserManager.findAllMongoUsers();
		JSONObject allUsersJson = new JSONObject();
		allUsersJson.put("Users", users);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(allUsersJson);
	}
	
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	@PostMapping(value = "", produces = "application/json", params="addUsersGroups")
	public ResponseEntity<?> addUsersGroups(@RequestBody UpdateUsersGroups updateUserGroups) {
		klabUserManager.addUsersGroupsFromNames(updateUserGroups);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Updated Succesful");
	}
	
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	@PostMapping(value = "", produces = "application/json", params="removeUsersGroups")
	public ResponseEntity<?> removeUsersGroups(@RequestBody UpdateUsersGroups updateUserGroups) {
		klabUserManager.removeUsersGroups(updateUserGroups);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Updated Succesful");
	}
	
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	@PostMapping(value = "", produces = "application/json", params="setUsersGroups")
	public ResponseEntity<?> setUsersGroups(@RequestBody UpdateUsersGroups updateUserGroups) {
		klabUserManager.setUsersGroupsFromNames(updateUserGroups);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Updated Succesful");
	}
	
	@RequestMapping("/me")
	public ProfileResource getCurrentProfile() {
		return klabUserManager.getLoggedInUserProfile().getSafeProfile();
	}
}
