package org.integratedmodelling.klab.hub.agreements.services;

import java.util.List;

import org.integratedmodelling.klab.hub.agreements.dto.Agreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;

@Service
public class AgreementMongoTemplate {
	
	private final String AGREEMENT_COLLECTION = "Agreements";
	private final String AGREEMENTS = "agreements";
	
	private final MongoTemplate mongoTemplate;

	@Autowired
	public AgreementMongoTemplate(MongoTemplate mongoTemplate) {
		super();
		this.mongoTemplate = mongoTemplate;
	}
	
	private UpdateResult updateAll(Query query, Update update) {
		return mongoTemplate.update(Agreement.class)
			    .matching(query)
			    .apply(update)
			    .all();
	}
	
	public List<String> removeAgreementsById(List<String> agreements) {
		Query query = new Query(Criteria.where(AGREEMENTS).is(agreements));
		return mongoTemplate.findAllAndRemove(query, AGREEMENT_COLLECTION);	
	}
	
	

}
