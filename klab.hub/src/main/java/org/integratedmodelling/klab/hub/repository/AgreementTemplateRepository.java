package org.integratedmodelling.klab.hub.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.api.AgreementTemplate;
import org.integratedmodelling.klab.hub.enums.AgreementLevel;
import org.integratedmodelling.klab.hub.enums.AgreementType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementTemplateRepository  extends MongoRepository<AgreementTemplate, ObjectId>{

     Optional<AgreementTemplate> findByAgreementTypeAndAgreementLevel(AgreementType agreementType, AgreementLevel agreementLevel);

    Optional<AgreementTemplate> findById(String id);


}
