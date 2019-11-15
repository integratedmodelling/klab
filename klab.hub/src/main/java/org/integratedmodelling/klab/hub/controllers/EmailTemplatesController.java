package org.integratedmodelling.klab.hub.controllers;

import javax.annotation.security.RolesAllowed;

import org.integratedmodelling.klab.hub.models.EmailTemplate;
import org.integratedmodelling.klab.hub.service.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
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

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<Object> getEmailTemplates() {
		return new ResponseEntity<>(emailTemplateService.getEmailTemplates(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET, params="names")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Object> getEmailTemplateNames() {
		JSONObject resp = new JSONObject();
		resp.appendField("EmailTemplate", emailTemplateService.getEmailTemplateNames());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getGroup(@PathVariable("id") String id) {
		EmailTemplate emailTemplate = emailTemplateService.getEmailTemplate(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
		return new ResponseEntity<>(emailTemplate, HttpStatus.OK);		
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateGroup(@PathVariable("id") String id, @RequestBody EmailTemplate emailTemplate) {
		emailTemplateService.updateEmailTemplate(id, emailTemplate);
		return new ResponseEntity<>("The template has been updated successfully", HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable("id") String id) {
		emailTemplateService.deleteEmailTemplate(id);
		return new ResponseEntity<>("The template has been deleted successfully", HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> createEmailTemplate(@RequestBody EmailTemplate emailTemplate) {
		emailTemplateService.createEmailTemplate(emailTemplate);
		return new ResponseEntity<>(emailTemplate, HttpStatus.CREATED);
	}
}
