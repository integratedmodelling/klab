package org.integratedmodelling.klab.hub.users.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.apache.commons.lang3.tuple.Triple;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.GroupEntry;
import org.integratedmodelling.klab.hub.api.JwtToken;
import org.integratedmodelling.klab.hub.api.ProfileResource;
import org.integratedmodelling.klab.hub.api.TokenType;
import org.integratedmodelling.klab.hub.api.TokenVerifyEmailClickback;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.controllers.dto.FilterCondition;
import org.integratedmodelling.klab.hub.controllers.pagination.GenericPageAndFilterConverter;
import org.integratedmodelling.klab.hub.exception.ActivationTokenFailedException;
import org.integratedmodelling.klab.hub.payload.EngineProfileResource;
import org.integratedmodelling.klab.hub.payload.PageRequest;
import org.integratedmodelling.klab.hub.payload.PageResponse;
import org.integratedmodelling.klab.hub.payload.PasswordChangeRequest;
import org.integratedmodelling.klab.hub.payload.UpdateEmailResponse;
import org.integratedmodelling.klab.hub.payload.UpdateUserRequest;
import org.integratedmodelling.klab.hub.service.FilterBuilderService;
import org.integratedmodelling.klab.hub.tokens.services.RegistrationTokenService;
import org.integratedmodelling.klab.hub.users.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
public class UserProfileController {
	
	private UserProfileService userService;
    private final GenericPageAndFilterConverter genericPageAndFilterConverter;
    private final FilterBuilderService filterBuilderService;
	private RegistrationTokenService tokenService;
	
	private static final JwtToken JWT_TOKEN_FACTORY = new JwtToken();
	private static final String FILTER_NO_GROUPS = "$NO_GROUPS$";
	
	@Autowired
	UserProfileController(UserProfileService userService, GenericPageAndFilterConverter genericPageAndFilterConverter,
			FilterBuilderService filterBuilderService, RegistrationTokenService tokenService) {
		this.userService = userService;
		this.genericPageAndFilterConverter = genericPageAndFilterConverter;
		this.filterBuilderService = filterBuilderService;
		this.tokenService = tokenService;
	}
	
	@SuppressWarnings("unlikely-arg-type")
    @GetMapping(API.HUB.USER_BASE)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> getAllUserProfiles(PageRequest pageRequest) {
	    PageResponse<User> response = new PageResponse<>();
        
        /* Call function to convert pageRequest object in <Query, Pageable> pair, where query has the filters and pageable, the pagination properties*/
        Triple<Query, List<FilterCondition>, List<FilterCondition>> triple = genericPageAndFilterConverter.genericFilterConvert(pageRequest, "groups");
        Pageable pageable = filterBuilderService.getPageable(pageRequest.getSize(), pageRequest.getPage(), pageRequest.getOrders());
        if (triple.getMiddle().isEmpty() && triple.getRight().isEmpty()) {
            /* Call getPage function, to findAll elements applying the filters and the pagination given in the pageRequest*/
            Page<User> pg = userService.getPage(triple.getLeft(), pageable);
            response.setPageStats(pg);
        } else {
            List<User> users = userService.getQuery(triple.getLeft());
            ArrayList<String> groupsSearchAnd = triple.getMiddle().stream().map(f -> f.getValue().toString()).collect(Collectors.toCollection(ArrayList<String>::new));
            ArrayList<String> groupsSearchOr = triple.getRight().stream().map(f -> f.getValue().toString()).collect(Collectors.toCollection(ArrayList<String>::new));
            
            if (groupsSearchAnd.contains(FILTER_NO_GROUPS) || groupsSearchOr.contains(FILTER_NO_GROUPS)) {
                users = users.stream().filter(user -> {
                    return user.getAgreements().stream().findFirst().get().getAgreement().getGroupEntries().isEmpty();
                }).collect(Collectors.toCollection(ArrayList<User>::new));
            } else {
                if (!triple.getMiddle().isEmpty()) {
                    users = users.stream().filter(user -> {
                        return user.getAgreements().stream().findFirst().get().getAgreement().getGroupEntries().stream()
                                .map(GroupEntry::getGroupName).collect(Collectors.toCollection(ArrayList<String>::new)).containsAll(groupsSearchAnd);
                    }).collect(Collectors.toCollection(ArrayList<User>::new));
                }
                if (!triple.getRight().isEmpty()) {
                    users = users.stream().filter(user -> {
                        return user.getAgreements().stream().findFirst().get().getAgreement().getGroupEntries().stream()
                                .map(GroupEntry::getGroupName).anyMatch(groupsSearchOr::contains);
                    }).collect(Collectors.toCollection(ArrayList<User>::new));
                }
            }
            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), users.size());
            final List<User> content = users.subList(start, end);
            final Page<User> page = new PageImpl<>(content, pageable, users.size());
            response.setPageStats(page);
        }
        
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping(API.HUB.USER_BASE_ID)
	@PreAuthorize("authentication.getPrincipal() == #id or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> getUserProfile(@PathVariable String id) {
		ProfileResource profile = userService.getUserProfile(id);
		return new ResponseEntity<>(profile,HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value=API.HUB.USER_BASE_ID_NOAUTH, params = API.HUB.PARAMETERS.USER_GET)
	public ResponseEntity<?> getUserDataByToken(@PathVariable String id, @RequestParam(API.HUB.PARAMETERS.USER_GET) String setEmail) {
		TokenVerifyEmailClickback token = (TokenVerifyEmailClickback) tokenService.getAndVerifyToken(id, setEmail, TokenType.verifyEmail);
		if (token == null) {
			throw new ActivationTokenFailedException("User Verification token failed");
		}
		ProfileResource profile = userService.getUserProfile(id);
		UpdateEmailResponse response = new UpdateEmailResponse(profile.getEmail(), token.getNotVerifiedEmail());
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping(API.HUB.CURRENT_PROFILE)
	// TODO this is call from single user, not need PreAuthorize
	// @PreAuthorize("authentication.getPrincipal() == #username or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	//correct the auth should be caught on the token filter side.
	public ResponseEntity<?> getCurrentUserProfile(@RequestParam(required = false) boolean remote) {
		ProfileResource profile = userService.getCurrentUserProfile(remote);
		if (remote) {
            profile.setJwtToken(JWT_TOKEN_FACTORY.createEngineJwtToken(profile));
            return new ResponseEntity<>(new EngineProfileResource(profile), HttpStatus.ACCEPTED); 
        }
		return new ResponseEntity<>(profile,HttpStatus.ACCEPTED);
	}

	@PutMapping(API.HUB.USER_BASE_ID)
	@PreAuthorize("authentication.getPrincipal() == #id" )
	public ResponseEntity<?> updateUserProfile(@PathVariable String id, @RequestBody UpdateUserRequest updateRequest) {
		ProfileResource profile = userService.updateUserByProfile(updateRequest.getProfile());
		return new ResponseEntity<>(profile,HttpStatus.ACCEPTED);
	}
	
	@PostMapping(value = API.HUB.USER_BASE_ID, params = API.HUB.PARAMETERS.USER_REQUEST_EMAIL)
	@PreAuthorize("authentication.getPrincipal() == #id")
	public ResponseEntity<?> requestNewUserEmail(@PathVariable String id, @RequestParam(API.HUB.PARAMETERS.USER_REQUEST_EMAIL) String requestNewEmail) {		
		ProfileResource profile;
		try {
			profile = userService.createNewEmailRequest(id, requestNewEmail);
		} catch (MessagingException e) {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(e.getMessage());
		}
		return new ResponseEntity<>(profile, HttpStatus.ACCEPTED);

	}
	
//	@PostMapping(value=API.HUB.USER_BASE_ID, params = API.HUB.PARAMETERS.USER_SET_PASSWORD)
//	public ResponseEntity<?> updateUserEmail(@PathVariable String id, @RequestParam(API.HUB.PARAMETERS.USER_SET_PASSWORD) String setPassword,
//			@RequestBody PasswordChangeRequest passwordRequest) {
//		TokenType[] types = { TokenType.newUser, TokenType.password, TokenType.lostPassword };
//		if (!tokenService.verifyTokens(id, setPassword, types)) {
//			throw new ActivationTokenFailedException("User Verification token failed");
//		}
//		//User user = userService.setPassword(id, passwordRequest.getNewPassword(), passwordRequest.getConfirm());
//		if(user != null) {
//			tokenService.deleteToken(setPassword);
//		}
//		JSONObject resp = new JSONObject();
//		resp.appendField("Message", "User password updated");
//		return new ResponseEntity<JSONObject>(resp,HttpStatus.CREATED);
//	}

	@GetMapping(value = API.HUB.USER_BASE_ID, params = "remote-login")
	@PreAuthorize("authentication.getPrincipal() == #id")
	public ResponseEntity<?> getFullUserProfile(@PathVariable String id) {
		ProfileResource profile = userService.getRawUserProfile(id);
		return new ResponseEntity<>(profile,HttpStatus.ACCEPTED);
	}
	
}
