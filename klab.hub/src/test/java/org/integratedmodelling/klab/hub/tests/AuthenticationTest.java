package org.integratedmodelling.klab.hub.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.URISyntaxException;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.hub.exception.LoginFailedExcepetion;
import org.integratedmodelling.klab.hub.users.controllers.UserAuthenticationController;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.UserAuthenticationRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import junit.framework.Assert;
import net.minidev.json.JSONObject;

@SuppressWarnings("deprecation")
public class AuthenticationTest extends ApplicationCheck {
	
	@Autowired
	UserAuthenticationController userAuthController;
	
	@Autowired
	TestRestTemplate restTemplate;
	
	
	@Test
	public void contextLoads() {
		assertThat(userAuthController).isNotNull();
	}
	
	@Test
	public void fail_log_in_no_user() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+ port + "/hub" + API.HUB.AUTHENTICATE_USER;
		URI uri = new URI(baseUrl);
		UserAuthenticationRequest request = new UserAuthenticationRequest();
		request.setUsername("notRealUser");
		request.setPassword("password");
		ResponseEntity<JSONObject> result = restTemplate.postForEntity(uri, request, JSONObject.class);
		Assert.assertEquals(LoginFailedExcepetion.class.getSimpleName(), result.getBody().get("error"));
		Assert.assertEquals(401, result.getStatusCodeValue());
	}
	
	@Test
	public void pass_log_in_system_user() throws URISyntaxException {
		ResponseEntity<JSONObject> response = loginRsponse("system", "password");
		Assert.assertNotNull(response.getHeaders().get("Authentication").get(0));
		Assert.assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void fail_get_users_protected_endpoint() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+ port + "/hub" + API.HUB.USER_BASE;
		URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authentication", null);
        HttpEntity<?> request = new HttpEntity<>(null, headers);
		ResponseEntity<JSONObject> result = restTemplate.exchange(uri, HttpMethod.GET, request, JSONObject.class);
		Assert.assertEquals(401, result.getStatusCodeValue());
	}
	
	@Test
	public void pass_get_users_protected_endpoint() throws URISyntaxException {
		ResponseEntity<JSONObject> login = loginRsponse("system", "password");
		final String baseUrl = "http://localhost:"+ port + "/hub" + API.HUB.USER_BASE;
		URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authentication", login.getHeaders().get("Authentication").get(0));
        HttpEntity<?> request = new HttpEntity<>(null, headers);
		ResponseEntity<JSONObject> result = restTemplate.exchange(uri, HttpMethod.GET, request, JSONObject.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertTrue(result.getBody().containsKey("profiles"));
	}
	
	@Test
	public void pass_get_user_engine_certificate() throws URISyntaxException {
		String username = "hades";
		ResponseEntity<JSONObject> login = loginRsponse(username, "password");
		final String baseUrl = "http://localhost:"+ port + "/hub" + API.HUB.USER_BASE + "/" + username + "?certificate";
		URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authentication", login.getHeaders().get("Authentication").get(0));
        HttpEntity<?> request = new HttpEntity<>(null, headers);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
		KlabCertificate cert = new KlabCertificate(result.getBody());
		cert.isValid();
		EngineAuthenticationRequest engineRequest = new EngineAuthenticationRequest();
	}

	private ResponseEntity<JSONObject> loginRsponse(String username, String password) throws URISyntaxException {
		final String baseUrl = "http://localhost:"+ port + "/hub" + API.HUB.AUTHENTICATE_USER;
		URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        UserAuthenticationRequest auth= new UserAuthenticationRequest();
        auth.setPassword(password);
        auth.setUsername(username);
        HttpEntity<?> request = new HttpEntity<>(auth, headers);
		ResponseEntity<JSONObject> result = restTemplate.postForEntity(uri, request, JSONObject.class);
		return result;
	}
}
