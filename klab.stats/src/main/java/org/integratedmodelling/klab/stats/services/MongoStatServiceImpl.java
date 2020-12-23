package org.integratedmodelling.klab.stats.services;

import org.integratedmodelling.klab.rest.StatsInstertResponse;
import org.integratedmodelling.klab.stats.api.models.StatsInsertRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

@Service
public class MongoStatServiceImpl implements StatsService {
	
	@Value("${mongo.database:stats}")
	private String dbName;
    
	@Autowired
    MongoClient mongo;
	
	@Override
	public <T> StatsInstertResponse<T> insertRequest(StatsInsertRequest<T> request) {
		MongoDatabase db = mongo.getDatabase("stats");
		MongoCollection<T> col = db.getCollection("dbName", request.getType());
		if (request.getType().isInstance(request.getM())) {
			T objAsType = request.getType().cast(request.getM());
			InsertOneResult res = col.insertOne(objAsType);
			StatsInstertResponse<T> resp = new StatsInstertResponse<>(request.getType());
			resp.setSuccess(res.wasAcknowledged());
			resp.setId(res.getInsertedId().toString());
			return resp;
		} else {
			StatsInstertResponse<T> resp = new StatsInstertResponse<>(request.getType());
			resp.setSuccess(false);
			return resp;
		}
	}

}
