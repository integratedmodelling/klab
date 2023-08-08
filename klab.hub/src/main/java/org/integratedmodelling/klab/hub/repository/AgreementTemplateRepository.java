package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.api.AgreementTemplate;
import org.integratedmodelling.klab.hub.enums.AgreementLevel;
import org.integratedmodelling.klab.hub.enums.AgreementType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementTemplateRepository  extends ResourceRepository<AgreementTemplate, String>{

     Optional<AgreementTemplate> findByAgreementTypeAndAgreementLevelAndDefaultTemplate(AgreementType agreementType, AgreementLevel agreementLevel, Boolean deafaultTemplate);
     List<AgreementTemplate> findAllByAgreementTypeAndAgreementLevel(AgreementType agreementType, AgreementLevel agreementLevel);

    Optional<AgreementTemplate> findById(String id);

    Optional<AgreementTemplate> findByIdIgnoreCase(String id);


}
