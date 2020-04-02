package org.integratedmodelling.klab.hub.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.hub.exception.LoginFailedExcepetion;
import org.integratedmodelling.klab.hub.payload.SignupRequest;
import org.integratedmodelling.klab.hub.users.controllers.UserAuthenticationController;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.integratedmodelling.klab.rest.UserAuthenticationRequest;
import org.integratedmodelling.klab.utils.NameGenerator;
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
	public void fail_create_user_username_present() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+ port + "/hub" + API.HUB.USER_BASE;
		URI uri = new URI(baseUrl);
		String username = "srwohl";
		String password = "password";
		SignupRequest request = new SignupRequest(username,password);
		ResponseEntity<JSONObject> result = restTemplate.postForEntity(uri, request, JSONObject.class);
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
	public void fail_protected_endpoint_no_token() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+ port + "/hub" + API.HUB.USER_BASE;
		URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<>(null, headers);
		ResponseEntity<JSONObject> result = restTemplate.exchange(uri, HttpMethod.GET, request, JSONObject.class);
		Assert.assertEquals(401, result.getStatusCodeValue());
	}
	
	@Test
	public void fail_users_admin_protected_endpoint() throws URISyntaxException {
		ResponseEntity<JSONObject> login = loginRsponse("srwohl", "password");
		final String baseUrl = "http://localhost:"+ port + "/hub" + API.HUB.USER_BASE;
		URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authentication", login.getHeaders().get("Authentication").get(0));
        HttpEntity<?> request = new HttpEntity<>(null, headers);
		ResponseEntity<JSONObject> result = restTemplate.exchange(uri, HttpMethod.GET, request, JSONObject.class);
		Assert.assertEquals(403, result.getStatusCodeValue());
	}
	
	@Test
	public void pass_users_admin_protected_endpoint() throws URISyntaxException {
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
	public void pass_get_user_engine_certificate() throws URISyntaxException, IOException {
		String username = "hades";
		ResponseEntity<JSONObject> login = loginRsponse(username, "password");
		final String baseUrl = "http://localhost:"+ port + "/hub" + API.HUB.USER_BASE + "/" + username + "?certificate";
		URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authentication", login.getHeaders().get("Authentication").get(0));
        HttpEntity<?> request = new HttpEntity<>(null, headers);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
		ICertificate cert = certStringAdaptor(result.getBody());
		EngineAuthenticationRequest engineRequest = new EngineAuthenticationRequest(
				cert.getProperty(KlabCertificate.KEY_USERNAME),
				cert.getProperty(KlabCertificate.KEY_SIGNATURE),
				cert.getProperty(KlabCertificate.KEY_CERTIFICATE_TYPE),
				cert.getProperty(KlabCertificate.KEY_CERTIFICATE), cert.getLevel());
		engineRequest.setEmail(cert.getProperty(KlabCertificate.KEY_USERNAME));
        headers.clear();
        headers.add("TEST", "false");
        HttpEntity<?> authRequestEntity = new HttpEntity<>(engineRequest, headers);
		final String authUrl = "http://localhost:"+ port + "/hub" + API.HUB.AUTHENTICATE_ENGINE;
		URI authUri = new URI(authUrl);
		ResponseEntity<EngineAuthenticationResponse> engineAuth = 
				restTemplate.exchange(authUri, HttpMethod.POST, authRequestEntity, EngineAuthenticationResponse.class);
		Assert.assertEquals(200, engineAuth.getStatusCodeValue());
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
	
	private ICertificate certStringAdaptor(String certString) throws IOException {
		File temp = File.createTempFile(NameGenerator.newName(), ".cert");
		BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
		ICertificate cert = KlabCertificate.createFromFile(temp);
		bw.write(certString);
		bw.close();
		cert.isValid();
		temp.delete();
		return cert;
	}
}
