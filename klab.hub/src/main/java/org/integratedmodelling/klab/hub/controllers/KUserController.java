package org.integratedmodelling.klab.hub.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.integratedmodelling.klab.hub.manager.KlabUserManager;
import org.integratedmodelling.klab.hub.manager.LicenseManager;
import org.integratedmodelling.klab.hub.manager.TokenManager;
import org.integratedmodelling.klab.hub.models.ProfileResource;
import org.integratedmodelling.klab.hub.models.Role;
import org.integratedmodelling.klab.hub.models.User;
import org.integratedmodelling.klab.hub.models.tokens.ChangePasswordClickbackToken;
import org.integratedmodelling.klab.hub.models.tokens.ClickbackAction;
import org.integratedmodelling.klab.hub.models.tokens.ClickbackToken;
import org.integratedmodelling.klab.hub.payload.PasswordChangeRequest;
import org.integratedmodelling.klab.hub.payload.UpdateUserRequest;
import org.integratedmodelling.klab.hub.repository.UserRepository;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/api/users")
public class KUserController {

	@Autowired
	TokenManager tokenManager;
	
	@Autowired
	LicenseManager licenseManager;
	
	@Autowired
	KlabUserManager klabUserManager;
	
	@Autowired
	private UserRepository repository;

	@GetMapping(value= "/{id}", produces = "application/json", params="activate")
	public ResponseEntity<?> activateResponse(@PathVariable("id") String userId, @RequestParam("activate") String tokenString) throws URISyntaxException {
		ClickbackToken verificationToken = (ClickbackToken) tokenManager
				.handleVerificationToken(userId, tokenString);
		ProfileResource profile = klabUserManager.getLoggedInUserProfile();
		JSONObject clickback = new JSONObject();	
		if(verificationToken.getClickbackAction().equals(ClickbackAction.password)) {
			HttpHeaders headers = new HttpHeaders();
			ChangePasswordClickbackToken token = tokenManager.createNewPasswordClickbackToken(userId);
			clickback.appendField("clickback", token.getTokenString());
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
			@RequestParam("addGroups") List<String> groups)  {
		tokenManager.handleGroupsClickbackToken(userId, tokenString, groups);
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
	
	@GetMapping(value= "/{id}", produces = "application/json", params="requestGroups")
	@PreAuthorize("authentication.getPrincipal() == #username")
	public ResponseEntity<?> requestGroupsResponse(@PathVariable("id") String username, @RequestParam("requestGroups") List<String> groups) {
		tokenManager.sendGroupClickbackToken(username, groups);
		return new ResponseEntity<>("Sent email to system adminstrator requesting additional groups",HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = "application/json", params="password")
	@PreAuthorize("authentication.getPrincipal() == #username")
	public ResponseEntity<?> requestNewPasswordResponse(@PathVariable("id") String username) {
		ChangePasswordClickbackToken token = tokenManager.createNewPasswordClickbackToken(username);
		JSONObject clickback = new JSONObject();	
		clickback.appendField("clickback", token.getTokenString());
		return new ResponseEntity<>(clickback, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/{id}", produces = "application/json", params="password")
	public ResponseEntity<?> handlePasswordChange(@PathVariable("id") String username, @RequestParam("password") String token,
			@RequestBody PasswordChangeRequest passwordRequest) throws IOException, URISyntaxException {
		tokenManager.handleChangePasswordToken(username, token, passwordRequest.newPassword);
		return new ResponseEntity<String>("Set new password",HttpStatus.CREATED);
	}
	
	@PostMapping("/{id}")
	@PreAuthorize("authentication.getPrincipal() == #username")
	public ResponseEntity<?> updateUserProfile(@PathVariable("id") String username, @RequestBody UpdateUserRequest updateRequest) {
		klabUserManager.updateUserProfile(updateRequest.getProfile());
		ProfileResource profile = klabUserManager.getLoggedInUserProfile().getSafeProfile();
		return new ResponseEntity<>(profile,HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	@PreAuthorize("authentication.getPrincipal() == #username or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public JSONObject getUserById(@PathVariable("id") String id) {
		Optional<User> user = repository.findByUsernameIgnoreCase(id);
		if (user.isPresent()) {
			JSONObject Response = new JSONObject();
			Response.put("User", user.get());
			return Response;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}
	}
	
	@DeleteMapping(value= "/{id}", produces = "application/json")
	@PreAuthorize("authentication.getPrincipal() == #username or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> deleteUser(@PathVariable("id") String username) {
		klabUserManager.deleteUser(username);
    	JSONObject resp = new JSONObject();
    	resp.appendField("User", username);
    	resp.appendField("Message", String.format("The %s has been deleted", username));
    	return ResponseEntity
  			  .status(HttpStatus.OK)
  			  .body(resp);
	}
	
	@GetMapping(value= "/{id}", params = "certificate")
	@PreAuthorize("authentication.getPrincipal() == #username")
	public void generateCertFile(@PathVariable("id") String username, HttpServletResponse response) throws IOException {
		User user = klabUserManager.getLoggedInUser();
		byte[] certFileContent = licenseManager.generateEngineCert(user);
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
		List<User> users = repository.findAll();
		JSONObject allUsersJson = new JSONObject();
		allUsersJson.put("Users", users);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(allUsersJson);
	}
	
	@RequestMapping("/me")
	public ProfileResource getCurrentProfile() {
		return klabUserManager.getLoggedInUserProfile().getSafeProfile();
	}
}