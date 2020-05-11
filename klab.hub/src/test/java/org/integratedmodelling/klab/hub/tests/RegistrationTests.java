package org.integratedmodelling.klab.hub.tests;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.emails.services.EmailManager;
import org.integratedmodelling.klab.hub.payload.SignupRequest;
import org.integratedmodelling.klab.hub.users.controllers.UserRegistrationController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.Retriever;

import net.minidev.json.JSONObject;

public class RegistrationTests extends ApplicationCheck {
	
	@Autowired
	EmailManager emailManager;
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Autowired
	UserRegistrationController userRegistrationController;
	
	
	@Test
	public void testEmailRecieved() throws MessagingException {
		String body = "is there anybody out there";
		emailManager.sendFromMainEmailAddress("new.user@email.com", "Test", body);
		Retriever r = new Retriever(greenMail.getPop3());
		Message[] msgs = r.getMessages("new.user@email.com", "password");
		String recv = GreenMailUtil.getBody(msgs[0]).trim().toString();
		r.close();
		assertEquals(body, recv);
	}
	
	@Test
	public void fail_create_user_username_present() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+ port + "/hub" + API.HUB.USER_BASE;
		URI uri = new URI(baseUrl);
		String username = "srwohl";
		String email = "steven.wohl@bc3research.org";
		SignupRequest request = new SignupRequest(username,email);
		ResponseEntity<JSONObject> result = restTemplate.postForEntity(uri, request, JSONObject.class);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void fail_create_user_email_present() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+ port + "/hub" + API.HUB.USER_BASE;
		URI uri = new URI(baseUrl);
		String username = "srwohl.not.present";
		String email = "steven.wohl@bc3research.org";
		SignupRequest request = new SignupRequest(username,email);
		ResponseEntity<JSONObject> result = restTemplate.postForEntity(uri, request, JSONObject.class);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void pass_create_new_user() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+ port + "/hub" + API.HUB.USER_BASE;
		URI uri = new URI(baseUrl);
		String username = "new.user";
		String email = "new.user@email.com";
		SignupRequest request = new SignupRequest(username,email);
		ResponseEntity<JSONObject> result = restTemplate.postForEntity(uri, request, JSONObject.class);
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		Retriever r = new Retriever(greenMail.getPop3());
		Message[] msgs = r.getMessages("new.user@email.com", "password");
		String recv = GreenMailUtil.getBody(msgs[0]).trim().toString();
		r.close();
		
		Pattern URL_PATTERN = Pattern.compile("https?://[^ ]+\n");
		Matcher m = URL_PATTERN.matcher(recv);
		URL s = null;
		while (m.find()) {
		    try {
				s = new URL(m.group(0));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		
		s.getQuery().toString();
			
	}
}
