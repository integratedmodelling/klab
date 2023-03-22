package org.integratedmodelling.klab.hub.repository;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.api.Agreement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementRepository extends MongoRepository<Agreement, ObjectId> {

}
