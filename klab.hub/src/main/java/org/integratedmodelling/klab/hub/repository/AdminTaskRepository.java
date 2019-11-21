package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.models.Task;
import org.integratedmodelling.klab.hub.models.TaskStatus;
import org.integratedmodelling.klab.hub.models.tokens.ClickbackToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminTaskRepository extends MongoRepository<Task, ObjectId>{
	Optional<Task> findById(String id);
	
	Optional<Task> findByRequesteeIgnoreCase(String username);
	
	Optional<Task> findByToken(ClickbackToken token);
	
	List<Task> findByStatus(TaskStatus status);
	
}
