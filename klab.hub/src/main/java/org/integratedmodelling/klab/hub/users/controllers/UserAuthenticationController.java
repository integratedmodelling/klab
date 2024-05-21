package org.integratedmodelling.klab.hub.users.controllers;

import org.integratedmodelling.klab.api.API;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthenticationController {

//	private UserAuthTokenService userAuthService;
//	
//	@Autowired
//	UserAuthenticationController(UserAuthTokenService userAuthService) {
//		this.userAuthService = userAuthService;
//	}

//	@PostMapping(API.HUB.AUTHENTICATE_USER)
//	public ResponseEntity<?> loginResponse(@RequestBody UserAuthenticationRequest request) {
//		LoginResponse response = userAuthService.getAuthResponse(request.getUsername(), request.getPassword());
//		return response.getResponse();
//	}

    @PostMapping(API.HUB.AUTHENTICATE_USER)
    public ResponseEntity< ? > loginResponse() {
        System.out.println("holis");
//        LoginResponse response = userAuthService.getAuthResponse("", "");
//        return response.getResponse();
//	    Principal hola = request.getUserPrincipal();
//	    KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();        
//        KeycloakPrincipal principal=(KeycloakPrincipal)token.getPrincipal();
//        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
//        AccessToken accessToken = session.getToken();
//        String username = accessToken.getPreferredUsername();
//        String emailID = accessToken.getEmail();
//        String lastname = accessToken.getFamilyName();
//        String firstname = accessToken.getGivenName();
//        String realmName = accessToken.getIssuer();            
//        Access realmAccess = accessToken.getRealmAccess();
//        Set<String> roles = realmAccess.getRoles();
//        LoginResponse response = userAuthService.getAuthResponse(username, accessToken.getAccessTokenHash());
//        JSONObject resp = new JSONObject();
//        ProfileResource profile = new ProfileResource();
//        profile.setUsername("user_id");
//        
//        resp.appendField("Profile", profile.getSafeProfile());
//        resp.appendField("Authentication", );
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authentication", );
//        return new ResponseEntity<JSONObject>(resp, headers, HttpStatus.OK);
//        return response.getResponse();
        return new ResponseEntity<>("hemos entrado con el token", HttpStatus.OK);
    }

    @PostMapping(API.HUB.AUTHENTICATE_USER + "2")
    public ResponseEntity< ? > loginResponse2() {
        System.out.println("holis");
//        LoginResponse response = userAuthService.getAuthResponse("", "");
//        return response.getResponse();
        return new ResponseEntity<>("hemos entrado con el token", HttpStatus.OK);
    }

//	@PostMapping(API.HUB.DEAUTHENTICATE_USER)
//	public ResponseEntity<?> logoutResponse(@RequestHeader("Authentication") String token) {
//		LogoutResponse response = userAuthService.getLogoutResponse(token);
//		return response.getResponse();
//	}

}
