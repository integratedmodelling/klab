package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.models.tasks.Task;
import org.integratedmodelling.klab.hub.models.tasks.TaskStatus;
import org.integratedmodelling.klab.hub.models.tokens.ClickbackToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends MongoRepository<Task, ObjectId>{
	
	Optional<Task> findById(String id);
	
	Optional<Task> findByRequesteeIgnoreCase(String username);
	
	List<Task> findByStatus(TaskStatus status);
	
	@Query(value="{ '_class' : 'org.integratedmodelling.klab.hub.models.tasks.GroupRequestTask', 'token' : ?0 }")
	Optional<Task> findGroupRequestByToken(ClickbackToken token);
}