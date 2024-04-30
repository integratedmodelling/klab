package org.integratedmodelling.klab.hub.tasks.services;

import org.integratedmodelling.klab.hub.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;

@Service
public class TaskMongoTemplate {
	
	private final String USER = "user";
	
	
	private final MongoTemplate mongoTemplate;
	
	

	@Autowired
	public TaskMongoTemplate(MongoTemplate mongoTemplate) {
		super();
		this.mongoTemplate = mongoTemplate;
	}
	
	private UpdateResult updateAll(Query query, Update update) {
		return mongoTemplate.update(Task.class)
			    .matching(query)
			    .apply(update)
			    .all();
	}
	
	public UpdateResult updateTaskUserToNull(String user) {
		Query query = new Query(Criteria.where(USER).is(user));
		Update update = new Update().set(USER, null);
		
		return updateAll(query, update);		
	}
	
	

}
