package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.api.Task;
import org.integratedmodelling.klab.hub.api.TaskStatus;
import org.integratedmodelling.klab.hub.api.TokenClickback;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends MongoRepository<Task, ObjectId>, PagingAndSortingRepository<Task, ObjectId>{
	
	Optional<Task> findById(String id);
	
	List<Task> findByStatus(TaskStatus status);
	
    @Query("{'status' : ?0 }")
    List<Task> findByStatus(TaskStatus status, PageRequest pageRequest);
	
	@Query(value="{ '_class' : ?0, 'token' : ?1 }")
	Optional<Task> findByToken(String clazz, TokenClickback token);
	
	@Query("{'_class' : ?0 }")
	List<Task> findByClass(String clazz);

    @Query("{'_class' : ?0 }")
    List<Task> findByClass(String clazz, PageRequest pageRequest);

	@Query("{'_class' : ?0 , 'status' : ?1}")
	List<Task> findByClassAndStatus(String clazz, TaskStatus status);
	
    @Query("{'_class' : ?0 , 'status' : ?1}")
    List<Task> findByClassAndStatus(String clazz, TaskStatus status, PageRequest pageRequest);

}