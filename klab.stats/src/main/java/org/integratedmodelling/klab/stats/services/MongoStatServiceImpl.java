package org.integratedmodelling.klab.stats.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.rest.SessionReference;
import org.integratedmodelling.klab.rest.StatsInstertResponse;
import org.integratedmodelling.klab.stats.api.models.StatsFindPageRequest;
import org.integratedmodelling.klab.stats.api.models.StatsFindPageResponse;
import org.integratedmodelling.klab.stats.api.models.StatsInsertRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.model.Filters;


@Service
public class MongoStatServiceImpl implements StatsService {
	
	@Value("${mongo.database:stats}")
	private String dbName;
    
	@Autowired
    MongoClient mongo;
	
	private Integer automaticLimit = 50;
	
	@Override
	public <T> StatsInstertResponse<T> insertRequest(StatsInsertRequest<T> request) {
		MongoDatabase db = mongo.getDatabase("stats");
		MongoCollection<T> col = db.getCollection(dbName, request.getType());
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

    @Override
    public <T> StatsFindPageResponse<T> findRequest(StatsFindPageRequest<T> request) {
        MongoCollection<T> db = mongo.getDatabase("stats").getCollection(dbName, request.getType());
        int limit = (request.getLimit() != 0) ? request.getLimit() : automaticLimit;
        int page = (request.getPage() != 0) ? request.getPage() : 1;
        double pageCount = db.countDocuments(Filters.eq("_t", request.getType().getCanonicalName()))/ (double) limit;
        int pages = (int) Math.ceil(pageCount);
        
        MongoCursor<T> res = db.find(Filters.eq("_t", request.getType().getCanonicalName()))
            .skip(limit * (page - 1)).limit(limit).iterator();
        
        List<T> retrieved = new ArrayList<>();
        
        while(res.hasNext()) {
           retrieved.add(res.next());
        }
        
        StatsFindPageResponse<T> resp = new StatsFindPageResponse<>();
        resp.setResults(request.getType(), retrieved);
        resp.setCurrentPage(page);
        resp.setPages(pages);
        return resp;
    }


		

}
