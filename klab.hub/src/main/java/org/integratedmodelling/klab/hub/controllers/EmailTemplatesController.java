package org.integratedmodelling.klab.hub.controllers;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import org.integratedmodelling.klab.hub.config.EmailConfig;
import org.integratedmodelling.klab.hub.manager.KlabUserManager;
import org.integratedmodelling.klab.hub.models.EmailTemplate;
import org.integratedmodelling.klab.hub.models.User;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.service.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import net.minidev.json.JSONObject;


@RestController
@RequestMapping("/api/templates/emails")
@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
public class EmailTemplatesController {

	@Autowired
	EmailTemplateService emailTemplateService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	KlabUserManager klabUserManager;
	
	@Autowired
    private EmailConfig emailConfig;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<Object> getEmailTemplates() {
		return new ResponseEntity<>(emailTemplateService.getEmailTemplates(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET, params="senders")
	public ResponseEntity<Object> getAuthorizedSenders() {
		return new ResponseEntity<>(emailConfig.getAuthorizedEmailAddresses(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET, params="names")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Object> getEmailTemplateNames() {
		JSONObject resp = new JSONObject();
		resp.appendField("EmailTemplate", emailTemplateService.getEmailTemplateNames());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getEmailTemplate(@PathVariable("id") String id) {
		EmailTemplate emailTemplate = emailTemplateService.getEmailTemplate(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
		return new ResponseEntity<>(emailTemplate, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET, params="name")
	public ResponseEntity<Object> getEmailTemplateByName(@PathVariable("name") String name) {
		Collection<EmailTemplate> emailTemplate = emailTemplateService.getEmailTemplatesByName(name);
		return new ResponseEntity<>(emailTemplate, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET, params="author")
	public ResponseEntity<Object> getEmailTemplateByAuthor(@PathVariable("author") String id) {
		EmailTemplate emailTemplate = emailTemplateService.getEmailTemplate(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
		return new ResponseEntity<>(emailTemplate, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> createEmailTemplate(@RequestBody EmailTemplate emailTemplate) {
		checkUserName(emailTemplate);
		try {
			emailTemplateService.createEmailTemplate(emailTemplate);
		} catch (DuplicateKeyException dke) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "User and email exists");
		}
		return new ResponseEntity<>(emailTemplate, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateGroup(@PathVariable("id") String id, @RequestBody EmailTemplate emailTemplate) {
		checkUserName(emailTemplate);
		emailTemplateService.updateEmailTemplate(id, emailTemplate);
		return new ResponseEntity<>("The template has been updated successfully", HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable("id") String id) {
		emailTemplateService.deleteEmailTemplate(id);
		return new ResponseEntity<>("The template has been deleted successfully", HttpStatus.OK);
	}
	
	private void checkUserName(EmailTemplate emailTemplate) {
		if (emailTemplate.getAuthorUsername() == null) {
			emailTemplate.setAuthorUsername(klabUserManager.getLoggedInUsername());
		} else {
			Optional<User> user = userRepository.findByUsername(emailTemplate.getAuthorUsername());
			if (!user.isPresent()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
			}
		}
	}
}
