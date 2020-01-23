package org.integratedmodelling.klab.hub.service;

import java.util.Collection;
import java.util.Optional;

import org.integratedmodelling.klab.hub.emails.EmailTemplate;

/**
 * The email template service
 * @author Enrico Girotto
 *
 */
public interface EmailTemplateService {
	
	/**
	 * Create a new email template
	 * @param emailTemplate the new template
	 */
	public void createEmailTemplate(EmailTemplate emailTemplate);
	
	/**
	 * Update an email template
	 * @param id the id of email template to update
	 * @param emailTemplate new attributes
	 */
	public void updateEmailTemplate(String id, EmailTemplate emailTemplate);
	
	/**
	 * Delete an email template
	 * @param id the id of email template to delete
	 */
	public void deleteEmailTemplate(String id);
	
	/**
	 * Return all the existing email templates
	 * @return collection of email templates
	 */
	public Collection<EmailTemplate> getEmailTemplates();
	
	/**
	 * Return a specific email template
	 * @param id the id of email template
	 * @return the specific email template
	 */
	public Optional<EmailTemplate> getEmailTemplate(String id);
	
	/**
	 * Return an email template that CONTAINS the given string
	 * @param name string with all or part of the template name
	 * @return a collection with all the template names that match the search
	 */
	public Collection<EmailTemplate> getEmailTemplatesByName(String name);
	
	/**
	 * Return all the templates made by an author
	 * @param username the username of author
	 * @return a collection of email templates with 0 or more elements
	 */
	public Collection<EmailTemplate> getEmailTemplatesByAuthorUsername(String username);
	
	/**
	 * Return all the names of actual email templates
	 * @return
	 */
	public Collection<String> getEmailTemplateNames();
}
