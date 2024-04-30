package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.api.Task;
import org.integratedmodelling.klab.hub.tasks.enums.TaskStatus;
import org.integratedmodelling.klab.hub.tokens.dto.TokenClickback;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends ResourceRepository<Task, String>{
	
	Optional<Task> findById(String id);
	
	List<Task> findByStatus(TaskStatus status);
	
	@Query(value="{ '_class' : ?0, 'token' : ?1 }")
	Optional<Task> findByToken(String clazz, TokenClickback token);
	
	@Query("{'_class' : ?0 }")
	List<Task> findByClass(String clazz);
	
	@Query("{'_class' : ?0 , 'status' : ?1}")
	List<Task> findByClassAndStatus(String clazz, TaskStatus status);
	
}