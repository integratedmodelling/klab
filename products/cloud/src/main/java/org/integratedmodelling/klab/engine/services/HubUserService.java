package org.integratedmodelling.klab.engine.services;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.ISessionState;
import org.integratedmodelling.klab.auth.EngineUser;
import org.integratedmodelling.klab.auth.KlabUser;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.api.HubLoginResponse;
import org.integratedmodelling.klab.engine.api.RemoteUserLoginResponse;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.RemoteUserAuthenticationRequest;
import org.integratedmodelling.klab.rest.ScaleReference;
import org.integratedmodelling.klab.rest.SessionActivity;
import org.integratedmodelling.klab.rest.UserAuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * This hub service is used to authenticate a user request to login to an engine
 * that they are not the owner of. The authentication is completed via the
 * partner hub url located in the engine certificate. If successful it should
 * create a session and return a response entity with a relative url for the
 * seesion.
 * 
 * @author steve
 *
 */
@Service
@EnableAsync
public class HubUserService implements RemoteUserService {

	@Autowired
	RestTemplate restTemplate;

	/*
	 * Generates a response entity a url to the session generated after succesful
	 * authentication.
	 */
	@Override
	public ResponseEntity<?> login(RemoteUserAuthenticationRequest login) {
		ResponseEntity<HubLoginResponse> result = null;
		if (!"".equals(login.getUsername()) && !"".equals(login.getPassword())) {
		    login.setJwtToken(true);
			try {
				result = hubLogin(login);
			} catch (HttpClientErrorException e) {
				if (e.getRawStatusCode() == 401) {
					return ResponseEntity.status(HttpStatus.FORBIDDEN)
							.body("Failed to login user: " + login.getUsername());
				}
				throw new KlabAuthorizationException("Failed to login user: " + login.getUsername());
			}
			if (result != null && result.getStatusCode().is2xxSuccessful()) {
				HubUserProfile profile = result.getBody().getProfile();
				String jwtToken = result.getBody().getJwtToken();
				Session session;
				if (checkForActiveSessions(profile)) {
				    session = getActiveSessionResponse(profile);
				} else {
				    session = processProfile(profile, jwtToken);
				}
				RemoteUserLoginResponse response = getRemoteUserLoginResponse(session);
				response.setAuthorization(result.getBody().getAuthentication().getTokenString());
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
			} else {
	            throw new KlabAuthorizationException("Failed to login user: " + login.getUsername());
			}
		} else {
			return login(login.getToken());
		}
	}

	@Override
	public ResponseEntity<?> login(String token) {
		ResponseEntity<HubUserProfile> result;

		try {
			result = hubToken(token);
		} catch (HttpClientErrorException e) {
			if (e.getRawStatusCode() == 401) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to login with token");
			}
			throw new KlabAuthorizationException("Failed to login with token");
		}

		if (result != null && result.getStatusCode().is2xxSuccessful()) {
			

			HubUserProfile profile = result.getBody();
			Session session;
			if (checkForActiveSessions(profile)) {
			    session = getActiveSessionResponse(profile);
			} else {
			    session = processProfile(profile, profile.getJwtToken());
			}
			RemoteUserLoginResponse response = getRemoteUserLoginResponse(session);
			response.setAuthorization(token);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
		} else {
	        throw new KlabAuthorizationException("Failed to login via token");
		}
	}

	@Override
	public ResponseEntity<?> logout(String token) {
		ResponseEntity<HubUserProfile> result;
		try {
			result = hubToken(token);
		} catch (HttpClientErrorException e) {
			if (e.getRawStatusCode() == 401) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to logout with token");
			}
			throw new KlabAuthorizationException("Failed to logout with token");
		}

		if (result != null && result.getStatusCode().is2xxSuccessful()) {
			HubUserProfile profile = result.getBody();

			if (checkForActiveSessions(profile)) {
				Session session = activeSessions(profile).iterator().next();
				try {
					session.close();
				} catch (IOException e) {
					throw new KlabException("Could not close the session :(");
				}
			}
			hubLogout(token);
			return ResponseEntity.status(HttpStatus.RESET_CONTENT).build();
		} else {
			throw new KlabAuthorizationException("Failed to logout via token");
		}

	}
	
	private RemoteUserLoginResponse getRemoteUserLoginResponse(Session session) {
	    RemoteUserLoginResponse response = new RemoteUserLoginResponse();
        response.setPublicApps(session.getSessionReference().getPublicApps());
        response.setRedirect("/modeler/ui/viewer?session=" + session.getId());
        response.setSession(session.getId());
        return response;
	}

	private Session getActiveSessionResponse(HubUserProfile profile) {
		return activeSessions(profile).iterator().next();
	}

	private Session processProfile(HubUserProfile profile, String jwtToken) {
		List<String> roles = profile.getRoles();
		List<GrantedAuthority> authorities = getAuthorities(roles);
		List<Group> groups = new ArrayList<>();

		profile.getGroupEntries().forEach(e -> {
			Group group = new Group();
			group.setId(e.getGroup().getId());
			group.setIconUrl(e.getGroup().getIconUrl());
			group.setObservables(e.getGroup().getObservables());
			group.setDescription(e.getGroup().getDescription());
			groups.add(group);
		});

		String token = jwtToken;
		KlabUser user = new KlabUser(profile.getName(), token, authorities);
		user.setEmailAddress(profile.getEmail());
		user.getGroups().addAll(groups);

		Session session = getSession(user);

		session.getState().addListener(new ISessionState.Listener() {

			@Override
			public void historyChanged(SessionActivity rootActivity, SessionActivity currentActivity) {
				session.touch();
			}

			@Override
			public void scaleChanged(ScaleReference scale) {
				session.touch();
			}

			@Override
			public void newContext(ISubject context) {
				session.touch();
			}

			@Override
			public void newObservation(IObservation observation, ISubject context) {
				session.touch();
			}

		});

		return session;
	}

	private Session getSession(KlabUser user) {
		Engine engine = Authentication.INSTANCE.getAuthenticatedIdentity(Engine.class);
		// TODO: Check why this
		// user.getGroups().clear();
		// this is only so we can get observables right now;
		// user.getGroups().addAll(engine.getDefaultEngineUser().getGroups());
		Authentication.INSTANCE.registerIdentity(user);
		EngineUser remoteEngine = new EngineUser(user, engine);
		return engine.createSession(remoteEngine);

	}

	private List<GrantedAuthority> getAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String role : roles) {

			if (role.equals("ROLE_ADMINSTRATOR")) {
				authorities.add(new SimpleGrantedAuthority(Roles.ADMIN));
			}

			if (role.equals("ROLE_USER")) {
				authorities.add(new SimpleGrantedAuthority(Roles.ENGINE_USER));
			}

		}

		if (authorities.isEmpty()) {
			throw new KlabAuthorizationException("User does not have the authority to create session.");
		}

		return authorities;
	}

	private boolean checkForActiveSessions(HubUserProfile profile) {
		return !activeSessions(profile).isEmpty();
	}

	private Set<Session> activeSessions(HubUserProfile profile) {
		Set<Session> sessions = new HashSet<>();
		Authentication.INSTANCE.getSessions().forEach(s -> {
			if (s.getParentIdentity().getUsername().equals(profile.getName())) {
				Session sesh = Authentication.INSTANCE.getIdentity(s.getId(), Session.class);
				if (sesh.isEnabled()) {
					sessions.add(sesh);
				}
			}
		});
		return sessions;
	}

	private ResponseEntity<HubLoginResponse> hubLogin(UserAuthenticationRequest login) {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> request = new HttpEntity<>(login, headers);
		return restTemplate.postForEntity(getLoginUrl(), request, HubLoginResponse.class);
	}

	private URI hubLogout(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authentication", token);
		HttpEntity<?> request = new HttpEntity<>(headers);
		return restTemplate.postForLocation(getLogOutUrll(), request);
	}

	private ResponseEntity<HubUserProfile> hubToken(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authentication", token);
		HttpEntity<?> request = new HttpEntity<>(headers);
		ResponseEntity<HubUserProfile> response = restTemplate.exchange(getProfileUrl(), HttpMethod.GET, request,
				HubUserProfile.class);
		return response;
	}

	private String getLoginUrl() {
		return Network.INSTANCE.getHub().getUrls().iterator().next() + API.HUB.AUTHENTICATE_USER;
	}

	private String getLogOutUrll() {
		return Network.INSTANCE.getHub().getUrls().iterator().next() + API.HUB.DEAUTHENTICATE_USER;
	}

	private String getProfileUrl() {
		return Network.INSTANCE.getHub().getUrls().iterator().next() + API.HUB.CURRENT_PROFILE;
	}

}
