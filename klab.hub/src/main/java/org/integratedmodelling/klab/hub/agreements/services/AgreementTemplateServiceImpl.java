package org.integratedmodelling.klab.hub.agreements.services;

import org.integratedmodelling.klab.hub.api.AgreementTemplate;
import org.integratedmodelling.klab.hub.enums.AgreementLevel;
import org.integratedmodelling.klab.hub.enums.AgreementType;
import org.integratedmodelling.klab.hub.repository.AgreementTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgreementTemplateServiceImpl implements AgreementTemplateService{
    
    @Autowired
    AgreementTemplateRepository agreementTemplateRepository;

    @Override
    public AgreementTemplate getAgreementTemplate(AgreementType agreementType, AgreementLevel agreementLevel) {
        return agreementTemplateRepository.findByAgreementTypeAndAgreementLevel(agreementType, agreementLevel).get();
    }
 

    
}
