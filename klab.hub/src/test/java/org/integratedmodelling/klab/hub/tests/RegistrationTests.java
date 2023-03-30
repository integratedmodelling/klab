package org.integratedmodelling.klab.hub.tests;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.HubApplication;
import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.emails.services.EmailManager;
import org.integratedmodelling.klab.hub.enums.AgreementLevel;
import org.integratedmodelling.klab.hub.enums.AgreementType;
import org.integratedmodelling.klab.hub.payload.PasswordChangeRequest;
import org.integratedmodelling.klab.hub.payload.SignupRequest;
import org.integratedmodelling.klab.hub.users.controllers.UserRegistrationController;
import org.integratedmodelling.klab.rest.UserAuthenticationRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.icegreen.greenmail.store.FolderException;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.Retriever;

import net.minidev.json.JSONObject;

@TestPropertySource(locations="classpath:application.yml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {HubApplication.class})
@ActiveProfiles(profiles = "development")
public class RegistrationTests extends ApplicationCheck {
	
	@Autowired
	EmailManager emailManager;
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Autowired
	UserRegistrationController userRegistrationController;
	
	
	@Test
	public void testEmailRecieved() throws MessagingException, FolderException {
		String body = "is there anybody out there";
		emailManager.sendFromMainEmailAddress("new.user@email.com", "Test", body);
		Retriever r = new Retriever(greenMail.getPop3());
		Message[] msgs = r.getMessages("new.user@email.com", "password");
		String recv = GreenMailUtil.getBody(msgs[0]).trim().toString();
		greenMail.purgeEmailFromAllMailboxes();
		r.close();
		assertEquals(body, recv);
	}
	
	@Test
	public void fail_create_user_username_present() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+ port + "/hub" + API.HUB.USER_BASE;
		URI uri = new URI(baseUrl);
		String username = "srwohl";
		String email = "steven.wohl@bc3research.org";
		String agreementLevel = "NON_PROFIT";
		String agreementType = "INSTITUTION";		
		SignupRequest request = new SignupRequest(username,email, agreementLevel, agreementType);
		ResponseEntity<JSONObject> result = restTemplate.postForEntity(uri, request, JSONObject.class);
		assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
	}
	
	@Test
	public void fail_create_user_email_present() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+ port + "/hub" + API.HUB.USER_BASE;
		URI uri = new URI(baseUrl);
		String username = "srwohl.not.present";
		String email = "steven.wohl@bc3research.org";
		String agreementLevel = "PROFIT";
        String agreementType = "INSTITUTION";       
        SignupRequest request = new SignupRequest(username,email, agreementLevel, agreementType);
		ResponseEntity<JSONObject> result = restTemplate.postForEntity(uri, request, JSONObject.class);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void pass_create_new_user() throws URISyntaxException, MalformedURLException, FolderException {
		final String baseUrl = "http://localhost:"+ port + "/hub" + API.HUB.USER_BASE;
		URI uri = new URI(baseUrl);
		String username = "new.user";
		String email = "new.user@email.com";
		String agreementLevel = "NON_PROFIT";
        String agreementType = "USER";       
        SignupRequest request = new SignupRequest(username,email, agreementLevel, agreementType);
		ResponseEntity<JSONObject> result = restTemplate.postForEntity(uri, request, JSONObject.class);
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		Retriever r = new Retriever(greenMail.getPop3());
		Message[] msgs = r.getMessages("new.user@email.com", "password");
		String recv = GreenMailUtil.getBody(msgs[0]).trim().toString();
		r.close();
		greenMail.purgeEmailFromAllMailboxes();
		Pattern URL_PATTERN = Pattern.compile("https?://[^ ]+(.*?)\\r", Pattern.DOTALL);
		Matcher m = URL_PATTERN.matcher(recv.toString());
		
	    URL s = null; //this is a url because the string is annoying to grep i cant get the carrige return
	    // there is also the # which makes it a fragment so the parameters are lost.  fun,
		
	    while (m.find()) {
			s = new URL(m.group(0));
		}
	    List<NameValuePair> params = URLEncodedUtils.parse((s.toString().replace("#", "")), Charset.forName("UTF-8"));
	    
	    
	    URI verify = new URIBuilder(baseUrl.concat("/" + params.get(0).getValue()))
	          .addParameter(API.HUB.PARAMETERS.USER_VERIFICATION, params.get(1).getValue()).build();
	    
	    ResponseEntity<JSONObject> verified = restTemplate.postForEntity(verify, null, JSONObject.class);
	    
	    String newPasswordToken = verified.getBody().getAsString("clickback");
	    
	    URI newPassword = new URIBuilder(baseUrl.concat("/" + params.get(0).getValue()))
		          .addParameter(API.HUB.PARAMETERS.USER_SET_PASSWORD, newPasswordToken).build();
	    
	    PasswordChangeRequest passwordRequest = new PasswordChangeRequest();
	    passwordRequest.setConfirm("password");
	    passwordRequest.setNewPassword("password");
	    
	    ResponseEntity<JSONObject> newPasswordrequest = restTemplate.postForEntity(newPassword, passwordRequest, JSONObject.class);
	    
	    ResponseEntity<JSONObject> loginResult = loginRsponse(username, "password");
	    
	    assertEquals(HttpStatus.OK, loginResult.getStatusCode());
	}
	
	@Test
	public void fail_lost_passwordd_token_() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+ port + "/hub" + API.HUB.USER_BASE;
		URI uri = new URI(baseUrl);
		String username = "this.is.not.there";
	    URI newPassword = new URIBuilder(baseUrl.concat("/" + username))
	    		.addParameter(API.HUB.PARAMETERS.USER_LOST_PASSWORD, "")
	    		.build();
	    ResponseEntity<JSONObject> result = restTemplate.postForEntity(newPassword, null, JSONObject.class);
	    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
	}
	
	@Test
	public void pass_lost_passwordd_token() throws URISyntaxException, FolderException, MalformedURLException {
		final String baseUrl = "http://localhost:"+ port + "/hub" + API.HUB.USER_BASE;
		URI uri = new URI(baseUrl);
		String username = "hades";
	    URI newPassword = new URIBuilder(baseUrl.concat("/" + username))
	    		.addParameter(API.HUB.PARAMETERS.USER_LOST_PASSWORD, "")
	    		.build();
	    ResponseEntity<JSONObject> result = restTemplate.postForEntity(newPassword, null, JSONObject.class);
	    assertEquals(HttpStatus.CREATED, result.getStatusCode());
	    
		Retriever r = new Retriever(greenMail.getPop3());
		Message[] msgs = r.getMessages("hades@integratedmodelling.org", "password");
		String recv = GreenMailUtil.getBody(msgs[0]).trim().toString();
		r.close();
		greenMail.purgeEmailFromAllMailboxes();
		Pattern URL_PATTERN = Pattern.compile("https?://[^ ]+(.*?)\\r", Pattern.DOTALL);
		Matcher m = URL_PATTERN.matcher(recv.toString());
		
	    URL s = null; //this is a url because the string is annoying to grep i cant get the carrige return
	    // there is also the # which makes it a fragment so the parameters are lost.  fun,
		
	    while (m.find()) {
			s = new URL(m.group(0));
		}
	    List<NameValuePair> params = URLEncodedUtils.parse((s.toString().replace("#", "")), Charset.forName("UTF-8"));
	    
	    URI setNewPassword = new URIBuilder(baseUrl.concat("/" + params.get(0).getValue()))
		          .addParameter(API.HUB.PARAMETERS.USER_SET_PASSWORD, params.get(1).getValue()).build();
	    PasswordChangeRequest passwordRequest = new PasswordChangeRequest();
	    passwordRequest.setConfirm("password2");
	    passwordRequest.setNewPassword("password2");
	    
	    ResponseEntity<JSONObject> newPasswordrequest = restTemplate.postForEntity(setNewPassword, passwordRequest, JSONObject.class);
	    
	    ResponseEntity<JSONObject> loginResultNew = loginRsponse(username, "password2");
	    ResponseEntity<JSONObject> loginResultOld = loginRsponse(username, "password");
	    
	    assertEquals(HttpStatus.OK, loginResultNew.getStatusCode());
	    assertEquals(HttpStatus.UNAUTHORIZED, loginResultOld.getStatusCode());
	    
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
