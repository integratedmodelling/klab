package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.emails.dto.EmailTemplate;

/**
 * Repository for Email templates
 * @author Enrico Girotto
 *
 */
public interface EmailTemplateRepository extends ResourceRepository<EmailTemplate, String> {

	Optional<EmailTemplate> findById(String id);
	
	Optional<EmailTemplate> findByName(String name);
	
	List<EmailTemplate> findByAuthorUsername(String username);
}
