package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.emails.EmailTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for Email templates
 * @author Enrico Girotto
 *
 */
public interface EmailTemplateRepository extends MongoRepository<EmailTemplate, ObjectId> {

	Optional<EmailTemplate> findById(String id);
	
	List<EmailTemplate> findByName(String name);
	
	List<EmailTemplate> findByAuthorUsername(String username);
}
