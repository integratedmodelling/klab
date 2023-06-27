package org.integratedmodelling.klab.hub.agreements.services;

import org.integratedmodelling.klab.hub.api.AgreementTemplate;
import org.integratedmodelling.klab.hub.enums.AgreementLevel;
import org.integratedmodelling.klab.hub.enums.AgreementType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AgreementTemplateService {
    
    AgreementTemplate getAgreementTemplate(AgreementType agreementType, AgreementLevel agreementLevel);

}
