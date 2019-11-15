package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.models.EmailTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for Email templates
 * @author Enrico Girotto
 *
 */
public interface EmailTemplateRepository extends MongoRepository<EmailTemplate, ObjectId> {

	Optional<EmailTemplate> findById(String id);
	
	Optional<EmailTemplate> findByName(String name);
	
	public List<EmailTemplate> findAll();
	
}
