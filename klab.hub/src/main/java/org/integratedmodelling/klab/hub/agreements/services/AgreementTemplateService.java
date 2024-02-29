package org.integratedmodelling.klab.hub.agreements.services;

import java.util.List;

import org.integratedmodelling.klab.hub.agreements.dto.AgreementTemplate;
import org.integratedmodelling.klab.hub.agreements.payload.RequestAgreementTemplate;
import org.integratedmodelling.klab.hub.enums.AgreementLevel;
import org.integratedmodelling.klab.hub.enums.AgreementType;
import org.springframework.stereotype.Service;

@Service
public interface AgreementTemplateService {
    
    AgreementTemplate getAgreementTemplate(AgreementType agreementType, AgreementLevel agreementLevel);

    List<AgreementTemplate> getAgreementTemplates(RequestAgreementTemplate requestAgreementTemplate);

    AgreementTemplate getAgreementTemplate(RequestAgreementTemplate requestAgreementTemplate) throws Exception;

    AgreementTemplate create(AgreementTemplate agreementTemplate);

    AgreementTemplate update(AgreementTemplate agreementTemplate);

    boolean exists(String id);

    void delete(String id);

    void delete(List<AgreementTemplate> requestAgreementTemplates);

    

}
