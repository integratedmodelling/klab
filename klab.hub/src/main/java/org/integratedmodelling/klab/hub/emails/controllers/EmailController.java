package org.integratedmodelling.klab.hub.emails.controllers;

import java.util.Arrays;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.emails.EmailConfig;
import org.integratedmodelling.klab.hub.emails.EmailConfig.EmailType;
import org.integratedmodelling.klab.hub.emails.dto.EmailTemplate;
import org.integratedmodelling.klab.hub.emails.exceptions.SendEmailException;
import org.integratedmodelling.klab.hub.emails.payload.KlabEmail;
import org.integratedmodelling.klab.hub.emails.services.EmailManager;
import org.integratedmodelling.klab.hub.emails.services.EmailTemplateService;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.hub.users.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller for email related actions
 * Include template management
 * @author Enrico Girotto
 *
 */
@RestController
@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
public class EmailController {

	@Autowired
	EmailTemplateService emailTemplateService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserProfileService profileService;
	
	@Autowired
	EmailManager emailManager;
	
	@Autowired
    private EmailConfig emailConfig;

	/**
	 * Request templates
	 * @return collection of actuals existing templates
	 */
	@RequestMapping(value = API.HUB.EMAIL_BASE, method = RequestMethod.GET, params=API.HUB.PARAMETERS.TEMPLATES)
	public ResponseEntity<Object> getEmailTemplates() {
		return new ResponseEntity<>(emailTemplateService.getAll(), HttpStatus.OK);
	}
	
	/**
	 * Request available senders coming from configuration
	 * @return an array of available senders with format "Name \<email\>"
	 */
	@RequestMapping(value = API.HUB.EMAIL_BASE, method = RequestMethod.GET, params="senders")
	public ResponseEntity<Object> getAuthorizedSenders() {
		return new ResponseEntity<>(emailConfig.getAuthorizedEmailAddresses(), HttpStatus.OK);
	}
	
	/**
	 * Return the email templates of an author
	 * @param id the author id
	 * @return a collection of email templates for this author
	 */
	@RequestMapping(value = API.HUB.EMAIL_BASE, method = RequestMethod.GET, params="author")
	public ResponseEntity<Object> getEmailTemplateByAuthor(@PathVariable("author") String id) {
		EmailTemplate emailTemplate = emailTemplateService.getById(id);
		return new ResponseEntity<>(emailTemplate, HttpStatus.OK);		
	}
	
	/**
	 * Create a template
	 * @param emailTemplate the email template
	 * @return the created email template
	 */
	@RequestMapping(value = API.HUB.EMAIL_BASE, method = RequestMethod.POST, params="createTemplate", produces = "application/json")
	public ResponseEntity<Object> createEmailTemplate(@RequestBody EmailTemplate emailTemplate) {
		checkUserName(emailTemplate);
		try {
			emailTemplateService.create(emailTemplate);
		} catch (DuplicateKeyException dke) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "User and email exists");
		}
		return new ResponseEntity<>(emailTemplate, HttpStatus.CREATED);
	}
	
	/**
	 * Update a template
	 * @param id the template id
	 * @param emailTemplate the updated template
	 * @return the updated template
	 */
	@RequestMapping(value = API.HUB.EMAIL_BASE_ID, method = RequestMethod.PUT)
	public ResponseEntity<Object> updateGroup(@PathVariable("id") String id, @RequestBody EmailTemplate emailTemplate) {
		checkUserName(emailTemplate);
		emailTemplateService.update(emailTemplate);
		return new ResponseEntity<>("The template has been updated successfully", HttpStatus.OK);
	}

	/**
	 * Delete a template
	 * @param id the id of template
	 * @return confirmation message
	 */
	@RequestMapping(value = API.HUB.EMAIL_BASE_ID, method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable("id") String id) {
		emailTemplateService.delete(id);
		return new ResponseEntity<>("The template has been deleted successfully", HttpStatus.OK);
	}
	

	/**
	 * Return an email template by id
	 * @param id the email template id
	 * @return the email template
	 */
	@RequestMapping(value = API.HUB.EMAIL_BASE_ID, method = RequestMethod.GET)
	public ResponseEntity<Object> getEmailTemplate(@PathVariable("id") String id) {
		EmailTemplate emailTemplate = emailTemplateService.getById((id));
		return new ResponseEntity<>(emailTemplate, HttpStatus.OK);		
	}
	
	private void checkUserName(EmailTemplate emailTemplate) {
		if (emailTemplate.getAuthorUsername() == null) {
			emailTemplate.setAuthorUsername(profileService.getCurrentUserProfile(false).getUsername());
		} else {
			Optional<User> user = userRepository.findByName(emailTemplate.getAuthorUsername());
			if (!user.isPresent()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
			}
		}
	}
	
	/**
	 * Send an email
	 * @param sendEmail
	 * @return confirmation or error
	 */
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	@PostMapping(value = API.HUB.EMAIL_BASE, produces = "application/json", params="sendEmail")
	public ResponseEntity<?> sendEmail(@RequestBody KlabEmail email) {
		ResponseEntity<String> response = null;
		// checks
		// normally from is ever the same and coming from configuration, so the check is not needed
		email.from = emailConfig.senderEmail();
		// if (Arrays.stream(emailConfig.getAuthorizedEmailAddresses()).anyMatch(email.from::equals)) {
		// to and replayTo are known
		for(String userEmail: email.to) {
			if (!userRepository.findByEmailIgnoreCase(userEmail).isPresent()) {
				response = ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Recipient must be known users");
				break;
			}
		}
		for(String userEmail: email.replayTo) {
			if (!userRepository.findByEmailIgnoreCase(userEmail).isPresent()
					&& !(Arrays.stream(emailConfig.getAuthorizedEmailAddresses()).anyMatch(userEmail::equals))) {
				response = ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Reply to must be known users or authorized email");
				break;
			}
		}
		/*
		} else {
			response = ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body("Unauthorized remitter");
		}
		*/
		if (response != null) {
			return response;
		}
		try {
			emailManager.send(email.from, email.to, email.replayTo, email.subject, email.content, email.type != EmailType.TEXT, email.attachments);
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Email sended");
		} catch (SendEmailException see) {
			return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(see.getMessage());
		}
	}
}
