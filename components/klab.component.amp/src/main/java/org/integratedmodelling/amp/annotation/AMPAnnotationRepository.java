package org.integratedmodelling.amp.annotation;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.amp.beans.AMPAnnotation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AMPAnnotationRepository extends MongoRepository<AMPAnnotation, ObjectId> {

	Optional<AMPAnnotation> findById(String id);

}