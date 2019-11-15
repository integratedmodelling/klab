package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.models.EmailTemplate;
import org.integratedmodelling.klab.hub.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Repository for Email templates
 * @author Enrico Girotto
 *
 */
public interface EmailTemplateRepository extends MongoRepository<EmailTemplate, ObjectId> {

	Optional<EmailTemplate> findById(String id);
	
	List<EmailTemplate> findByName(String name);
	
	List<EmailTemplate> findByAuthor(User author);
	
	@Query(value = "{ 'username' : ?0 1 }")
	List<EmailTemplate> findByAuthor(String username);
	
}
