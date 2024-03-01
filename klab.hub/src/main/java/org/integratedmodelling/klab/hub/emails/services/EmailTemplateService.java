package org.integratedmodelling.klab.hub.emails.services;

import java.util.Collection;
import org.integratedmodelling.klab.hub.api.EmailTemplate;
import org.integratedmodelling.klab.hub.service.GenericHubService;

/**
 * The email template service
 * @author Enrico Girotto
 *
 */
public interface EmailTemplateService extends GenericHubService<EmailTemplate>{
	public Collection<EmailTemplate> getEmailTemplatesByAuthorUsername(String authorUsername);
	Collection<String> getEmailTemplateNames();
}
